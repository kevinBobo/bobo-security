/**
 * 
 */
package com.bobo.security.rbac.repository.spec;


import com.bobo.security.rbac.domain.Admin;
import com.bobo.security.rbac.dto.AdminCondition;
import com.bobo.security.rbac.repository.support.BoboSpecification;
import com.bobo.security.rbac.repository.support.QueryWraper;

/**
 * @author bobo
 *
 */
public class AdminSpec extends BoboSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
