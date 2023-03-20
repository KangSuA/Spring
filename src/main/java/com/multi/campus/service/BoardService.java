package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardService {
	public int boardInsert(BoardDTO dto);
	public int totalRecord(PagingVO vo);
	public List<BoardDTO> pageSelect(PagingVO vo);
	public BoardDTO boardSelect(int no);
	public BoardDTO boardEditSelect(int no);
	public int boardUpdate(BoardDTO dto);
	public void boardHitCount(int no);
	public int boardDelete(BoardDTO dto);
	public int boardMultiLineDelete(List<Integer> noList);
}
