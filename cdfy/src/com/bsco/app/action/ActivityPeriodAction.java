package com.bsco.app.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.Album;
import com.bsco.app.service.ActivityContentService;
import com.bsco.app.service.ActivityPeriodService;
import com.bsco.app.service.AlbumService;
import com.bsco.app.service.FilesService;

@Controller
public class ActivityPeriodAction {
	@Autowired
	private ActivityPeriodService apService;
	@Autowired
	private ActivityContentService acService;
	@Autowired
	private AlbumService abService;
	@Autowired
	private FilesService fileService;
	@RequestMapping("/delActivityPeriod_info.do")
	@Authority(roles = {"manager"})
	public String delActivityPeriod_info(String periodId,HttpServletRequest request,ModelMap map)
	{
		try {
			apService.delActivity_info(periodId, request, map);
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
	@RequestMapping("/delActivityPeriod_detailinfo.do")
	@Authority(roles = {"manager"})
	public String delActivityPeriod_detailinfo(String id,String delType,HttpServletRequest request,ModelMap map)
	{
		try {
			if("news_del".equals(delType))
			{
				acService.delete(Integer.parseInt(id));
			}
			if("album_del".equals(delType))
			{
				Album a=abService.findById(Integer.parseInt(id));
				if(a.getFileId()!=null)
				{
					fileService.createQuery("delete from Files where fileId in ("+a.getFileId().substring(0, a.getFileId().length()-1)+")").executeUpdate();
				}
				abService.delete(Integer.parseInt(id));
			}
			if("video_del".equals(delType))
			{
				fileService.createQuery("delete from Files where fileId ="+Integer.parseInt(id)).executeUpdate();
			}
			
			return "redirect:succeedActivity.do";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:failedActivity.do";
	}
}
