package com.bsco.app.service;

import java.util.List;

import com.bsco.app.model.SystemInfo;
import com.bsco.app.model.Users;
import com.bsco.framework.service.EntityService;

public interface SystemInfoService extends EntityService<SystemInfo>{
	
	public SystemInfo getSystemInfoById(Integer identityId);

}
