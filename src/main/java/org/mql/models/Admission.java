package org.mql.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admission")
public class Admission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admission_id")
	private int id ;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member ;
	
	@Column
	Boolean visible;
	//private String domaine;
	//private String motivation;
	
	public Admission() {
		super();
	}
	
	public Admission(Member member) {
		this.member = member;
	}
	
	public Admission(Member member, Boolean visible) {
		super();
		this.member = member;
		this.visible = visible;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
	
}
