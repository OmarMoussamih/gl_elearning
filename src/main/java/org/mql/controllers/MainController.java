package org.mql.controllers;

import java.util.List;
import java.util.Vector;

import org.mql.dao.FormationRepository;
import org.mql.dao.MemberRepository;
import org.mql.dao.ModuleRepository;
import org.mql.dao.StreamingRepository;
import org.mql.dao.TimingRepository;
import org.mql.models.Category;
import org.mql.models.Formation;
import org.mql.models.Member;
import org.mql.models.Module;
import org.mql.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@Autowired
	FormationRepository formationRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	StreamingRepository streamingRepository;

	@Autowired
	TimingRepository timingRepository;

	@Autowired
	CategoryService categoryService;

	@GetMapping(path = "/security")
	public String addNewMember() {
		return "dashboard/index";
	}

	/****************** added *///////////////////
	@GetMapping("/teacher/{id}")
	public String showStream(@PathVariable int id, Model model) {
		Member member = memberRepository.findById(id).get();
		model.addAttribute("member", member);
		return "main_views/teacher-detail";

	}

	//
	@GetMapping("/")
	public String home() {
		return "main_views/home";
	}

	@GetMapping("/articles")
	public String articles() {
		return "main_views/articles";
	}

	@GetMapping("/contacts")
	public String contacts() {
		return "main_views/contacts";
	}

	@GetMapping("/register2")
	public String register() {
		return "main_views/register";
	}

	@GetMapping("/register")
	public String register2(Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		return "admission/admission2";
	}

	@GetMapping("/add")
	public String addSave(@RequestParam(value = "preferences", required = false) int[] preferences, Model model,
			Member member) {

		if (preferences != null) {
			Category category;
			for (int i = 0; i < preferences.length; i++) {
				if (categoryService.existsById(preferences[i])) {
					category = new Category();
					category.setId(preferences[i]);
					member.addCategory(category);
				}
			}
		}
		return "index";
	}
}
