package org.mql.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mql.models.Formation;
import org.mql.models.Member;
import org.mql.services.FormationService;
import org.mql.services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FormationController {

	private Logger log = LoggerFactory.getLogger(FormationController.class);

	
	@Autowired
	MemberService memberService;
	
	@Autowired
	FormationService formationService;
	
	
	@GetMapping("/formation/{id}")
	public String getForm(@PathVariable int id,Model model,HttpServletRequest request,Principal principal) {
		Formation formation =formationService.findById(id);
		if(formation==null) {
			return "error/404";
		}
		try {
			Object flash = request.getSession().getAttribute("flash");
			model.addAttribute("flash", flash);
			request.getSession().removeAttribute("flash");
		}catch(Exception e){
			// flash doesn't exist.. do nothing
			
		}	
		boolean inscription = false;
		if(principal != null) {
			Member member = memberService.findByEmail(principal.getName());
			inscription = formationService.containsMember(formation, member);
		}
		model.addAttribute("inscription", inscription);
		model.addAttribute("formation",formation);
		return "/formations/formation";
	}
	
	//page dans l'acceuil pour afficher toutes les formations*************************************************
	@GetMapping(value="/formations")
	public String index(Model model)
	{
		List<Formation> Formations=formationService.findAllByOrderByIdDesc();
		model.addAttribute("listeFormations",Formations);	
		return "main_views/listformations" ;
	}
	
	// following
	@GetMapping(value="/formation/{id}/follow")
	public String followFormation(@PathVariable int id,Principal principal,Model model,HttpServletRequest request) {
		if(principal==null) {
			request.getSession().setAttribute("flash", "msg");
			return "redirect:/formation/"+id;  
		}
		Member member = memberService.findByEmail(principal.getName());
		Formation formation = formationService.findById(id);
		formation.addMember(member);
		formationService.save(formation);
		return "redirect:/formation/"+id;
	}
}
