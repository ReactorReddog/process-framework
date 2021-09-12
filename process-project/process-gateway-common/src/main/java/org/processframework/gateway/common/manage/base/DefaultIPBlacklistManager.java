package org.processframework.gateway.common.manage.base;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.processframework.gateway.common.manage.IPBlacklistManager;

import java.util.Set;

/**
 * ip黑名单管理
 * @author apple
 */
public class DefaultIPBlacklistManager implements IPBlacklistManager {

    private static Set<String> ipList = Sets.newConcurrentHashSet();

    @Override
    public void add(String ip) {
        ipList.add(ip);
    }

    @Override
    public void remove(String ip) {
        ipList.remove(ip);
    }

    @Override
    public boolean contains(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        return ipList.contains(ip);
    }

    @Override
    public void load() {

    }
}
