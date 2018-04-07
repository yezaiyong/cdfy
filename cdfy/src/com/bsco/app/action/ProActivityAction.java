package com.bsco.app.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bsco.app.annotation.Authority;
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
import com.bsco.framework.bean.Page;
import com.bsco.framework.dao.HibernateDao;

@Controller
public class ProActivityAction {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityPeriodService apService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FilesService fileService;
	@Autowired
	private ActivityContentService acService;
	@Autowired(required = false)
	private HibernateDao hibernateEntityDao;
	
	@RequestMapping("/proActivity_list.do")
	public String proActivity_list(HttpServletRequest request ,ModelMap model)
	{
		List<ProActivities> list_pro=activityService.getAll();
		model.put("list_pro", list_pro);
		if(list_pro!=null&&list_pro.size()>0)
		{
			model.put("activity", list_pro.get(0));
		}
		else
		{
			model.put("activity",null);
		}
		return "activity/pro_activity";
	}
	@RequestMapping("/get_proActivity.do")
	public String get_proActivity(String id,HttpServletRequest request ,ModelMap model)
	{
		ProActivities pa=activityService.findById(Integer.parseInt(id));
		List<ProActivities> list_pro=activityService.getAll();
		model.put("list_pro", list_pro);
		model.put("activity",pa);
		return "activity/pro_activity";
	}
	
	@RequestMapping("/show_activityInfo.do")
	public String show_activityInfo(String activityId,String periodId,HttpServletRequest request ,ModelMap model)
	{
		ProActivities pa=activityService.findById(Integer.parseInt(activityId));
		List<ProActivities> list_pro=activityService.getAll();
		ActivitiesPeriod ap=apService.findById(Integer.parseInt(periodId));
//		String photoId="";
//		for (Album a : ap.getAlbums()) {
//			photoId+=a.getFileId();
//		}
		//List<Files> file_photo=(List<Files>) fileService.queryForListBySql("select * from files where file_id in (?)",photoId.substring(0, photoId.length()-1));
		List<Files> file_video=(List<Files>)fileService.queryForListBySql("select * from files where file_id in ("+ap.getFileId()+")");
		//model.put("photo_list", file_photo);
		model.put("video_list", file_video);
		model.put("list_pro", list_pro);
		model.put("activity",pa);
		model.put("aperiod", ap);
		return "activity/activityinfo";
	}
	
	@RequestMapping("/toAddActivity.do")
	@Authority(roles = {"manager"})
	public String toAddActivity(@RequestParam(defaultValue ="-1")String choseId,HttpServletRequest request ,ModelMap model)
	{
		model.put("activityList", activityService.getAll());
		model.put("choseId", choseId);
		return "activity/addActivity";
	}
	
	@RequestMapping("/addActivity_info.do")
	@Authority(roles = {"manager"})
	public String addActivity_info(String activityName,
			String activitiesPeriodNum, String newsTitle, String newsContent,
			String albumId, String fileImgName, String fileUrlImg,
			String typeImg, String fileVideoName, String fileUrlVideo,
			String typeVideo, HttpServletRequest request, ModelMap map)
	{
	
		try {
			activityService.addActivity_info(activityName, activitiesPeriodNum, newsTitle, newsContent, albumId, fileImgName, fileUrlImg, typeImg, fileVideoName, fileUrlVideo, typeVideo, request, map);
			return "activity/succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "activity/failedActivity.do";
	}
	
	/**
	 * 添加活动
	 * @param activityName
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addActivity.do")
	@Authority(roles = {"manager"})
	public void addActivity(String activityName,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
			//添加活动
			ProActivities pa=new ProActivities();
			pa.setProActivitiesName(activityName);
			pa.setCreateDate(new Date());
			pa.setCreateId(user.getName());
			activityService.save(pa);
			//List<ProActivities> list_a=(List<ProActivities>) activityService.queryForListBySql("select * from pro_activities");
//			Gson gson=new Gson();
//			response.setContentType("text/plain; charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(pa.getProActivitiesId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除活动
	 * @param activityName
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delActivity.do")
	@Authority(roles = {"manager"})
	public void delActivity(String activityId,HttpServletRequest request,HttpServletResponse response,ModelMap map)
	{
		try {
			List<ActivitiesPeriod> list_a=apService.find("from ActivitiesPeriod where proActivities.proActivitiesId=?", Integer.parseInt(activityId));
			for(ActivitiesPeriod ap : list_a)
			{
				apService.delActivity_info(ap.getActivitiesPeriodId()+"", request, map);
			}
			activityService.delete(Integer.parseInt(activityId));
			response.getWriter().print("success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加活动批次
	 * @param activityName
	 * @param activitiesPeriodNum
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/addActivity_period.do")
	@Authority(roles = {"manager"})
	public String addActivity_period(String activityId,String activitiesPeriodNum,  HttpServletRequest request, ModelMap map)
	{
	
		try {
			ActivitiesPeriod ap=new ActivitiesPeriod();
			ProActivities pa=new ProActivities();
			pa.setProActivitiesId(Integer.parseInt(activityId));
			ap.setActivitiesPeriodNum(activitiesPeriodNum);
			ap.setProActivities(pa);
			apService.save(ap);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	
	@RequestMapping("/toAddActivityNews.do")
	@Authority(roles = {"manager"})
	public String toAddActivityNews(String periodId,HttpServletRequest request ,ModelMap model)
	{
		ActivitiesPeriod period=apService.findById(Integer.parseInt(periodId));
		model.put("period", period);
		return "activity/addActivityNews";
	}
	
	/**
	 * 添加活动新闻
	 * @param periodId
	 * @param newsTitle
	 * @param newsContent
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/addActivityNews.do")
	@Authority(roles = {"manager"})
	public String addActivityNews(String periodId,String newsTitle, String newsContent,HttpServletRequest request, ModelMap map)
	{
	
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		try {
			ActivitiesContent ac=new ActivitiesContent();
			ac.setActivitiesContentDesc(newsContent);
			ac.setActivitiesContentTitle(newsTitle);
			ac.setCreateDate(new Date());
			ac.setCreateId(user.getName());
			ActivitiesPeriod ap=new ActivitiesPeriod();
			ap.setActivitiesPeriodId(Integer.parseInt(periodId));
			ac.setActivitiesPeriod(ap);
			acService.save(ac);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	
	@RequestMapping("/toUpdateActivityNews.do")
	@Authority(roles = {"manager"})
	public String toUpdateActivityNews(String periodId,String newsId,HttpServletRequest request ,ModelMap model)
	{
		ActivitiesContent activityNew=acService.findById(Integer.parseInt(newsId));
		ActivitiesPeriod period=apService.findById(Integer.parseInt(periodId));
		model.put("period", period);
		model.put("activityNew", activityNew);
		return "activity/updateActivityNews";
	}
	
	@RequestMapping("/updateActivityNews.do")
	@Authority(roles = {"manager"})
	public String updateActivityNews(String newId,String periodId,String newsTitle, String newsContent,HttpServletRequest request, ModelMap map)
	{
	
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		try {
			ActivitiesContent ac=new ActivitiesContent();
			ac.setActivitiesContentId(Integer.parseInt(newId));
			ac.setActivitiesContentDesc(newsContent);
			ac.setActivitiesContentTitle(newsTitle);
			ac.setCreateDate(new Date());
			ac.setCreateId(user.getName());
			ActivitiesPeriod ap=new ActivitiesPeriod();
			ap.setActivitiesPeriodId(Integer.parseInt(periodId));
			ac.setActivitiesPeriod(ap);
			acService.update(ac);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	
	@RequestMapping("/toAddActivityPhoto.do")
	@Authority(roles = {"manager"})
	public String toAddActivityPhoto(String activityId,String periodId,@RequestParam(defaultValue="-1")String choseId,HttpServletRequest request ,ModelMap model)
	{
		List<Album> list_a=(List<Album>) albumService.queryForListBySql("select * from album where activities_period_id=?", Integer.parseInt(periodId));
		ActivitiesPeriod period=apService.findById(Integer.parseInt(periodId));
		model.put("albumList", list_a); 
		model.put("period", period);
		model.put("choseId", choseId);
		return "activity/addActivityPhoto";
	}
	
	/**
	 * 添加活动照片
	 * @param periodId
	 * @param albumId
	 * @param fileImgName
	 * @param fileUrlImg
	 * @param typeImg
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/addActivityPhoto.do")
	@Authority(roles = {"manager"})
	public String addActivityPhoto(String periodId,String albumId,
			String fileImgName, String fileUrlImg, String typeImg,
			HttpServletRequest request, ModelMap map)
	{
	
		try {
			activityService.addActivity_photo(periodId, albumId, fileImgName, fileUrlImg, typeImg, request, map);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	
	@RequestMapping("/toAddActivityVideo.do")
	@Authority(roles = {"manager"})
	public String toAddActivityVideo(String activityId,String periodId,HttpServletRequest request ,ModelMap model)
	{
		ActivitiesPeriod period=apService.findById(Integer.parseInt(periodId));
		model.put("period", period);
		return "activity/addActivityVideo";
	}
	
	/**
	 * 添加活动视频
	 * @param periodId
	 * @param fileVideoName
	 * @param fileUrlVideo
	 * @param typeVideo
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/addActivityVideo.do")
	@Authority(roles = {"manager"})
	public String addActivityVideo(String periodId, String fileVideoName,
			String fileUrlVideo, String typeVideo, HttpServletRequest request,
			ModelMap map)
	{
	
		try {
			activityService.addActivity_video(periodId, fileVideoName, fileUrlVideo, typeVideo, request, map);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	@RequestMapping("/showPhotos.do")
	public String showPhotos(String albumId,String activityId,Page page, HttpServletRequest request,
			ModelMap map)
	{
		page.setPageSize(12);
		Album a=albumService.findById(Integer.parseInt(albumId));
		String str_fileId="0";
		if(a.getFileId()!=null)
		{
			str_fileId=a.getFileId().substring(0, a.getFileId().length()-1);
		}
		page=hibernateEntityDao.pageQueryBySQL("select * from files where FILE_ID in ("+str_fileId+")", page);
		
		ProActivities pa=activityService.findById(Integer.parseInt(activityId));
		List<ProActivities> list_pro=activityService.getAll();
		map.put("page", page);
		map.put("list_pro", list_pro);
		map.put("activity",pa);
		map.put("album",a);
		return "activity/photos";
	}
	
	@RequestMapping("/to_updateActivity.do")
	@Authority(roles = {"manager"})
	public String to_updateActivity(String periodId,HttpServletRequest request, ModelMap map)
	{
		ActivitiesPeriod ap=apService.findById(Integer.parseInt(periodId));
		map.put("aper", ap);
		return "activity/updateActivity";
	}
	
	@RequestMapping("/updateActivity.do")
	@Authority(roles = {"manager"})
	public String updateActivity(String activityId,String periodId,String activityName,String activitiesPeriodNum,HttpServletRequest request,ModelMap map)
	{
		try {
			activityService.update("update ProActivities set proActivitiesName=? where proActivitiesId=?", activityName,Integer.parseInt(activityId));
			apService.update("update ActivitiesPeriod set activitiesPeriodNum=? where activitiesPeriodId=?", activitiesPeriodNum,Integer.parseInt(periodId));
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	
	
	@RequestMapping("/tovideoPlay.do")
	public String tovideoPlay(String periodId,String fileId,HttpServletRequest request ,ModelMap model)
	{
		ActivitiesPeriod period=apService.findById(Integer.parseInt(periodId));
		Files f=fileService.findById(Integer.parseInt(fileId));
		model.put("file", f);
		model.put("period", period);
		return "activity/videoPlay";
	}
	
	@RequestMapping("/succeedActivity.do")
	public String succeedActivity()
	{
		return "activity/succeed";
	}
	
	@RequestMapping("/failedActivity.do")
	public String failedActivity()
	{
		return "activity/failed";
	}
}
