/**
 * 
 */
package com.bobo.security.core.social.view;

import com.bobo.security.core.support.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绑定结果视图
 * @author bobo
 *
 */
public class BoboConnectView extends AbstractView {

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel
	 * (java.util.Map, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

		Map<String, Boolean> result = new HashMap<>();
		for (String key : connections.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		response.setContentType("application/json;charset=UTF-8");

		if (model.get("connections") == null) {
			response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse<Map>(0,"解绑成功",result)));
		} else {
			response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse<Map>(0,"绑定成功",result)));
		}


	}

}
