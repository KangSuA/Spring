package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterService {
	public RegisterDTO loginOk(String userid, String userpwd);
	public int idCheckCount(String userid);
	public List<ZipcodeDTO> zipSearch(String doroname);
	public int registerInsert(RegisterDTO dto);
	public RegisterDTO registerEdit(String userid);
	public int registerEditOk(RegisterDTO dto);
	public String idSearchOk(String username, String tel);
	public String pwdSearchOk(String userid, String username, String tel);
	public String idSearch(String username, String email);
}
