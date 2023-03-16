package com.multi.campus.dao;

import com.multi.campus.dto.CommentDTO;

public interface CommentDAO {
	//댓글등록
	public int commentInsert(CommentDTO dto);
}
