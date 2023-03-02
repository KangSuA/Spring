package com.multi.campus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller //annotation
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		//매개변수에 Model변수 선언, Model에 필요한 데이터를 셋팅, 뷰페이지에서 사용 가능
		model.addAttribute("num",1234);
		model.addAttribute("name","강수아");
		return "home";
	}
	
	// localhost:9090/campus/test?name=홍
	@RequestMapping("/test") //get방식은 생략가능
	public ModelAndView test(HttpServletRequest request) {
		String name = request.getParameter("name");
		System.out.println("test(이름):"+name);
		
		//데이터와 뷰페이지정보를 함께 가질 수 있는 클래스
		ModelAndView mav = new ModelAndView();
		
		//데이터 셋팅
		mav.addObject("num",5678);
		mav.addObject("name",name);
		
		//뷰페이지 셋팅
		mav.setViewName("home");
		
		return mav;
	}
	
	// localhost:9090/campus/test?addr=서울시
	@RequestMapping("/test2")
	public ModelAndView test2(String addr) {
		System.out.println("test2(주소):"+addr);
		
		// /test2를 처리한 후 /test3매핑주소 이동하도록 하기
		// 매핑에서 다른 매핑으로 이동하는 방법
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("num",9999);
		mav.addObject("name","손");
		
		mav.setViewName("redirect:test3");
		
		return mav;
	}
	
	// localhost:9090/campus/test3?num=120&name=세종대왕
	@RequestMapping("/test3")
	public String aaaa(TestDTO dto, Model model) {
		System.out.println("test3(num):"+(dto.getNum()+1000));
		System.out.println(dto.toString());
		
		model.addAttribute("num",7777);
		model.addAttribute("name","홍");
		
		return "home";
	}
	
	// localhost:9090/campus/test4?tel=010-1234-5678
	@GetMapping("/test4")
	public String test4(String tel) {
		System.out.println("test4(연락처):"+tel);
		return "home";
	}
	
}
