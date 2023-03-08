package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardService {
	public int boardInsert(BoardDTO dto);
	public int totalRecord();
	public List<BoardDTO> pageSelect(PagingVO vo);
}
