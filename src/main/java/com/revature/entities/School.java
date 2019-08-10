package com.revature.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "schools")
public class School {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "STUDENT_SIZE")
	private double studentSize;

	@Column(name = "HAS_AUDITORIUM")

	private boolean hasAuditorium;


	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "school_id") // Define foreign key column
	List<Professor> staff;

	public School() {
		super();
		// TODO Auto-generated constructor stub
	}


	public School(int id, double studentSize, boolean hasAuditorium, List<Professor> staff) {
		super();
		this.id = id;
		this.studentSize = studentSize;
		this.hasAuditorium = hasAuditorium;
		this.staff = staff;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hasAuditorium ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((staff == null) ? 0 : staff.hashCode());
		long temp;
		temp = Double.doubleToLongBits(studentSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		School other = (School) obj;
		if (hasAuditorium != other.hasAuditorium)
			return false;
		if (id != other.id)
			return false;
		if (staff == null) {
			if (other.staff != null)
				return false;
		} else if (!staff.equals(other.staff))
			return false;
		if (Double.doubleToLongBits(studentSize) != Double.doubleToLongBits(other.studentSize))
			return false;
		return true;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getStudentSize() {
		return studentSize;
	}


	public void setStudentSize(double studentSize) {
		this.studentSize = studentSize;
	}


	public boolean isHasAuditorium() {
		return hasAuditorium;
	}


	public void setHasAuditorium(boolean hasAuditorium) {
		this.hasAuditorium = hasAuditorium;
	}


	public List<Professor> getStaff() {
		return staff;
	}


	public void setStaff(List<Professor> staff) {
		this.staff = staff;
	}


	@Override
	public String toString() {
		return "School [id=" + id + ", studentSize=" + studentSize + ", hasAuditorium=" + hasAuditorium + ", staff="
				+ staff + "]";
	}


}