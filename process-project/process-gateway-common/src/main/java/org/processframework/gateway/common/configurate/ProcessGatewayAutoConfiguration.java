package org.processframework.gateway.common.configurate;

import org.processframework.gateway.common.configurate.gateway.AlipayGatewayConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author apple 
 * @see <a href="https://blog.csdn.net/seashouwang/article/details/80299571"></a>
 * @desc 网关环境类
 * @since 1.0.0.RELEASE
 */
@Configuration
@Import(AlipayGatewayConfiguration.class)
@AutoConfigureBefore(RibbonAutoConfiguration.class)
public class ProcessGatewayAutoConfiguration extends BaseGatewayAutoConfiguration {
}
