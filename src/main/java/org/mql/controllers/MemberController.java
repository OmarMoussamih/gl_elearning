package org.mql.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mql.models.Category;
import org.mql.models.Member;
import org.mql.services.CategoryService;
import org.mql.services.MemberService;
import org.mql.services.SecurityService;
import org.mql.services.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
		Logger logger = LoggerFactory.getLogger(MemberController.class);

		@Autowired MemberService memberService;
		@Autowired CategoryService categoryService;
		
		@Autowired
	    SecurityService securityService;
		
		@RequestMapping(value="inscrire" ,method=RequestMethod.GET)
		public String New(Model model,Principal principal, HttpServletRequest request) {
			if(principal!=null) {
				return "redirect:/dashboard/";
			}
			try {
				Object flash = request.getSession().getAttribute("flash");
				model.addAttribute("flash",flash);
				request.getSession().removeAttribute("flash");
			}catch(Exception e){
				// flash doesn't exist.. do nothing
			}	
			List<Category> categories = categoryService.findAll();
			model.addAttribute("cats", categories);
			model.addAttribute("member", new Member());
			return "admission/admission2";
		}
		
		@RequestMapping(value="/inscription",method=RequestMethod.POST)
		public String save(Model model,@Valid Member member, BindingResult bindingResult,HttpServletRequest request)
		{
			if(bindingResult.hasErrors())
			{
				//model.addAttribute("flash","Erreur lors de la saisie");
				request.getSession().setAttribute("flash", "Erreur lors de la saisie");
				return "redirect:/inscrire";
			}
			// old password before register ; for the auto login
			//String password = member.getPassword();
			if(memberService.registerNewMember(member)==null) {
				request.getSession().setAttribute("flash", "L'Email saisi existe déjà");
				//model.addAttribute("flash","l'email saisi existe déjà");
				return "redirect:/inscrire";
			}
			//securityService.autoLogin(member.getEmail(), password);
			model.addAttribute("flash","un email de confirmation a été envoyé à "+member.getEmail());
			return "admission/message";
		}
		
		
		// Process confirmation link
		@GetMapping(value="/confirm")
		public String showConfirmationPage(Model model, @RequestParam("token") String token) {
				
			Member member = memberService.findByConfirmationToken(token);
				
			if (member == null) { // No token found in DB
				model.addAttribute("flash", "Oops!  Lien de confirmation non valide ou expiré.");
			} else { // Token found
				member.setActivated(true);
				member.setConfirmationToken(null);
				model.addAttribute("flash","Votre email a été confirmé avec succés. Vous pouvez se connecter maintenant !");
				memberService.save(member);
			}	
			return "admission/message";
		}
}
