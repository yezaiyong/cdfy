package com.bsco.app.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bsco.app.model.ActivitiesContent;
import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.app.model.Album;
import com.bsco.app.model.Files;
import com.bsco.app.model.ProActivities;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.ActivityContentService;
import com.bsco.app.service.ActivityPeriodService;
import com.bsco.app.service.ActivityService;
import com.bsco.app.service.AlbumService;
import com.bsco.app.service.FilesService;
import com.bsco.framework.service.impl.EntityServiceImpl;
import com.bsco.framework.upload.FileRepository;
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityServiceImpl extends EntityServiceImpl<ProActivities> implements ActivityService {
	
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FilesService filesService;
	@Autowired
	private ActivityPeriodService aperiodService;
	@Autowired
	private ActivityContentService acService;
	@Autowired
	private AlbumService abService;
	@Override
	public void addActivity_info(String activityName,
			String activitiesPeriodNum, String newsTitle, String newsContent,
			String albumId, String fileImgName, String fileUrlImg,
			String typeImg, String fileVideoName, String fileUrlVideo,
			String typeVideo,HttpServletRequest request,ModelMap map) {
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		//添加活动
		ProActivities pa=new ProActivities();
		pa.setProActivitiesName(activityName);
		pa.setCreateDate(new Date());
		pa.setCreateId(user.getName());
		this.save(pa);
		//文件-视频
		String videoUrl[]=fileUrlVideo.split(",");
		String videoName[]=fileVideoName.split(",");
		String videoType[]=typeVideo.split(",");
		String str_videoId="";
		for (int i = 0; i < videoUrl.length; i++) {
			Files files=new Files();
			files.setType(videoType[i]);
			files.setFileUrl(videoUrl[i]);
			files.setFileName(videoName[i]);
			files.setCreateDate(new Date());
			files.setCreateId(user.getUserName());
			files.setBusinessType("activity");
			filesService.save(files);
			str_videoId+=files.getFileId()+",";
		}
		//添加活动批次
		ActivitiesPeriod ap=new ActivitiesPeriod();
		ap.setActivitiesPeriodNum(activitiesPeriodNum);
		ap.setProActivities(pa);
		ap.setFileId(str_videoId.substring(0, str_videoId.length()-1));
		aperiodService.save(ap);
		//添加活动新闻
		ActivitiesContent ac=new ActivitiesContent();
		ac.setActivitiesContentDesc(newsContent);
		ac.setActivitiesContentTitle(newsTitle);
		ac.setCreateDate(new Date());
		ac.setCreateId(user.getName());
		ac.setActivitiesPeriod(ap);
		acService.save(ac);
		//文件上传表(照片)
		String imgUrl[]=fileUrlImg.split(",");
		String imgName[]=fileImgName.split(",");
		String imgType[]=typeImg.split(",");
		String str_imgId="";
		for (int i = 0; i < imgUrl.length; i++) {
			Files files=new Files();
			files.setBusinessType("activity");
			files.setType(imgType[i]);
			files.setFileUrl(imgUrl[i]);
			files.setFileName(imgName[i]);
			files.setCreateDate(new Date());
			files.setCreateId(user.getUserName());
			filesService.save(files);
			str_imgId+=files.getFileId()+",";
		}
		abService.update("update Album set activitiesPeriod=?,fileId=? where albumId=?", ap,str_imgId.substring(0, str_imgId.length()),Integer.parseInt(albumId));
	}
	@Override
	public void addActivity(String activityName, String activitiesPeriodNum,
			HttpServletRequest request, ModelMap map) {
		// TODO Auto-generated method stub
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		//添加活动
		ProActivities pa=new ProActivities();
		pa.setProActivitiesName(activityName);
		pa.setCreateDate(new Date());
		pa.setCreateId(user.getName());
		this.save(pa);
		ActivitiesPeriod ap=new ActivitiesPeriod();
		ap.setActivitiesPeriodNum(activitiesPeriodNum);
		ap.setProActivities(pa);
		aperiodService.save(ap);
		
	}
	@Override
	public void addActivity_photo(String periodId, String albumId,
			String fileImgName, String fileUrlImg, String typeImg,
			HttpServletRequest request, ModelMap map) {
		// TODO Auto-generated method stub
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String imgUrl[]=fileUrlImg.split(",");
		String imgName[]=fileImgName.split(",");
		String imgType[]=typeImg.split(",");
		String str_imgId="";
		for (int i = 0; i < imgUrl.length; i++) {
			Files files=new Files();
			files.setBusinessType("activity");
			files.setType(imgType[i]);
			files.setFileUrl(imgUrl[i]);
			files.setFileName(imgName[i]);
			files.setCreateDate(new Date());
			files.setCreateId(user.getUserName());
			filesService.save(files);
			str_imgId+=files.getFileId()+",";
		}
		ActivitiesPeriod ap=new ActivitiesPeriod();
		ap.setActivitiesPeriodId(Integer.parseInt(periodId));
		Album a=abService.findById(Integer.parseInt(albumId));
		if(a.getFileId()!=null)
		{
			str_imgId+=a.getFileId();
		}
		abService.update("update Album set activitiesPeriod=?,fileId=? where albumId=?", ap,str_imgId,Integer.parseInt(albumId));
		
	}
	@Override
	public void addActivity_video(String periodId, String fileVideoName,
			String fileUrlVideo, String typeVideo, HttpServletRequest request,
			ModelMap map) {
		// TODO Auto-generated method stub
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String videoUrl[]=fileUrlVideo.split(",");
		String videoName[]=fileVideoName.split(",");
		String videoType[]=typeVideo.split(",");
		String str_videoId="";
		for (int i = 0; i < videoUrl.length; i++) {
			Files files=new Files();
			files.setType(videoType[i]);
			files.setFileUrl(videoUrl[i]);
			files.setFileName(videoName[i]);
			files.setCreateDate(new Date());
			files.setCreateId(user.getUserName());
			files.setBusinessType("activity");
			filesService.save(files);
			str_videoId+=files.getFileId()+",";
		}
		ActivitiesPeriod ap=aperiodService.findById(Integer.parseInt(periodId));
		if(ap.getFileId()!=null)
		{
			str_videoId+=ap.getFileId()+",";
		}
		aperiodService.update("update ActivitiesPeriod set fileId=? where activitiesPeriodId=?", str_videoId.substring(0, str_videoId.length()-1),Integer.parseInt(periodId));
	}

}
