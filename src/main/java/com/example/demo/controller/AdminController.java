package com.example.demo.controller;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Admin;
import com.example.demo.model.CodeGenerator;
import com.example.demo.model.Complain;
import com.example.demo.model.Course;
import com.example.demo.model.CourseTopic;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CodeGenRepository;
import com.example.demo.repository.ComplainRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseTopicRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.AuthenticateService;
//import com.mysql.cj.Session;
import com.example.demo.service.TeacherServiceImpl;

@Controller
public class AdminController {
	
	@Autowired
	AdminRepository adminService;
	
	@Autowired
	StudentRepository studrepo;

	@Autowired
	TeacherServiceImpl db;
	
	@RequestMapping("/adminDashboard")
	public String AdminDashboard(HttpSession session, Model model) {
		
		if(session.getAttribute("sessionEmail") != null) {
		
			List<Student> list =studrepo.findAll();
			List<Teacher> list2=db.getAllTeachers();
			List<Course> list3 = courseRepo.findAll();
			
			
			
			model.addAttribute("students", list);
			model.addAttribute("teachers",list2);
			model.addAttribute("allCoursesList",list3);
			model.addAttribute("student",list.size());
			model.addAttribute("teacher",list2.size());
			model.addAttribute("course",list3.size());
			
			return "adminDashboard";
		}
		else {
			return "redirect:/login-admin";
		}
		
	}
	
	@RequestMapping("/reg-admin")
	public String registerPage(Model model) {
		
		Admin admin = new Admin();
		model.addAttribute("AdminKey", admin);
		
		System.out.println("Admin's empty object Created.");
		return "page-register";
	}
	
	@RequestMapping("/register-admin")
	public String saveData(@ModelAttribute ("AdminKey") Admin admin ,HttpSession session) {
		
		try {
			adminService.save(admin);
			System.out.println("Admin data saved successfully.");
			return "redirect:/login-admin";
			
		} catch (DataIntegrityViolationException e) {
			
			
			session.setAttribute("sessionKey", "Email exists.");
			
			System.out.println("Email already registered.");
			return "redirect:/reg";
		}

	}
	
	@RequestMapping("/login-admin")
	public String OpenloginPage() {
		return "page-login";
	}
	
	@Autowired
	AuthenticateService authServ;
	@RequestMapping("/logn")
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,
						HttpSession session, Model model) {
		
		if(authServ.authenticate(email, password)) {
			session.setAttribute("sessionEmail", email);
			return "redirect:adminDashboard";
		}else {
			System.out.println("Login Failed !");

			session.setAttribute("errMsg", "Invalid Credentials !!");
			return "redirect:/login-admin";
		}
	}
	
	@RequestMapping("/logout-admin")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-admin";
    }
	
	
	@Autowired
	CourseRepository courseRepo;
	
	@RequestMapping("/allCoursesAdmin")	
	String allCourses(Model model,HttpSession session) {
		
		if(session.getAttribute("sessionEmail") != null) {
			List<Course> list = courseRepo.findAll();
			
			model.addAttribute("allCoursesList",list);
			
			return "allCoursesAdmin";
		}
		else {
			return "redirect:/login-admin";
		}
		
		
	}
	
	@Autowired
	CourseTopicRepository topicRepo;

	
	@RequestMapping("/deleteCourse/{title}")
	String deleteCoursesAdmin(@PathVariable("title") String title, HttpSession session) {
		
		if(session.getAttribute("sessionEmail") != null) {
			//deleting course 
			Course c = courseRepo.findBycourseTitle(title);
			
			courseRepo.deleteById(c.getId());
			
			//deleting topics
			List<CourseTopic> topics = topicRepo.findBycourseTitle(title);
			
			System.out.println(topics);
			
			for (CourseTopic courseTopic : topics) {
				
				topicRepo.deleteById(courseTopic.getTopicId());
			}
			
				
			return "redirect:/allCoursesAdmin";	
		}
		else {
			return "redirect:/login-admin";
		}
		
	}
	
	@Autowired
	CodeGenRepository CodegenRepo;
	
	@RequestMapping("/rancodeGen")
	String codeGenerator(Model model)
	{
		//CodeGenerator codGen = new CodeGenerator();
		//model.addAttribute("codegen", codGen);
		return "randomCodeGen";
	}
	
	@RequestMapping("/codegenerated")
	String codeGeneratored(@RequestParam("code") String code,HttpSession session,CodeGenerator codGen,
							Model model)
	{
		codGen.setCode(code);
		CodegenRepo.save(codGen);
		System.out.println("code generated succssfully...");
		
		session.setAttribute("codeDone", "Code Generated Succssfully...");
		
		List<CodeGenerator> list = CodegenRepo.findAll();
		
		int size =list.size();
		int codeIndex = size-1;
		
		CodeGenerator cg = list.get(codeIndex);
		
		String cod = cg.getCode();
		
		System.out.println(cod);
		
		session.setAttribute("codegen", cod);
		
		return "redirect:/rancodeGen";
	}
	
	@Autowired
	ComplainRepository complainRepo;	
	@Autowired
	TeacherRepository teachRepo;
	@Autowired 
	StudentRepository studRepo;

	@RequestMapping("/adminComplainsPage1")
	String AdminComplainsPage1(Model model)
	{

		List<String> allTeacherList = complainRepo.findAllTeacher();
	
		System.out.println("All Teacher from complain table : "+allTeacherList);
	
		
		model.addAttribute("allComplains",allTeacherList);
		return "AdminComplainsPage1";
	}
	
	@RequestMapping("/AdminComplainsPage2/{teacher}")
	String AdminComplainsPage2(@PathVariable("teacher") String Teacher,Model model)
	{
		List<String> allstudentList = complainRepo.getDistinctStudentByTeacherName(Teacher);
		
		System.out.println(Teacher +":"+ allstudentList);
		model.addAttribute("allStudentList", allstudentList);
		model.addAttribute("Teacher", Teacher);
		
		return "AdminComplainsPage2";
	}
	
	@RequestMapping("/AdminComplainsPage3/{teacher}/{student}")
	String AdminComplainsPage3(@PathVariable("teacher") String Teacher,
						@PathVariable("student") String Student,Model model)
	{
		List<Complain> complains = complainRepo.findByStudentNameAndTeacherName(Student, Teacher);
		model.addAttribute("complains",complains);
		
		return "AdminComplainsPage3";
	}
}
