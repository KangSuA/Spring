package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterService {
	public RegisterDTO loginOk(String userid, String userpwd);
	public int idCheckCount(String userid);
	public List<ZipcodeDTO> zipSearch(String doroname);
}
