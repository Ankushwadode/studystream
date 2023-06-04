package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column
	private String teacherName;
	@Column
	private String studentName;
	@Column
	private String teacherMessage;
	@Column
	private String studentMessage;
	@Column
	private String studentTime;
	@Column
	private String teacherTime;
	@Column
	private String studentDate;
	@Column
	private String teacherDate;
	
	
	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public Chat(int id, String teacherName, String studentName, String teacherMessage, String studentMessage,
			String studentTime, String teacherTime, String studentDate, String teacherDate) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.studentName = studentName;
		this.teacherMessage = teacherMessage;
		this.studentMessage = studentMessage;
		this.studentTime = studentTime;
		this.teacherTime = teacherTime;
		this.studentDate = studentDate;
		this.teacherDate = teacherDate;
	}


    

	public Chat(String teacherName, String studentName, String teacherMessage, String studentMessage,
			String studentTime, String teacherTime, String studentDate, String teacherDate) {
		super();
		this.teacherName = teacherName;
		this.studentName = studentName;
		this.teacherMessage = teacherMessage;
		this.studentMessage = studentMessage;
		this.studentTime = studentTime;
		this.teacherTime = teacherTime;
		this.studentDate = studentDate;
		this.teacherDate = teacherDate;
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


	public String getTeacherMessage() {
		return teacherMessage;
	}


	public void setTeacherMessage(String teacherMessage) {
		this.teacherMessage = teacherMessage;
	}


	public String getStudentMessage() {
		return studentMessage;
	}


	public void setStudentMessage(String studentMessage) {
		this.studentMessage = studentMessage;
	}
	
	


	public String getStudentTime() {
		return studentTime;
	}


	public void setStudentTime(String studentTime) {
		this.studentTime = studentTime;
	}


	public String getTeacherTime() {
		return teacherTime;
	}


	public void setTeacherTime(String teacherTime) {
		this.teacherTime = teacherTime;
	}


	public String getStudentDate() {
		return studentDate;
	}


	public void setStudentDate(String studentDate) {
		this.studentDate = studentDate;
	}


	public String getTeacherDate() {
		return teacherDate;
	}


	public void setTeacherDate(String teacherDate) {
		this.teacherDate = teacherDate;
	}


	@Override
	public String toString() {
		return "Chat [id=" + id + ", teacherName=" + teacherName + ", studentName=" + studentName + ", teacherMessage="
				+ teacherMessage + ", studentMessage=" + studentMessage + "]";
	}
	
	
	
	
	
	
}
