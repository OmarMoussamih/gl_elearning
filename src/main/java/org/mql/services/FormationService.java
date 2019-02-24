package org.mql.services;

import java.util.List;

import org.mql.models.Formation;
import org.mql.models.Member;

public interface FormationService {
	Formation findById(int id);
	List<Formation> findAllByOrderByIdDesc();
	boolean containsMember(Formation formation,Member member);
	Formation save(Formation formation);
	List<Formation> findByResponsable(Member member);
	List<Formation> findByFollower(Member member);
	List<Formation> findAll();
}
