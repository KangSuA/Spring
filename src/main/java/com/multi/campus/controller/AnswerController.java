package com.multi.campus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.AnswerDTO;
import com.multi.campus.service.AnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
	@Autowired
	AnswerService service;
	
	@GetMapping("/answerList")
	public ModelAndView answerList() {
		ModelAndView mav = new ModelAndView();
		//총레코드
		int totalRecord = service.answerTotalRecord();
		//레코드 선택
		List<AnswerDTO> list = service.answerAllSelect();
		
		mav.addObject("totalRecord",totalRecord);
		mav.addObject("list",list);
		
		mav.setViewName("answer/answerList");
		return mav;
	}
	
	//원글쓰기 폼
	@GetMapping("/answerWrite")
	public ModelAndView answerWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("answer/answerWriteForm");
		return mav;
	}
	
	//원글쓰기 DB
	@PostMapping("/answerWriteOk")
	public ResponseEntity<String> answerWriteOk(AnswerDTO dto, HttpServletRequest request ) {
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");
		//ip
		dto.setIp(request.getRemoteAddr());
		//로그인 id
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		
		try{ //등록
			service.answerInsert(dto);
			
			//글등록
			String body = "<script> location.href='/campus/answer/answerList';</script>";
			entity = new ResponseEntity<String>(body, headers, HttpStatus.OK);
		}catch(Exception e) { //실패
			String body = "<script> ";
			body += "alert('글등록에 실패하였습니다.');"
					+ "history.go(-1); </script>";
			entity = new ResponseEntity<String>(body, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
