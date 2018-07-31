/**
 * 
 */
package com.bobo.security.core.support;

import lombok.Data;

/**
 * 简单响应的封装类
 * 
 * @author bobo
 *
 */
@Data
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}
	
	private Object content;


}
