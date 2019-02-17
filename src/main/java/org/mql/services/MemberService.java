package org.mql.services;

import java.util.List;

import org.mql.models.Member;
import org.mql.models.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService{
	Member findByEmail(String email);
	Member registerNewMember(Member member);
	boolean emailExist(String email);
	boolean setRole(Member member,Role role);
	List<Member> findTeachers();
	Member save(Member member);
	Member findByConfirmationToken(String confirmation);
}
