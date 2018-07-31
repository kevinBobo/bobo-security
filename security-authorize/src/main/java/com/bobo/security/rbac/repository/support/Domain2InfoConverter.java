/**
 * 
 */
package com.bobo.security.rbac.repository.support;

import org.springframework.core.convert.converter.Converter;

/**
 * @author bobo
 *
 */
public interface Domain2InfoConverter<T, I> extends Converter<T, I> {
	
}
