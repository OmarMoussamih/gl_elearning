package org.mql.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;

@Entity
@Table(name = "member")
public class Member implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memb_id")
	private int id;

	@Column(name = "firstName")
	@NotEmpty
	private String firstName;

	@NotEmpty
	@Column(name = "lastName")
	private String lastName;

	@NotEmpty
	@Email
	@Column(name = "email")
	private String email;

	@NotEmpty
	@Column(name = "password")
	private String password;
	
	@Column
	private Boolean activated;
	
	
	// token for email confirmation : 
	@Column(name = "confirmation_token")
	private String confirmationToken;
	//
	

	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH })

	private List<Module> teachedModules;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinTable(name = "following", joinColumns = @JoinColumn(name = "memb_id"), inverseJoinColumns = @JoinColumn(name = "form_id"))
	private List<Formation> followedFormations;

	// roles for security :
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "members_roles", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "memb_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	////////////////

	// categories :
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "teacher_cat", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "cat_id"))
	private List<Category> categories = new ArrayList<>();
	
	//Motivation
	@Column(name = "motivation",columnDefinition="TEXT")
	private String motivation;
	
	
	
	/*
	 * code hajar
	 *************************************************************************************/
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Admission admission;
	
	

	//
	public Member() {

	}

	public Member(String firstName) {
		super();
		this.firstName = firstName;
	}

	public Member(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Member(String firstName, String lastName, String email, String password) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public void addCategory(Category category) {
		categories.add(category);
	}

	public void addModules(Module module) {
		if (teachedModules == null) {

			teachedModules = new ArrayList<Module>();

		}
		teachedModules.add(module);
	}

	public void addRole(Role role) {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		roles.add(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	@OneToMany(mappedBy = "member", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "form_id")
	public List<Formation> getFollowedFormations() {
		return followedFormations;
	}

	public int getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public List<Module> getTeachedModules() {
		return teachedModules;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return activated;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFormations(List<Formation> formations) {
		this.followedFormations = formations;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setModules(List<Module> modules) {
		this.teachedModules = modules;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName;
	}
	
	public String getConfirmationToken() {
		return confirmationToken;
	}
	
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	

	
	public Collection<Role> getRoles() {
		return roles;
	}
	
	public Admission getAdmission() {
		return admission;
	}
	
	public String getMotivation() {
		return motivation;
	}
	
	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
}
