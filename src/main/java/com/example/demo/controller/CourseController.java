package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Course;
import com.example.demo.model.CourseEnrolled;
import com.example.demo.model.CourseTopic;
import com.example.demo.repository.CourseEnrollRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseTopicRepository;

@Controller
public class CourseController {
	
	private String courseTitleImage; 
	private String fileName;
	private String UPLOAD_FOLDER;
	
	//Add Course Details
	
		@RequestMapping("/addCourse")	
		String addCourse(Model  model, HttpSession session) {
			
			if(session.getAttribute("sessionTeacher") != null) {
			
			Course course = new Course();
			model.addAttribute("course",course);
			return "addCourse";
			}
			else
			{
				return "redirect:/login-teacher";
			}
		
		}
		
		
		@Autowired
		CourseRepository courseRepo;
		String email;
		@RequestMapping("/addCourseData" )
		String addCourseData(@ModelAttribute("course") Course course,
				@RequestParam("courseTitle") String courseTitle) {
			
			courseRepo.save(course);
			System.out.println("Course Added.");
			
			//System.out.println("The ct is =" + courseTitle);
			
			courseTitleImage = courseTitle;
			
			return "redirect:/addCourseImage";
		}
		
		
		/** Adding Course Image **/
		
		@RequestMapping("/addCourseImage")
		String addImagePage( Model model, HttpSession session) {
			if(session.getAttribute("sessionTeacher") != null) {
				
				model.addAttribute("courseTitle",courseTitleImage);
				
				return "addCourseImage";
			}
			else {
				return "redirect:/login-teacher";
			}
			
		}
		
		
		@RequestMapping("/addImage")
		String addCourseImage(@RequestParam("courseImage") MultipartFile file, 
				RedirectAttributes attributes) {
	
			UPLOAD_FOLDER = "C://Users//91797//Documents//workspace-sts-3.9.12.RELEASE//Online-Education-Webapp-SemiFinal//src//main//resources//static//course_Title_Images//" ;
			fileName = file.getOriginalFilename();
			
			
			if(file.isEmpty()) {
				attributes.addFlashAttribute("message", "Please select a file to upload.");
	            return "redirect:/addCourseImage";
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
			
			return "redirect:/teacher-dashboard";
		}
		
		
		/**Function to update course data after inserting image**/
		void updateCourse() {
			
			Course course = courseRepo.findBycourseTitle(courseTitleImage);
			
			course.setCourseImage(fileName);
			
			courseRepo.save(course);
			
			System.out.println(course);		
		}
		
		
		@RequestMapping("/allCoursesTeacher")	
		String allCourses(Model model, HttpSession session) {
			String teacherEmail1 = (String) session.getAttribute("sessionTeacher") ;
			email=teacherEmail1 ;
			if(teacherEmail1 != null) {
			
				List<Course> list2 = courseRepo.findByAuthorEmail(email);;
				
				model.addAttribute("allCoursesList",list2);
			    model.addAttribute("course",list2.size());
				return "allCoursesTeacher";
			
			}
			else {
				return "redirect:/login-teacher";
			}
		}
		
		
		
		@RequestMapping("/allCoursesHomepage")	
		String allCoursesHomepage(Model model, HttpSession session) {
			
//			if(session.getAttribute("sessionStudent") != null) {
			
			List<Course> list = courseRepo.findByStatus("Published");
			
			model.addAttribute("allCoursesListHomepage",list);
			
			return "coursesHomepage";
//			}
//			else
//			{
//				return "redirect:/login-student";
//			}
		}
		
		
		/** Add Topics **/
		@RequestMapping("/addTopic/{title}")
		String addCourseTopicPage(@PathVariable("title") String title, Model model) {
			
			System.out.println("Course Name : "+ title);
			Course course = courseRepo.findBycourseTitle(title);
			//System.out.println(course);
			model.addAttribute("courseKey", course);
			
			//Creating Empty Object
			CourseTopic topic = new CourseTopic();
			model.addAttribute("topicKey", topic);
			System.out.println("Topic,s empty object created.");
			
			return "addCourseTopic";
		}

		
		@Autowired
		CourseTopicRepository topicRepo;
		
		@RequestMapping("/setTopic")
		String setCourseTopicPage(@ModelAttribute("topicKey") CourseTopic topic) {
			
			//Breaking youTube Link
			String url=topic.getYoutubeLink();
			String urlId[]=url.split("=");
			topic.setYoutubeLink(urlId[1]);
			
			topicRepo.save(topic);
			System.out.println("Topics added.");
		
			return "redirect:allCoursesTeacher";
		}
		
	
		
		/** All Courses titles **/
private String courseTitle;
		
		@RequestMapping("/courseSingle/{courseTitle}")
		String openSingleCourse(@PathVariable("courseTitle") String courseTitle,
				HttpSession session, Model model ) {
			
			if(session.getAttribute("sessionStudent") != null) {
				
				this.courseTitle = courseTitle;
				this.studentEmail = (String) session.getAttribute("sessionStudent");
			
				List<CourseTopic> list = topicRepo.findBycourseTitle(courseTitle);
				model.addAttribute("courseTopics",list);
				
				for (CourseTopic courseTopic : list) {
					
					Course course = courseRepo.findBycourseTitle(courseTopic.getCourseTitle());
					
					model.addAttribute("coursename",courseTopic.getCourseTitle());
					model.addAttribute("authoremail",courseTopic.getAuthorEmail());
					model.addAttribute("courseDescription",course.getDescription());
					model.addAttribute("courseImage",course.getCourseImage());
					
					System.out.println(courseTopic);
				}
				
				return "courseSingle";
			
			}
			else
			{
				return "redirect:/login-student";
			}
		}
		
		static long progress=0;
		String studentEmail;
		
		@Autowired
		CourseEnrollRepository enrollCourseRepo;
		@RequestMapping("/progress/{id}")
		String Progress(Model model ,@PathVariable("id") int id) {
			
			List<CourseTopic> list = topicRepo.findBycourseTitle(courseTitle);
			model.addAttribute("courseTopics",list);
			
			int no_of_topics = list.size();
			int eachTopicPercentage = 100/no_of_topics;
			
			System.out.println("Initial Progress : "+progress);
			
			System.out.println("Video "+ id +"clicked");
			
			progress=progress+eachTopicPercentage;
			System.out.println("Increased Progress : "+progress);
			
			if(progress<=100) {
				CourseEnrolled course = enrollCourseRepo.findByStudentEmailAndEnrolledCourse(studentEmail, courseTitle);
				course.setProgress(progress);
				
				enrollCourseRepo.save(course);
				System.out.println("Progress Updated : " + progress);
			}
			
			
			
//			model.addAttribute("progress",progress);
//			System.out.println(progress);
			
			return "redirect:/courseSingle2/"+courseTitle+"/"+id;
		}
		
		
		@RequestMapping("/courseSingle2/{courseTitle}/{id}")
		String openSingleCourse2(@PathVariable("courseTitle") String courseTitle,
				@PathVariable("id") int id,
				HttpSession session, Model model ) {
			
			if(session.getAttribute("sessionStudent") != null) {
				
				List<CourseTopic> list = topicRepo.findBycourseTitle(courseTitle);
				model.addAttribute("courseTopics",list);
				model.addAttribute("vid",id);
				//progress = progress + eachTopicPercentage;

				for (CourseTopic courseTopic : list) {
					
					Course course = courseRepo.findBycourseTitle(courseTopic.getCourseTitle());
					
					model.addAttribute("coursename",courseTopic.getCourseTitle());
					model.addAttribute("authoremail",courseTopic.getAuthorEmail());
					model.addAttribute("courseDescription",course.getDescription());
					model.addAttribute("courseImage",course.getCourseImage());
					
					System.out.println(courseTopic);
				}
				
				return "courseSingle2";
			
			}
			else
			{
				return "redirect:/login-student";
			}
		}
}
