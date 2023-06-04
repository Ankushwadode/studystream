package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="complain")
public class Complain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column
	private String teacherName;
	@Column
	private String studentName;
	@Column
	private String teacherComplainReply;
	@Column
	private String studentComplain;
	
	
	
	public Complain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Complain(int id, String teacherName, String studentName, String teacherComplainReply,
			String studentComplain) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.studentName = studentName;
		this.teacherComplainReply = teacherComplainReply;
		this.studentComplain = studentComplain;
	}


	public Complain(String teacherName, String studentName, String teacherComplainReply, String studentComplain) {
		super();
		this.teacherName = teacherName;
		this.studentName = studentName;
		this.teacherComplainReply = teacherComplainReply;
		this.studentComplain = studentComplain;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getTeacherComplainReply() {
		return teacherComplainReply;
	}
	public void setTeacherComplainReply(String teacherComplainReply) {
		this.teacherComplainReply = teacherComplainReply;
	}
	public String getStudentComplain() {
		return studentComplain;
	}
	public void setStudentComplain(String studentComplain) {
		this.studentComplain = studentComplain;
	}


	@Override
	public String toString() {
		return "Complain [id=" + id + ", teacherName=" + teacherName + ", studentName=" + studentName
				+ ", teacherComplainReply=" + teacherComplainReply + ", studentComplain=" + studentComplain + "]";
	}

}
