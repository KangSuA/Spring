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
	//글 수정
	public AnswerDTO getAnswerSelect(int no);
	//글 수정 DB
	public int answerUpdate(AnswerDTO dto);
	//lvl 가져오기
	public int getLevel(int no);
	//글 삭제
	public int answerDelete(int no);
	//삭제 - update 답변글일때
	public int answerDeleteUpdate(int no);
}
