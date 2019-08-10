package com.revature.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="TEXT")
public class Text {
	
	public Text() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Text(int id, double pages, double textWeight, Professor writer) {
		super();
		this.id = id;
		this.pages = pages;
		this.textWeight = textWeight;
		this.writer = writer;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="PAGES")
	private double pages;
	
	@Column(name="TEXT_WEIGHT")
	private double textWeight;

	@OneToOne(mappedBy = "text")
	private Professor writer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPages() {
		return pages;
	}

	public void setPages(double pages) {
		this.pages = pages;
	}

	public double getTextWeight() {
		return textWeight;
	}

	public void setTextWeight(double textWeight) {
		this.textWeight = textWeight;
	}

	public Professor getWriter() {
		return writer;
	}

	public void setWriter(Professor writer) {
		this.writer = writer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(pages);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(textWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
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
		Text other = (Text) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(pages) != Double.doubleToLongBits(other.pages))
			return false;
		if (Double.doubleToLongBits(textWeight) != Double.doubleToLongBits(other.textWeight))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Text [id=" + id + ", pages=" + pages + ", textWeight=" + textWeight + ", writer=" + writer + "]";
	}
	
	

}