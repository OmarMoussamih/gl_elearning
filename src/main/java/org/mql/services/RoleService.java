package org.mql.services;

import org.mql.models.Role;

public interface RoleService {
	static String STUDENT = "ROLE_STUDENT";
	static String TEACHER = "ROLE_TEACHER";
	static String ADMIN = "ROLE_ADMIN";
	static String RESPONSABLE = "ROLE_RESPONSABLE";
	
	Role findRoleByName(String name);
}
