package com.bsco.app.service;

import java.util.List;

import com.bsco.framework.service.EntityService;
import com.bsco.app.model.Users;

public interface UserService extends EntityService<Users> {

	public List<Users> getUsersByIdentity(String identityId, String userPwd);

	public List<Users> userLoginByNickName(String nickName, String userPwd);

}
