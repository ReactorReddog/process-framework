package org.processframework.gateway.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.tools.StringPool;
import com.cn.processframework.tools.string.StringUtils;
import com.google.common.base.Charsets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Miscellaneous utilities for web applications.
 *
 * @author apple
 */
@Slf4j
public class WebUtil extends org.springframework.web.util.WebUtils {

	public static final String USER_AGENT_HEADER = "user-agent";
	public static final String MULTIPART = "multipart/";

	private static final String UTF8 = "UTF-8";
	private static final String IP_UNKNOWN = "unknown";
	private static final String IP_LOCAL = "127.0.0.1";
	private static final int IP_LEN = 15;


	/**
	 * ????????????ajax??????
	 * spring ajax ???????????? ResponseBody ?????? RestController??????
	 *
	 * @param handlerMethod HandlerMethod
	 * @return ??????ajax??????
	 */
	public static boolean isBody(HandlerMethod handlerMethod) {
		ResponseBody responseBody = ClassUtil.getAnnotation(handlerMethod, ResponseBody.class);
		return responseBody != null;
	}

	/**
	 * ??????cookie
	 *
	 * @param name cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(String name) {
		HttpServletRequest request = WebUtil.getRequest();
		Assert.notNull(request, "request from RequestContextHolder is null");
		return getCookieVal(request, name);
	}

	/**
	 * ??????cookie
	 *
	 * @param request HttpServletRequest
	 * @param name    cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * ?????? ???????????????cookie
	 *
	 * @param response HttpServletResponse
	 * @param key      cookie key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * ??????cookie
	 *
	 * @param response        HttpServletResponse
	 * @param name            cookie name
	 * @param value           cookie value
	 * @param maxAgeInSeconds maxage
	 */
	public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(StringPool.SLASH);
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * ?????? HttpServletRequest
	 *
	 * @return {HttpServletRequest}
	 */
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * ??????json
	 *
	 * @param response HttpServletResponse
	 * @param result   ????????????
	 */
	public static void renderJson(HttpServletResponse response, Object result) {
		renderJson(response, result, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * ??????json
	 *
	 * @param response    HttpServletResponse
	 * @param result      ????????????
	 * @param contentType contentType
	 */
	public static void renderJson(HttpServletResponse response, Object result, String contentType) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		try (PrintWriter out = response.getWriter()) {
			out.append(JSONObject.toJSONString(result));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * ??????ip
	 *
	 * @return {String}
	 */
	public static String getIP() {
		return getIP(WebUtil.getRequest());
	}

	private static final String[] IP_HEADER_NAMES = new String[]{
		"x-forwarded-for",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_CLIENT_IP",
		"HTTP_X_FORWARDED_FOR"
	};

	private static final Predicate<String> IP_PREDICATE = (ip) -> StringUtils.isBlank(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip);

	/**
	 * ??????ip
	 *
	 * @param request HttpServletRequest
	 * @return {String}
	 */
	@Nullable
	public static String getIP(@Nullable HttpServletRequest request) {
		if (request == null) {
			return StringPool.EMPTY;
		}
		String ip = null;
		for (String ipHeader : IP_HEADER_NAMES) {
			ip = request.getHeader(ipHeader);
			if (!IP_PREDICATE.test(ip)) {
				break;
			}
		}
		if (IP_PREDICATE.test(ip)) {
			ip = request.getRemoteAddr();
		}
		return StringUtils.isBlank(ip) ? null : StringUtils.splitTrim(ip, StringPool.COMMA).get(0);
	}

	/**
	 * ?????????????????????ip
	 * @param request request
	 * @return ??????ip
	 */
	public static String getIP(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		String ipAddress = headers.getFirst("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = headers.getFirst("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = headers.getFirst("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = Optional.ofNullable(request.getRemoteAddress())
					.map(address -> address.getAddress().getHostAddress())
					.orElse("");
			if (IP_LOCAL.equals(ipAddress)) {
				// ??????????????????????????????IP
				try {
					InetAddress inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					// ignore
				}
			}
		}

		// ?????????????????????????????????????????????IP??????????????????IP,??????IP??????','??????
		if (ipAddress != null && ipAddress.length() > IP_LEN) {
			int index = ipAddress.indexOf(",");
			if (index > 0) {
				ipAddress = ipAddress.substring(0, index);
			}
		}
		return ipAddress;
	}
	/**
	 * ?????????????????????
	 *
	 * @param name ???????????????
	 * @return ?????????
	 */
	public static String getHeader(String name) {
		HttpServletRequest request = getRequest();
		return Objects.requireNonNull(request).getHeader(name);
	}

	/**
	 * ?????????????????????
	 *
	 * @param name ???????????????
	 * @return ?????????
	 */
	public static Enumeration<String> getHeaders(String name) {
		HttpServletRequest request = getRequest();
		return Objects.requireNonNull(request).getHeaders(name);
	}

	/**
	 * ????????????????????????
	 *
	 * @return ???????????????
	 */
	public static Enumeration<String> getHeaderNames() {
		HttpServletRequest request = getRequest();
		return Objects.requireNonNull(request).getHeaderNames();
	}

	/**
	 * ??????????????????
	 *
	 * @param name ???????????????
	 * @return ????????????
	 */
	public static String getParameter(String name) {
		HttpServletRequest request = getRequest();
		return Objects.requireNonNull(request).getParameter(name);
	}

	/**
	 * ?????? request ?????????
	 *
	 * @param servletInputStream servletInputStream
	 * @return body
	 */
	public static String getRequestBody(ServletInputStream servletInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (servletInputStream != null) {
				try {
					servletInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ?????? request ????????????
	 *
	 * @param request request
	 * @return {String}
	 */
	public static String getRequestContent(HttpServletRequest request) {
		try {
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				return new String(queryString.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8).replaceAll("&amp;", "&").replaceAll("%22", "\"");
			}
			String charEncoding = request.getCharacterEncoding();
			if (charEncoding == null) {
				charEncoding = StringPool.UTF_8;
			}
			byte[] buffer = getRequestBody(request.getInputStream()).getBytes();
			String str = new String(buffer, charEncoding).trim();
			if (StringUtils.isBlank(str)) {
				StringBuilder sb = new StringBuilder();
				Enumeration<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String key = parameterNames.nextElement();
					String value = request.getParameter(key);
					StringUtils.appendBuilder(sb, key, "=", value, "&");
				}
				str = StringUtils.removeSuffix(sb.toString(), "&");
			}
			return str.replaceAll("&amp;", "&");
		} catch (Exception ex) {
			ex.printStackTrace();
			return StringPool.EMPTY;
		}
	}
	/**
	 * ???get????????????????????????map???
	 *
	 * @param query charset=utf-8&biz_content=xxx
	 * @return ??????map??????
	 */
	public static Map<String, String> parseQueryToMap(String query) {
		if (query == null) {
			return Collections.emptyMap();
		}
		String[] queryList = org.apache.commons.lang3.StringUtils.split(query, '&');
		Map<String, String> params = new HashMap<>(16);
		for (String param : queryList) {
			String[] paramArr = param.split("\\=");
			if (paramArr.length == 2) {
				try {
					params.put(paramArr[0], URLDecoder.decode(paramArr[1], UTF8));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			} else if (paramArr.length == 1) {
				params.put(paramArr[0], "");
			}
		}
		return params;
	}
	/**
	 * ???map???????????????????????????
	 *
	 * @return ??????aa=1&b=c...
	 */
	public static String convertMapToQueryString(Map<String, ?> apiParam) {
		List<String> list = new ArrayList<>(apiParam.size());
		for (Map.Entry<String, ?> entry : apiParam.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Collection) {
				Collection collection = (Collection) value;
				for (Object el : collection) {
					list.add(key + "=" + URLEncoder.encode(String.valueOf(el), StandardCharsets.UTF_8));
				}
			} else {
				list.add(key + "=" + URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8));
			}
		}
		return org.apache.commons.lang.StringUtils.join(list, "&");
	}
	/**
	 * request?????????????????????map
	 *
	 * @param request request??????
	 * @return ?????????????????????
	 */
	public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap == null || paramMap.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, String> retMap = new HashMap<>(paramMap.size() * 2);

		Set<Map.Entry<String, String[]>> entrySet = paramMap.entrySet();

		for (Map.Entry<String, String[]> entry : entrySet) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			if (values.length >= 1) {
				retMap.put(name, values[0]);
			} else {
				retMap.put(name, "");
			}
		}
		return retMap;
	}
	/**
	 * ??????json?????????Map???
	 *
	 * @param request ???????????????application/json???Request
	 * @return ??????Map
	 */
	public static Map<String, Object> convertJsonRequestToMap(HttpServletRequest request) {
		try {
			String text = getText(request);
			return JSON.parseObject(text);
		} catch (IOException e) {
			log.error("??????json????????????", e);
			return Collections.emptyMap();
		}
	}
	/**
	 * ???????????????????????????
	 *
	 * @param request ??????
	 * @return true??????
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		String contentType = request.getContentType();
		// Don't use this filter on GET method
		if (contentType == null) {
			return false;
		}
		return contentType.toLowerCase(Locale.ENGLISH).startsWith(MULTIPART);
	}

	/**
	 * ????????????????????????
	 *
	 * @param request request
	 * @return ?????????????????????????????????
	 */
	public static UploadInfo getUploadInfo(HttpServletRequest request) {
		if (request instanceof StandardMultipartHttpServletRequest) {
			return getUploadInfo((StandardMultipartHttpServletRequest)request);
		}
		UploadInfo uploadInfo = new UploadInfo();
		// ?????????????????????????????????
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		Map<String, String> uploadParams = new HashMap<>(16);
		UploadContext uploadContext = null;
		try {
			List<MultipartFile> multipartFileList = new ArrayList<>(16);
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					uploadParams.put(fileItem.getFieldName(), fileItem.getString(UTF8));
				} else {
					multipartFileList.add(new CommonsMultipartFile(fileItem));
				}
			}
			if (multipartFileList.size() > 0) {
				Map<String, List<MultipartFile>> multipartFileMap = multipartFileList
						.stream()
						.collect(Collectors.groupingBy(MultipartFile::getName));
				uploadContext = new ApiUploadContext(multipartFileMap);
			}
			uploadInfo.setUploadParams(uploadParams);
			uploadInfo.setUploadContext(uploadContext);
		} catch (Exception e) {
			log.error("??????????????????", e);
		}
		return uploadInfo;
	}

	/**
	 * ????????????????????????
	 * @param request request
	 * @return
	 */
	public static UploadInfo getUploadInfo(StandardMultipartHttpServletRequest request) {
		UploadInfo uploadInfo = new UploadInfo();
		Map<String, String> uploadParams = new HashMap<>(16);
		request.getParameterMap().forEach((key, value)-> uploadParams.put(key, value[0]));
		MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
		List<MultipartFile> multipartFileList = new ArrayList<>(10);
		for (String key : multiFileMap.keySet()) {
			multipartFileList.addAll(multiFileMap.get(key));
		}
		Map<String, List<MultipartFile>> multipartFileMap = multipartFileList
				.stream()
				.collect(Collectors.groupingBy(MultipartFile::getName));
		UploadContext uploadContext = new ApiUploadContext(multipartFileMap);

		uploadInfo.setUploadParams(uploadParams);
		uploadInfo.setUploadContext(uploadContext);
		return uploadInfo;
	}

	/**
	 * ???????????????
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getText(HttpServletRequest request) throws IOException {
		return IOUtils.toString(request.getInputStream(), String.valueOf(StandardCharsets.UTF_8));
	}

	/**
	 * ????????????
	 * @param responseBody
	 * @param sign
	 * @param secret
	 * @throws Exception
	 */
	public static void checkResponseBody(String responseBody, String sign, String secret) throws Exception {
		if (sign == null) {
			throw new Exception("???????????????");
		}
		String signContent = secret + responseBody + secret;
		String clientSign = DigestUtils.md5Hex(signContent);
		if (!sign.equals(clientSign)) {
			throw new Exception("????????????");
		}
	}

	/**
	 * ????????????????????????
	 */
	@Data
	public static class UploadInfo {
		private Map<String, String> uploadParams;
		private UploadContext uploadContext;
	}

}

