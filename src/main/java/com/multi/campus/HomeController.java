package com.multi.campus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller //annotation
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	// localhost:9090/campus/test?name=홍
	@RequestMapping("/test") //get방식은 생략가능
	public String test(HttpServletRequest request) {
		String name = request.getParameter("name");
		System.out.println("test(이름):"+name);
		
		return "home";
	}
	
	// localhost:9090/campus/test?addr=서울시
	@RequestMapping("/test2")
	public String test2(String addr) {
		System.out.println("test2(주소):"+addr);
		return "home";
	}
	
	// localhost:9090/campus/test3?num=120&name=세종대왕
	@RequestMapping("/test3")
	public String aaaa(TestDTO dto) {
		System.out.println("test3(num):"+(dto.getNum()+1000));
		System.out.println(dto.toString());
		return "home";
	}
	
	// localhost:9090/campus/test4?tel=010-1234-5678
	@GetMapping("/test4")
	public String test4(String tel) {
		System.out.println("test4(연락처):"+tel);
		return "home";
	}
	
}
