package org.mql.services;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.mql.dao.MemberRepository;
import org.mql.email.DemandeAdmission;
import org.mql.models.Member;
import org.mql.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired 
	private MemberRepository repository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DemandeAdmission mail;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Member findByEmail(String email) {	
		return repository.findByEmail(email);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// load member - throw exception if not found
		Member member = repository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException("Member not found");
			
		}
		// return the found member
		return member;
	}
	
	@Override
	public Member registerNewMember(Member member) {
		if(emailExist(member.getEmail())) {
			return null;
		}
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		Role studentRole = roleService.findRoleByName(RoleService.STUDENT);
		member.addRole(studentRole);
		member.setActivated(false);
		member.setConfirmationToken(UUID.randomUUID().toString());
		
		String htmlContent = "<html>"
				+ "<body  style=\"color: white; font-family: Helvetica, Sans-Serif;\">\r\n"
				+"<div style=\"background-color:rgb(36, 119, 192);  padding:10px;\">\r\n"
		        + " <h1 align=\"center\">Confirmation d'email</h1>"
				+ " <p><strong>Pour confirmer votre adresse e-mail, veuillez cliquer sur le lien ci-dessous.</strong></p>" 
		        + "<a style=\"color: rgb(255, 210, 210)\" href=\"http://localhost:8080/confirm?token="+member.getConfirmationToken()+"\">Lien de confirmation</a>" + "</div>" + "</body>\r\n" + "</html>";
		try {
			mail.send(member.getEmail(),"master.qualite.logiciel2019@gmail.com", "Confirmation d'email - E-Learning MQL", htmlContent);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return repository.save(member);
	}
	
	@Override
	public boolean emailExist(String email) {
		Member member = repository.findByEmail(email);
		if(member!=null) return true;
		return false;
	}
	
	@Override
	public boolean setRole(Member member, Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Member> findTeachers() {
		Role role = roleService.findRoleByName(RoleService.TEACHER);
		return repository.findByRolesContaining(role);
	}
	
	@Override
	public Member save(Member member) {
		return repository.save(member);
	}
	
	@Override
	public Member findByConfirmationToken(String confirmation) {
		return repository.findByConfirmationToken(confirmation);
	}
}
