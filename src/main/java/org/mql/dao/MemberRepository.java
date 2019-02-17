package org.mql.dao;

import java.util.List;

import org.mql.models.Member;
import org.mql.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, Integer>{
	public Member findByEmail(String email);
	public List<Member> findByRolesContaining(Role role);
	Member findByConfirmationToken(String confirmationToken);
}