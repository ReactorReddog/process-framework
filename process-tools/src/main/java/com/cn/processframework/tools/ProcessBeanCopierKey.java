package com.cn.processframework.tools;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 * @author apple
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProcessBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
