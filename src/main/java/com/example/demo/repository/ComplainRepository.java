package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Complain;

public interface ComplainRepository extends JpaRepository<Complain, Integer> {
	
	List<Complain> findByStudentNameAndTeacherName(String studentName, String teacherName);
	
	@Query("SELECT DISTINCT com.studentName FROM Complain com WHERE com.teacherName=:teacherName")
	List<String> getDistinctStudentByTeacherName(String teacherName);
	
	@Query("SELECT DISTINCT com.teacherName FROM Complain com WHERE com.studentName=:studentName")
	List<String> getDistinctTeacherByStudentName(String studentName);
	
	@Query("SELECT DISTINCT com.teacherName FROM Complain com")
	List<String> findAllTeacher();
	
	//List<Complain> findByStudentNameAndteacherName(String studentName, String teacherName);
	
//	@Query("SELECT DISTINCT * FROM Complain com WHERE com.studentName=:studentName com.teacherName=:teacherName")
//	List<String> getDistinctByStudentNameAndTeacherName(String studentName, String teacherName);

}
