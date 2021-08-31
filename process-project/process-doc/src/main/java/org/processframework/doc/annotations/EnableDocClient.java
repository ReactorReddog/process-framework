package org.processframework.doc.annotations;

import org.processframework.doc.configuration.ProcessDocConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author apple
 * @desc 文档开启配置
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ProcessDocConfiguration.class})
@EnableAutoConfiguration
public @interface EnableDocClient {
}
