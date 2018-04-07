package com.bsco.app.service.impl;

import org.springframework.stereotype.Service;

import com.bsco.app.model.SystemInfo;
import com.bsco.app.service.SystemInfoService;
import com.bsco.framework.service.impl.EntityServiceImpl;
@SuppressWarnings("unchecked")
@Service
public class SystemInfoServiceImpl extends EntityServiceImpl<SystemInfo> implements SystemInfoService{

	@Override
	public SystemInfo getSystemInfoById(Integer identityId) {
		return (SystemInfo) this.find("from SystemInfo where id=?",
				identityId).get(0);
	}

}
