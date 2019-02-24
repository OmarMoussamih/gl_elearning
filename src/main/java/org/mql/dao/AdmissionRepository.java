package org.mql.dao;

import java.util.List;

import org.mql.models.Admission;
import org.mql.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRepository extends JpaRepository<Admission, Integer>{
	Admission findByMember(Member member);
	List<Admission> findByVisible(Boolean bool);
}
