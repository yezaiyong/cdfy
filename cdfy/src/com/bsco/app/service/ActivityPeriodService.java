package com.bsco.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.framework.service.EntityService;

public interface ActivityPeriodService extends EntityService<ActivitiesPeriod>{

	public void delActivity_info(String periodId,HttpServletRequest request,ModelMap map);
}
