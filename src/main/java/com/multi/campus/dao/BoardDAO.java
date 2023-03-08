package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardDAO {
	//글등록
	public int boardInsert(BoardDTO dto);
	//총레코드수
	public int totalRecord();
	//해당페이지 선택
	public List<BoardDTO> pageSelect(PagingVO vo);
}
