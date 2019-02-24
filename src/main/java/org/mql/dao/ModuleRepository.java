package org.mql.dao;

import java.util.List;

import org.mql.models.Member;
import org.mql.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer>{
	List<Module> findAllByTeacher(Member member);
}
