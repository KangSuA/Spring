package com.multi.campus.controller;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;
import com.multi.campus.service.BoardService;

// Controller : 뷰언어 사용할 수 없음. 스크립트 필요하면 jsp파일 생성하여 구현.
// ResuController : 프론트 언어를 백엔드에서 기술할 수 있는 기능 제공.
//					반환형을 String으로 하면 뷰페이지 파일명이 아니라 컨텐츠 내용으로 처리한다.
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;
	//게시판목록
	@GetMapping("/boardList")
	public ModelAndView boardList(PagingVO vo) {
		
		ModelAndView mav = new ModelAndView();
		//총레코드수 구하기
		vo.setTotalRecord(service.totalRecord(vo));
		//System.out.println(vo.toString());
		
		//DB조회
		//해당페이지 레코드 선택하기
		mav.addObject("list",service.pageSelect(vo));
		mav.addObject("vo",vo); //뷰페이지로 페이지정보 셋팅
		mav.setViewName("/board/boardList");
		return mav;
	}
	//글쓰기 폼
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/board/boardWrite");
		return mav;
	}
	//글쓰기(DB등록)
	@PostMapping("/boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		dto.setIp(request.getRemoteAddr()); //ip
		dto.setUserid((String)request.getSession().getAttribute("logId")); //로그인한 아이디 구하기
		//글등록 실패시 예외발생
		String htmlTag = "<script>";
		try {
			int res = service.boardInsert(dto);
			htmlTag += "location.href='boardList'";
		}catch(Exception e) {
			htmlTag += "alert('글이 등록되지 않았습니다.');";
			htmlTag += "history.back();";
			e.printStackTrace();
		}
		htmlTag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","html", Charset.forName("UTF-8")));
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		return new ResponseEntity<String>(htmlTag,headers,HttpStatus.OK);
	}
	//글 내용 보기
	@GetMapping("/boardView")
	public ModelAndView boardView(int no, PagingVO vo) {
		ModelAndView mav = new ModelAndView();
		
		BoardDTO dto = service.boardSelect(no);
		mav.addObject("dto",dto);
		mav.addObject("vo",vo);
		service.boardHitCount(no);
		mav.setViewName("/board/boardView");
		
		return mav;
	}
	//수정폼
	@GetMapping("/boardEdit")
	public ModelAndView boardEdit(int no, PagingVO vo) {
		BoardDTO dto = service.boardEditSelect(no);
		ModelAndView mav = new ModelAndView();
		
		String subject = dto.getSubject().replaceAll("\"", "&quot;"); // " -> \"
		subject.replaceAll("\'", "&#39;");
		dto.setSubject(subject);
		
		mav.addObject("dto",dto);
		mav.addObject("vo",vo);
		
		mav.setViewName("/board/boardEdit");
		return mav;
	}
	//수정(DB update)
	@PostMapping("/boardEditOk")
	public ResponseEntity<String> boardEditOk(BoardDTO dto, PagingVO vo, HttpSession session) {
		//no레코드 번호, 로그인한 아이디가 같은 때 업데이트
		dto.setUserid((String)session.getAttribute("logId"));
		
		String bodyTag = "<script>";
		try {
			service.boardUpdate(dto);
			bodyTag += "location.href='boardView?no="+dto.getNo()+"&nowPage="+vo.getNowPage();
			if(vo.getSearchWord()!=null) { //검색어가 있을 때
				bodyTag += "&searchKey="+vo.getSearchKey()+"&searchWord="+vo.getSearchWord();
			}
			bodyTag += "';";
		}catch(Exception e) {
			e.printStackTrace();
			bodyTag += "alert('게시판 글수정 실패하였습니다.');";
			bodyTag += "history.back();";
		}
		bodyTag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","html", Charset.forName("UTF-8")));
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		ResponseEntity<String> entity = new ResponseEntity<String>(bodyTag, headers, HttpStatus.OK);
		
		return entity;
	}
	//글삭제
	@GetMapping("/boardDel")
	public ModelAndView boardDel(BoardDTO dto, PagingVO vo, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		int result = service.boardDelete(dto);
		ModelAndView mav = new ModelAndView();
		mav.addObject("nowPage",vo.getNowPage());
		if(vo.getSearchWord()!=null) {
			mav.addObject("searchKey",vo.getSearchKey());
			mav.addObject("searchWord",vo.getSearchWord());
		}
		if(result>0) {
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("no",dto.getNo());
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
