package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Chat;
import com.example.demo.model.Complain;
import com.example.demo.model.Course;
import com.example.demo.model.CourseTopic;
import com.example.demo.model.LiveSession;
import com.example.demo.model.McqCreate;
import com.example.demo.model.McqTest;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.ComplainRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseTopicRepository;
import com.example.demo.repository.McqCreateRepo;
import com.example.demo.repository.McqTestRepo;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.AuthenticateService;
import com.example.demo.service.LiveSessionImpl;
import com.example.demo.service.TeacherServiceImpl;

@Controller
public class TeacherController {
	
	
	@Autowired
	TeacherServiceImpl db;
	
	@Autowired
	LiveSessionImpl db1;
	
	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	CourseRepository courseRepo;
String email;
	@RequestMapping("teacher-dashboard")	
	String home(Model model,HttpSession session) {
		
    String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		email=teacherEmail ;
		if( teacherEmail != null) {
		List<LiveSession> list = db1.getAllLiveSession();
		List<Course> list2 = courseRepo.findByAuthorEmail(email);
		List<Student> list3 = studRepo.findAll();
		List<String> chats = chatRepo.getDistinctStudentByTeacherName(teacherEmail);
		System.out.println(chats);
		
		model.addAttribute("totalChats", chats.size());
		model.addAttribute("chats", chats);

		model.addAttribute("liveSessionList",list);
		model.addAttribute("allCoursesList",list2);
		model.addAttribute("students",list3);
		model.addAttribute("sessions",list.size());
		model.addAttribute("course",list2.size());
		model.addAttribute("student",list3.size());
		return "teacher-dashboard";
	}
		else {
			return "redirect:/login-teacher";

		}
	}
	
	@GetMapping("/all")	
	String showDetailsPage(Model m) {
		List<Teacher> list = db.getAllTeachers();	 
	    m.addAttribute("teachers", list);
		return "all_teachers";
	}
	
	@RequestMapping("/addteacher")
	String addTeacherPage(Model m) {
		Teacher teacher = new Teacher();
		m.addAttribute("teacher", teacher);
		return "add-teacher";
	}
	
//	@RequestMapping("/saveteacher")
//	String saveTeacher (@ModelAttribute ("teacher") Teacher t, RedirectAttributes redirAttrs) {
//		db.saveTeacher(t);
//	    redirAttrs.addFlashAttribute("success", "Everything went just fine.");
//		return "redirect:/all";
//	}
	
	@RequestMapping("/saveteacher")
	String saveTeacher (@ModelAttribute ("teacher") Teacher t) {
		db.saveTeacher(t);
		System.out.println("Saved successfully!");
		return "redirect:/all";
	}
	
	@GetMapping("/editPage")
	public String editPage() {
		return "redirect:/all";
	}
	
	@GetMapping("/edit/{id}")	
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
	    Teacher teacher = db.getTeacherById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//	    System.out.println("Updated! "+teacher);
	    model.addAttribute("teacher", teacher);
	    return "add-teacher2";
	}
	
	@RequestMapping("/update/{id}")
	public String updateTeacher(@PathVariable("id") int id, Teacher teacher, BindingResult result) {
	    if (result.hasErrors()) {
	        teacher.setId(id);
	        return "redirect:/all";
	    }
	        
	    db.saveTeacher(teacher);
		System.out.println("Updated successfully!");

	    return "redirect:/all";
	}
	
	
	
	@GetMapping("/deletePage")
	public String deletePage() {
		return "redirect:/all";
	}
	
	
	
	@RequestMapping("/delete/{id}")
	public String deleteTeacher(@PathVariable("id") int id) {
		Teacher user = db.getTeacherById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    db.deleteTeacher(user);
		System.out.println("Deleted successfully!");
	    return "redirect:/all";
	}
	
	
	
	@RequestMapping("/login-teacher")
	public String OpenTeacherloginPage() {
		return "teacher-login";
	}
	
	
	
	@Autowired
	AuthenticateService authserv;
	
	@RequestMapping("/check-teacher")
	public String AuthenticateTeacherlogin(@RequestParam("email") String email,
							@RequestParam("password") String password,
							HttpSession session, Model model) {
		if(authserv.teacherAuthenticate(email, password)) {
			session.setAttribute("sessionTeacher", email);
			return "redirect:/teacher-dashboard";
		}else {
			System.out.println("Login Failed !");

			session.setAttribute("errMsg", "Invalid Credentials !!");
			return "redirect:/login-teacher";
		}
	}
	
	
	
	/****Teacher Profile******/
	@Autowired
	TeacherRepository trepo;
	@RequestMapping("/profile")
	String viewteprofile(Model model,HttpSession session){
		
	String teacherEmail = (String) session.getAttribute("sessionTeacher");
	System.out.println(teacherEmail);
	
	if( teacherEmail != null) {
		Teacher teacher = trepo.findByEmail(teacherEmail);
		
		model.addAttribute("teacher",teacher);
		
		List<Course> course = courseRepo.findByAuthorEmail(teacherEmail);
		
		model.addAttribute("course",course);
		
		
		return "teacherprofile";
	}
	else{
		return "redirect:/login-teacher";
	}
	}
	
	@RequestMapping("/savetdetais")
	String profile(@ModelAttribute("teacher") Teacher teacher  ,Model model) 
	{
		trepo.save(teacher);
		return "redirect:/profile";
	}
	
//////////////////UPLOAD TEACHER PROFILE PICTURE//////////////////////
	
private String fileName;
private String UPLOAD_FOLDER;

@RequestMapping("/addteacherImage")
String addCourseImage(@RequestParam("teacherImg") MultipartFile file, 
RedirectAttributes attributes) {

UPLOAD_FOLDER = "D:\\Sample\\Online-Education-Webapp-SemiFinal\\src\\main\\resources\\static\\teacherImg\\" ;
fileName = file.getOriginalFilename();


if(file.isEmpty()) {
attributes.addFlashAttribute("message", "Please select a file to upload.");
return "redirect:/profile";
}

try {
// read and write the file to the selected location-
byte[] bytes = file.getBytes();

Path path = Paths.get(UPLOAD_FOLDER + fileName);
Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
System.out.println("File uploded");
}
catch (Exception e) {

e.printStackTrace();
}

// return success response
attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

updateCourse();

return "redirect:/profile";
}

//Function to update course data after inserting image/
void updateCourse() {

Teacher teacher=trepo.findByEmail(email);
teacher.setTeacherImg(fileName);
trepo.save(teacher);

System.out.println(teacher);		
}
	
	
	@RequestMapping("/logout-teacher")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-teacher";
    }
	

	//Publish Course Page Access
	
	@RequestMapping("/publishCourse")
	String publishCoursePage(Model model) {
		List<Course> list1 = courseRepo.findByAuthorEmail(email);
		
		model.addAttribute("allCoursesList",list1);
	    model.addAttribute("course",list1.size());
		return "publishCourse";
	}
	
	//Publish Course Functionality 
	@RequestMapping("/publish/{id}")
	String publishCourse(@PathVariable("id") int id, Course course) {
		
		course = courseRepo.getReferenceById(id);
		
		course.setStatus("Published");
		
		courseRepo.save(course);
		
		System.out.println(course);
		System.out.println("Course Published.");
		
		return "redirect:/publishCourse";
		
	}
	
	
	@Autowired
	private McqCreateRepo mcqcredb;
	String emails;
	@RequestMapping("/allPublishedCoursesTeacher")
	String allPublishedCourses(Model model, HttpSession session){
		
		String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		
		if( teacherEmail != null) {
			
			//Fetching all published courses for students
		
			List<Course> list1 = courseRepo.findByAuthorEmail(email);
			model.addAttribute("allCoursesList",list1);
			
		    model.addAttribute("course",list1.size());
		    
			List<McqCreate>  c=mcqcredb.findByEmail(teacherEmail);
			model.addAttribute("alltest",c);
			return "allPublishedCoursesTeacher";
		}
		else {
			return "redirect:/login-teacher";
		}
		
	}
	
	@Autowired
	private McqCreateRepo createRepo;
	@RequestMapping("/all-tests")
	String getallmcq(Model model)
	{
		
		List<McqCreate> mcq = createRepo.findAll();
		model.addAttribute("tobj", mcq);
		
		return "AllcreatedMcq";
	}
	
	@Autowired
	McqTestRepo mcqtrepo;
	@RequestMapping("/deletetest/{id}/{title}")
	String Deltest(@PathVariable("id") int id,@PathVariable("title")String title)
	{
		
McqCreate m=mcqcredb.findByTitel(title);
		
		//deleting Mcq
		
		 mcqcredb.deleteById(m.getMid());
		
		List<McqTest> mcq =mcqtrepo.findBytitle(title);
		
		System.out.println(title);
		
		for (McqTest mcq1 : mcq) {
	
			mcqtrepo.deleteById(mcq1.getqId());
		}

		return "redirect:/all-tests";
	}
	
/**************** Chat Functionalities ****************/
	
	
	
	@Autowired
	ChatRepository chatRepo;
	@RequestMapping("/teacherChatPage1")
	String teacherChatPage1open(Model model, HttpSession session){
		
		String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		
		if( teacherEmail != null) {
			
			List<String> chats = chatRepo.getDistinctStudentByTeacherName(teacherEmail);
			model.addAttribute("chats", chats);
			
			return "/teacherChatPage1";
		}
		else {
			return "redirect:/login-teacher";
		}
		
	}
	
	private String student;
	
	@RequestMapping("/teacherChatPage2/{student}")
	String teacherChatPage2(Model model, HttpSession session,
			@PathVariable("student") String student){
		
		this.student=student;
		model.addAttribute("student",student);
		
		String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		
		if( teacherEmail != null) {
			
			List<Chat> allChats = chatRepo.findByStudentNameAndTeacherName(student, teacherEmail);
			System.out.println(allChats);
			model.addAttribute("allChats",allChats);
			
			Chat chat = new Chat();
			model.addAttribute("chat",chat);
			System.out.println("Khali Chat Object Created.");
			
			return "/teacherChatPage2";
		}
		else {
			return "redirect:/login-teacher";
		}
		
	}
	
	
	@RequestMapping("/savechatTeacher")
	String savechat(@ModelAttribute("chat") Chat chat) {
		
System.out.println(chat);
		
		Date date = Calendar.getInstance().getTime();  
		System.out.println("CURRENT DATE : " +date);
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		
		String strDate = dateFormat.format(date);  
		String strTime = timeFormat.format(date);  
		
		chat.setTeacherTime(strTime);
		chat.setTeacherDate(strDate);
		
		System.out.println("with date: "+chat);
		chatRepo.save(chat);
		
		System.out.println("chat saved");
		return "redirect:/teacherChatPage2/"+student;
	}
	
/****** Complains Functionalities ******/
	
	
	@Autowired
	ComplainRepository complainRepo;
	
	@RequestMapping("/TeacherComplainsPage1")
	String TeacherComplainPage1(Model model, HttpSession session)
	{
		String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		
		if( teacherEmail != null) 
		{
		
			List<String> complain = complainRepo.getDistinctStudentByTeacherName(teacherEmail);
			model.addAttribute("complains", complain);
			
			
		return "TeacherComplainsPage1";
		}
		else {
			return "redirect:/login-teacher";
		}
	}
	
	private String students;
	
	
	
	@RequestMapping("/teacherComplainsPage2/{student}")
	String TeacherComplainPage2(Model model, HttpSession session,
			@PathVariable("student") String student)
	{
		this.students=student;
		model.addAttribute("student",student);
		
		String teacherEmail = (String) session.getAttribute("sessionTeacher") ;
		
		if( teacherEmail != null) 
		{
			List<Complain> allComplain = complainRepo.findByStudentNameAndTeacherName(student, teacherEmail);
			System.out.println(allComplain);
			model.addAttribute("allComplains",allComplain);
			
			
			Complain complain = new Complain();
			model.addAttribute("complain",complain);
			System.out.println("Khali Chat Object Created.");
			
		
			return "TeacherComplainsPage2";
		}
		else {
			return "redirect:/login-teacher";
		}
	}
	
	
	@RequestMapping("/savecomplainTeacher")
	String saveComplain(@ModelAttribute("complain") Complain complain)
	{
		
		complainRepo.save(complain);
		System.out.println("complain saved");
		return "redirect:/teacherComplainsPage2/"+students;
	}
	
}
