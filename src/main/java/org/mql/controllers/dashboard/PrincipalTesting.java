package org.mql.controllers.dashboard;

import java.security.Principal;

import org.mql.models.Category;
import org.mql.models.Member;
import org.mql.services.CategoryService;
import org.mql.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/principal")
public class PrincipalTesting {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired 
	MemberService memberService;
	
	@GetMapping("/")
	public @ResponseBody String mainPage(Principal principal) {
		if(principal == null) {

		}
		return principal.toString();
	}
	
	
	@GetMapping("/test")
	public @ResponseBody String test() {
		Category category = new Category("Informatique décisionelle");
		categoryService.save(category);
		Member member = memberService.findByEmail("Khalidqqr@gmail.com");
		member.addCategory(category);
		memberService.save(member);
		return "ss";
	}
}
