package org.mql.controllers;

import java.security.Principal;
import java.util.List;

import javax.mail.MessagingException;

import org.mql.dao.MemberRepository;
import org.mql.dao.AdmissionRepository;
import org.mql.email.DemandeAdmission;
import org.mql.models.Formation;
import org.mql.models.Member;
import org.mql.models.Role;
import org.mql.services.CategoryService;
import org.mql.services.MemberService;
import org.mql.services.RoleService;
import org.mql.models.Admission;
import org.mql.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AdmissionController {
	@Autowired
	private AdmissionRepository admissionRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	private DemandeAdmission mail;

	@Autowired
	MemberService memberService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private RoleService roleService;

	@GetMapping(value = "/admission")
	public String FormulaireAdmission(Model model, Principal principal) {
		Member member = memberService.findByEmail(principal.getName());
		model.addAttribute("member", member);
		//System.out.println(member);
		//model.addAttribute("admission", new Admission());
		List<Category> categories = categoryService.findAll();
		model.addAttribute("cats", categories);
		return "admission/addAdmission";
	}

	// creation de l'email qui sera envoyee a l'email de l'administrateur
	@PostMapping(value = "/saveAdmission")
	public String sendAdmission(Model model, Member member) {
		Member currentMember = memberService.findByEmail(member.getEmail());
		currentMember.setCategories(member.getCategories());
		currentMember.setMotivation(member.getMotivation());
		Admission admission = new Admission(currentMember,true); 
		//admission.setMember(currentMember);
		memberRepository.save(currentMember);
		admissionRepository.save(admission); // TEST if there is possiblity to delete
		
		memberService.sendAdmissionEmail(currentMember);
		
		model.addAttribute("flash","Votre demande a été enregistré! ");
		return "admission/message";
	
	}

	@GetMapping(value = "/dashboard/demands")
	public String index(Model model) {
		List<Admission> Teachers = admissionRepository.findByVisible(true);
		model.addAttribute("listeTeachers", Teachers);
		return "admission/demandes";
	}

	@PostMapping(value = "/acceptation")
	public String accepter(Model model, @RequestParam("id") int id) throws MessagingException {
		Admission teacher = admissionRepository.findById(id).get();
		admissionRepository.deleteById(id);
		Member member = teacher.getMember();
		Role role = roleService.findRoleByName(RoleService.TEACHER);
		member.addRole(role);
		memberRepository.save(member);
		String htmlContent = "<body>\r\n" + "<div align=\"center\" style=\"background-color:lightblue\">\r\n"
				+ " <h3>demande d'admission acceptee </h3>" + " <p> Bonjour, " + member.getFirstName() + " "
				+ member.getLastName()
				+ " votre demande d'etre enseignant dans notre platforme est acceptee par l'administrateur. "
				+ "</p></body>\r\n"
				+ "</html>";
		mail.send(member.getEmail(), "master.qualite.logiciel2019@gmail.com", "demande d'admission acceptee",
				htmlContent);
		return "redirect:/dashboard/demands";
	}

	@PostMapping(value = "/refus")
	public String refuser(Model model, @RequestParam("id") int id) throws MessagingException {
		Admission teacher = admissionRepository.findById(id).get();
		Member member = teacher.getMember();
		admissionRepository.deleteById(id);
		String htmlContent = "<body>\r\n" + "<div align=\"center\" style=\"background-color:lightblue\">\r\n"
				+ " <h3>demande d'admission refusee </h3>" + " <p> Bonjour, " + member.getFirstName() + " "
				+ member.getLastName()
				+ " votre demande d'etre enseignant dans notre platforme est refusee par l'administrateur. "
				+ "</p>"
				+ "</body>\r\n" + "</html>";
		mail.send(member.getEmail(), "master.qualite.logiciel2019@gmail.com", "demande d'admission refusee",
				htmlContent);

		return "redirect:/dashboard/demands";
	}

}
