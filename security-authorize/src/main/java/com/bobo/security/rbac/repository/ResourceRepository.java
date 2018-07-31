/**
 * 
 */
package com.bobo.security.rbac.repository;

import com.bobo.security.rbac.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author bobo
 *
 */
@Repository
public interface ResourceRepository extends BoboRepository<Resource> {

	Resource findByName(String name);

}
