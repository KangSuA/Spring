package com.multi.campus.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.multi.campus.dao.RegisterDAO;
import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

@Service
public class RegisterServiceImpl implements RegisterService {
	//객체생성
	// @AutoWried, @Inject : interface를 객체로 생성
	@Inject
	RegisterDAO dao;
	
	@Override
	public RegisterDTO loginOk(String userid, String userpwd) {
		return dao.loginOk(userid, userpwd);
	}
	@Override
	public int idCheckCount(String userid){
		return dao.idCheckCount(userid);
	}
	@Override
	public List<ZipcodeDTO> zipSearch(String doroname) {
		return dao.zipSearch(doroname);
	}
	@Override
	public int registerInsert(RegisterDTO dto) {
		return dao.registerInsert(dto);
	}
	@Override
	public RegisterDTO registerEdit(String userid) {
		return dao.registerEdit(userid);
	}
	@Override
	public int registerEditOk(RegisterDTO dto) {
		return dao.registerEditOk(dto);
	}
	@Override
	public String idSearchOk(String username, String tel) {
		return dao.idSearchOk(username, tel);
	}
	@Override
	public String pwdSearchOk(String userid, String username, String tel) {
		return dao.pwdSearchOk(userid, username, tel);
	}
	@Override
	public String idSearch(String username, String email) {
		return dao.idSearch(username, email);
	}
}
