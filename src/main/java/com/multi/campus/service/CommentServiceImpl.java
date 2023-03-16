package com.multi.campus.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.multi.campus.dao.CommentDAO;
import com.multi.campus.dto.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {
	@Inject
	CommentDAO dao;

	@Override
	public int commentInsert(CommentDTO dto) {
		return dao.commentInsert(dto);
	}
}
