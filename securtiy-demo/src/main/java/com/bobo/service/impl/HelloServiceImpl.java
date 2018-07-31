/**
 * 
 */
package com.bobo.service.impl;

import com.bobo.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author bobo
 *
 */
@Service
public class HelloServiceImpl implements HelloService {

	/**
	 *
	 * @param name
	 * @return
	 */
	@Override
	public String greeting(String name) {
		System.out.println("greeting");
		return "hello "+name;
	}

}
