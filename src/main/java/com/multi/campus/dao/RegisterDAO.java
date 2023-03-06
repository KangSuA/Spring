package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterDAO {
	//추상메소드
	public RegisterDTO loginOk(String userid, String userpwd);
	//아이디 중복검사 - 아이디의 개수를 구한다.
	public int idCheckCount(String userid);
	//도로명 검색
	public List<ZipcodeDTO> zipSearch(String doroname);
}
