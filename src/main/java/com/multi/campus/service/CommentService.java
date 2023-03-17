package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.CommentDTO;

public interface CommentService {
	public int commentInsert(CommentDTO dto);
	public List<CommentDTO> commentListSelect(int no);
	public int commentUpdate(CommentDTO dto);
	public int commentDelete(int c_no, String userid);
}
