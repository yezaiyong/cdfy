package com.bsco.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bsco.app.model.ProActivities;
import com.bsco.framework.service.EntityService;

public interface ActivityService extends EntityService<ProActivities> {
	public void addActivity_info(String activityName,
			String activitiesPeriodNum, String newsTitle, String newsContent,
			String albumId, String fileImgName, String fileUrlImg,
			String typeImg, String fileVideoName, String fileUrlVideo,
			String typeVideo,HttpServletRequest request,ModelMap map);
	
	public void addActivity(String activityName,
			String activitiesPeriodNum,HttpServletRequest request,ModelMap map);
	
	public void addActivity_photo(String periodId,String albumId,String fileImgName, String fileUrlImg,
			String typeImg,HttpServletRequest request,ModelMap map);
	
	public void addActivity_video(String periodId,String fileVideoName, String fileUrlVideo,
			String typeVideo,HttpServletRequest request,ModelMap map);

}
