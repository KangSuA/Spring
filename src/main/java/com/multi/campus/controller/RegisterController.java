package com.multi.campus.controller;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;
import com.multi.campus.service.RegisterService;

@Controller
public class RegisterController {
	@Autowired
	RegisterService service;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	//로그인폼
	@GetMapping("/loginForm")
	public String login() {
		
		return "register/loginForm"; // /WEB-INF/views/register/loginForm.jsp
	}
	//로그인(DB)
	@PostMapping("/loginOk")
	public ModelAndView loginOk(String userid, String userpwd, HttpServletRequest request, HttpSession session) {
		//Session 객체 얻어오기
		//매개변수로 HttpServletRequest request -> session 구하기
		//매개변수로 HttpSession session
		
		RegisterDTO dto = service.loginOk(userid, userpwd);
		//dto -> null : 선택 레코드가 없음 = 로그인 실패
		ModelAndView mav = new ModelAndView();
		if(dto!=null) { //로그인
			session.setAttribute("logId", dto.getUserid());
			session.setAttribute("logName", dto.getUsername());
			session.setAttribute("logStatus", "Y");
			mav.setViewName("redirect:/");
		}else { //로그인 실패
			mav.setViewName("redirect:loginForm");
		}
		return mav;
	}
	//로그아웃 - 세션 제거
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
	//회원가입 폼
	@GetMapping("/join")
	public String join(){
		return "register/join";
	}
	//아이디 중복검사
	@GetMapping("/idCheck")
	public String idCheck(String userid, Model model) {
		//조회
		//아이디의 개수 구하기 - 0 or 1
		int result = service.idCheckCount(userid);
		
		//뷰에서 사용하기 위해 모델 셋팅
		model.addAttribute("userid",userid);
		model.addAttribute("result",result);
		
		return "register/idCheck";
	}
	//우편번호 검색
	@RequestMapping(value="/zipcodeSearch",method=RequestMethod.GET)
	public ModelAndView zipcodeSearch(String doroname) {
		ModelAndView mav = new ModelAndView();
		
		//선택한 주소가 없으면 리턴은 null
		List<ZipcodeDTO> zipList= null;
		if(doroname!=null) {
			zipList = service.zipSearch(doroname);
		}
		
		mav.addObject("zipList",zipList);
		mav.setViewName("register/zipcodeSearch");
		
		return mav;
	}
	@RequestMapping(value="/joinOk", method=RequestMethod.POST)
	public ModelAndView joinOk(RegisterDTO dto) {
		System.out.println(dto.toString());
		
		ModelAndView mav = new ModelAndView();
		//회원가입
		int result = service.registerInsert(dto);
		if(result>0) {//회원가입 성공시
			mav.setViewName("redirect:loginForm");
		}else {//회원가입 실패시
			mav.addObject("msg","회원등록을 실패하였습니다.");
			mav.setViewName("register/joinOkResult");
		}
		return mav;
	}
	//회원정보 수정폼 - session 로그인 아이디에 해당하는 회원정보 select하여 뷰페이지로 이동
	@GetMapping("/joinEdit")
	public ModelAndView joinEdit(HttpSession session) {
		RegisterDTO dto = service.registerEdit((String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto",dto);
		mav.setViewName("register/joinEdit");
		
		return mav;
	}
	//회원정보 수정(DB) - form의 내용과 session의 로그인 아이디로 회원정보를 수정한다.
	@PostMapping("/joinEditOk")
	public ModelAndView joinEditOk(RegisterDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		int cnt = service.registerEditOk(dto);
		if(cnt>0) { //수정성공시 -> db에서 수정된 내용을 보여준다
			mav.setViewName("redirect:joinEdit");
		}else { //수정실패시 -> 이전페이지(알림)
			mav.addObject("msg","회원정보수정을 실패하였습니다.");
			mav.setViewName("register/joinOkResult");
		}
		return mav;
	}
	@GetMapping("/idSearch")
	public String idSearch() {
		return "register/idSearch";
	}
	@PostMapping("idSearchEmailSend")
	@ResponseBody
	public String idSearchEmailSend(RegisterDTO dto) {
		String userid = service.idSearch(dto.getUsername(), dto.getEmail());
		if(userid==null || userid.equals("")) { //아이디가 없으면 존재하지 않는 정보
			return "N";
		}else {
			//아이디가 있으면
			//DB조회한 아이디를 이메일로 보내고 메일보냈따는 정보를 알려준다.
			String emailSubject = "아이디 찾기 결과";
			String emailContent = "<div style='background:pink; margin:50px; padding:50px; border:2px solid gray'>";
			emailContent += "검색한 아이디입니다.";
			emailContent += "아이디 : "+userid;
			emailContent += "</div>";
			try {
				//mimeMessage -> mimeMessageHelper
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				
				//보내는 메일 주소
				messageHelper.setFrom("samdo102@naver.com");
				messageHelper.setTo("");
				messageHelper.setSubject(emailSubject);
				messageHelper.setText("text/html; charset=UTF-8",emailContent);
				
				mailSender.send(message);
				return "Y";
			}catch(Exception e) {
				e.printStackTrace();
				return "N";
			}
			
		}
	}
	
	@PostMapping("/idSearchOk")
	public ModelAndView idSearchOk(String username, String tel1, String tel2, String tel3) {
		ModelAndView mav = new ModelAndView();
		String tel = tel1+"-"+tel2+"-"+tel3;
		String res = service.idSearchOk(username, tel);
		System.out.println(res+"\n"+username+", "+tel);
		if(res!=null) {
			mav.addObject("msg","아이디는 "+res+"입니다.");
		}else {
			mav.addObject("msg","아이디를 찾을 수 없습니다.");		
		}
		mav.setViewName("register/joinOkResult");
		return mav;
	}
	@GetMapping("/pwdSearch")
	public String pwdSearch() {
		return "register/pwdSearch";
	}
	@PostMapping("/pwdSearchOk")
	public ModelAndView pwdSearchOk(String userid, String username, String tel1, String tel2, String tel3) {
		ModelAndView mav = new ModelAndView();
		String tel = tel1+"-"+tel2+"-"+tel3;
		String res = service.pwdSearchOk(userid, username, tel);
		//System.out.println(res+"\n"+username+", "+tel);
		if(res!=null) {
			mav.addObject("msg","비밀번호는 "+res+"입니다.");
		}else {
			mav.addObject("msg","비밀번호를 찾을 수 없습니다.");		
		}
		mav.setViewName("register/joinOkResult");
		return mav;
	}
}
