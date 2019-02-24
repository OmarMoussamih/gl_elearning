package org.mql.services;

import java.util.List;
import java.util.Optional;

import org.mql.dao.FormationRepository;
import org.mql.models.Formation;
import org.mql.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationServiceImpl implements FormationService{
	
	@Autowired
	FormationRepository formationRepository;
	
	@Override
	public Formation findById(int id) {
		Optional<Formation> formation = formationRepository.findById(id);
		if(formation.isPresent()) return formation.get();
		return null;
	}
	
	@Override
	public List<Formation> findAllByOrderByIdDesc() {
		return formationRepository.findAllByOrderByIdDesc();
	}
	
	@Override
	public boolean containsMember(Formation formation, Member member) {
		List<Member> members = formation.getMembers();
		if(members.contains(member)) return true;
		return false;
	}
	
	@Override
	public Formation save(Formation formation) {
		return formationRepository.save(formation);
	}
	
	@Override
	public List<Formation> findAll() {
		return formationRepository.findAllByOrderByIdDesc();
	}
	
	@Override
	public List<Formation> findByResponsable(Member member) {
		return formationRepository.findAllByCreatorOrderByIdDesc(member);
	}
	
	@Override
	public List<Formation> findByFollower(Member member) {
		return member.getFollowedFormations();
	}
}
