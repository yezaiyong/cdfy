package com.bsco.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bsco.framework.service.impl.EntityServiceImpl;
import com.bsco.app.model.Users;
import com.bsco.app.service.UserService;

@SuppressWarnings("unchecked")
@Service
public class UserServiceImpl extends EntityServiceImpl<Users> implements
		UserService {

	public List<Users> userLoginByNickName(String nickName, String userPwd) {
		return null;
	}

	public List<Users> getUsersByIdentity(String identityId, String userPwd) {
		return this.find("from Users where identityId=? and userPassword=?",
				identityId, userPwd);
	}
	
}
