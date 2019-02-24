package org.mql.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Module")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "mod_id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "description",columnDefinition="TEXT")
	private String description;

	@Column(name = "rating")
	private float rating;

	@Column(name = "type")
	private String type;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tim_id")
	private Timing timing;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "form_id")
	private Formation formation;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private List<Streaming> streams;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name="member_id")
	private Member teacher;

	public Module() {

	}
	
	public Member getTeacher() {
		return teacher;
	}
	
	public Module(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	
	
	public Module(String title, Member teacher) {
		super();
		this.title = title;
		this.teacher = teacher;
	}

	public Module(String title, String description, float rating, String type) {
		super();
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.type = type;
	}

	public void add(Streaming stream) {
		if (streams == null) {

			streams = new ArrayList<Streaming>();

		}
		streams.add(stream);
		stream.setModule(this);
	}
	
	public String getDescription() {
		return description;
	}

	public Formation getFormation() {
		return formation;
	}

	public int getId() {
		return id;
	}

	public float getRating() {
		return rating;
	}

	public List<Streaming> getStreams() {
		return streams;
	}

	public Timing getTiming() {
		return timing;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setStreams(List<Streaming> streams) {
		this.streams = streams;
	}

	public void setTeacher(Member teacher) {
		this.teacher = teacher;
		if(!teacher.getTeachedModules().contains(this)) {
			teacher.getTeachedModules().add(this);
		}
	}

	public void setTiming(Timing timing) {
		this.timing = timing;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "M:Title : "+title+"M:description : "+description;
	}

}
