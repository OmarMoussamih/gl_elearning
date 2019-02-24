package org.mql.services;

import org.mql.dao.RoleRepository;
import org.mql.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository repository;
	
	@Override
	public Role findRoleByName(String name) {
		return repository.findRoleByName(name);
	}

}
