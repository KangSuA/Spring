package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.AnswerDTO;

public interface AnswerService {
	public int answerTotalRecord();
	public List<AnswerDTO> answerAllSelect();
	public int answerInsert(AnswerDTO dto);
	public int hitCount(int no);
	public AnswerDTO answerSelect(int no);
	public AnswerDTO replyDataSelect(int no);
	public int stepUp(AnswerDTO orgDataDto);
	public int replyWrite(AnswerDTO dto);
}
