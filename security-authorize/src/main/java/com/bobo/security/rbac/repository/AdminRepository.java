/**
 * 
 */
package com.bobo.security.rbac.repository;

import com.bobo.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author bobo
 *
 */
@Repository
public interface AdminRepository extends BoboRepository<Admin> {

	Admin findByUsername(String username);

}
