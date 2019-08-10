package com.revature.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

/* Using JPA Annotations to configure an Entity */
/*
 * @Entity - Tells Hibernate (or any JPA implementing ORM) that this class
 * represents an Entity that can be persisted.
 * 
 * @Table (optional) - Provides optional table configuration
 * 
 * @Id - Indicates the primary key of a table
 * 
 * @GeneratedValue - Annotation used to configure how Hibernate generates values
 * 
 * @Column - Optional Column configuration such as Not Null or column name
 */

@Entity
@Table(name = "professors")
@NamedQuery(name = "favoriteClassQuery", 
	query = "from Professor p WHERE favoriteClass like :favoriteClass")

public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// nullable = false -> Not Null
	// If we don't use an @Column annotation, will this be persisted?
	// -Yes it will be. To prevent tracking, we can use @Transient
	@Column(nullable = false)
	private String subject;


	@Column(name = "favorite_class")
	private String favoriteClass;

	@ManyToOne
	@JoinColumn(name = "school_id")
	private School school;
	
	
	@OneToOne
	@JoinColumn(name = "text_id")
	private Text text;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFavoriteClass() {
		return favoriteClass;
	}

	public void setFavoriteClass(String favoriteClass) {
		this.favoriteClass = favoriteClass;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public List<Professor> getSchools() {
		return schools;
	}

	public void setSchools(List<Professor> schools) {
		this.schools = schools;
	}

	public List<School> getProfessors() {
		return professors;
	}

	public void setProfessors(List<School> professors) {
		this.professors = professors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((favoriteClass == null) ? 0 : favoriteClass.hashCode());
		result = prime * result + id;
		result = prime * result + ((professors == null) ? 0 : professors.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result + ((schools == null) ? 0 : schools.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (favoriteClass == null) {
			if (other.favoriteClass != null)
				return false;
		} else if (!favoriteClass.equals(other.favoriteClass))
			return false;
		if (id != other.id)
			return false;
		if (professors == null) {
			if (other.professors != null)
				return false;
		} else if (!professors.equals(other.professors))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (schools == null) {
			if (other.schools != null)
				return false;
		} else if (!schools.equals(other.schools))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", subject=" + subject + ", favoriteClass=" + favoriteClass + ", school="
				+ school + ", text=" + text + ", schools=" + schools + ", professors=" + professors + "]";
	}

	public Professor(int id, String subject, String favoriteClass, School school, Text text) {
		super();
		this.id = id;
		this.subject = subject;
		this.favoriteClass = favoriteClass;
		this.school = school;
		this.text = text;
	}

	public Professor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@ManyToMany
	@JoinTable(name="professor_school", joinColumns = { @JoinColumn(name="professor_id") },
			inverseJoinColumns = { @JoinColumn(name="school_id")})
	List<Professor> schools;
	
	@ManyToMany
	@JoinTable(name="school_professor", joinColumns = { @JoinColumn(name="school_id")},
			inverseJoinColumns = { @JoinColumn(name="professor_id")})
	List<School> professors;
}

