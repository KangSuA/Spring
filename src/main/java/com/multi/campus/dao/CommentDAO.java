package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.CommentDTO;

public interface CommentDAO {
	//댓글등록
	public int commentInsert(CommentDTO dto);
	//댓글목록
	public List<CommentDTO> commentListSelect(int no);
	//댓글수정
	public int commentUpdate(CommentDTO dto);
	//댓글삭제
	public int commentDelete(int c_no, String userid);
}
