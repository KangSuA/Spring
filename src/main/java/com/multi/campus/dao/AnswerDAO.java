package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.AnswerDTO;

public interface AnswerDAO {
	//총레코드 수
	public int answerTotalRecord();
	//레코드 전체 선택
	public List<AnswerDTO> answerAllSelect();
	//글등록
	public int answerInsert(AnswerDTO dto);
	//조회수 증가
	public int hitCount(int no);
	//해당글 선택
	public AnswerDTO answerSelect(int no);
	//원글의 ref, lvl, step을 선택한다.
	public AnswerDTO replyDataSelect(int no);
	//원글의 ref가 같고 step 원글의 값보다 크면 step 1증가
	public int stepUp(AnswerDTO orgDataDto);
	//답글등록
	public int replyWrite(AnswerDTO dto);
}
