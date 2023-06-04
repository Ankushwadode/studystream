


package com.example.demo.controller;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;


@Controller


public class CertificateController {
	
	@Autowired
	StudentRepository studRepo;
	@RequestMapping("/certificate/{courseTitle}/{studentName}")
	String Certificate(Model model, @PathVariable("courseTitle") String courseTitle,
			@PathVariable("studentName") String studentEmail) throws IOException {
		
		String path="D:\\Sample\\Online-Education-Webapp-SemiFinal\\src\\main\\resources\\static\\certificates\\Certificate.png";
		
		final BufferedImage image = ImageIO.read(new File(path));

		int width = image.getWidth();
		int height = image.getHeight();
		
		    Font font = new Font("Arial", Font.BOLD, 70);

		    Graphics g = image.getGraphics();
		    g.setFont(font);
		    g.setColor(Color.GREEN);
		    
		    Student student = studRepo.findByEmail(studentEmail); 
		    
		    String studentName = student.getName();
		    
		   // g.drawString("Hello World!", 800, 700);
		    
		    String text=studentName;
		    AffineTransform affinetransform = new AffineTransform();     
		    FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		    
		    int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
		    int textheight = (int)(font.getStringBounds(text, frc).getHeight());
		    
		    System.out.println(textwidth+" "+textheight);
		    g.drawString(text, (width/2)-textwidth/2, (height/2)-textheight/2);
		    g.dispose();
		    System.out.println((width/2)-textwidth);
		    System.out.println((height/2)-textheight);
		    ImageIO.write(image, "png", new File("src\\main\\resources\\static\\gen\\"+text+".png"));
		   
		    
		    model.addAttribute("studentName",studentName);
		    
		    System.out.println("done");
	    
	    return "certificate";
	    
	}
}
