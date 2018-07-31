/**
 * 
 */
package com.bobo.security.rbac.service.impl;

import com.bobo.security.rbac.domain.Admin;
import com.bobo.security.rbac.domain.RoleAdmin;
import com.bobo.security.rbac.dto.AdminCondition;
import com.bobo.security.rbac.dto.AdminInfo;
import com.bobo.security.rbac.repository.AdminRepository;
import com.bobo.security.rbac.repository.RoleAdminRepository;
import com.bobo.security.rbac.repository.RoleRepository;
import com.bobo.security.rbac.repository.spec.AdminSpec;
import com.bobo.security.rbac.repository.support.QueryResultConverter;
import com.bobo.security.rbac.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bobo
 *
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAdminRepository roleAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 *
	 * @param adminInfo
	 * @return
	 */
	@Override
	public AdminInfo create(AdminInfo adminInfo) {
		
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminInfo, admin);
		admin.setPassword(passwordEncoder.encode("123456"));
		adminRepository.save(admin);
		adminInfo.setId(admin.getId());
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/**
	 *
	 * @param adminInfo
	 * @return
	 */
	@Override
	public AdminInfo update(AdminInfo adminInfo) {
		
		Admin admin = adminRepository.findOne(adminInfo.getId());
		BeanUtils.copyProperties(adminInfo, admin);
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/**
	 * 创建角色用户关系数据。
	 * @param adminInfo
	 * @param admin
	 */
	private void createRoleAdmin(AdminInfo adminInfo, Admin admin) {
		if(CollectionUtils.isNotEmpty(admin.getRoles())){
			roleAdminRepository.delete(admin.getRoles());
		}
		RoleAdmin roleAdmin = new RoleAdmin();
		roleAdmin.setRole(roleRepository.getOne(adminInfo.getRoleId()));
		roleAdmin.setAdmin(admin);
		roleAdminRepository.save(roleAdmin);
	}

	/**
	 *
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		adminRepository.delete(id);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AdminInfo getInfo(Long id) {
		Admin admin = adminRepository.findOne(id);
		AdminInfo info = new AdminInfo();
		BeanUtils.copyProperties(admin, info);
		return info;
	}

	/**
	 *
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);
		return QueryResultConverter.convert(admins, AdminInfo.class, pageable);
	}

}
