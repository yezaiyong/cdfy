package com.bsco.app.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.app.model.Album;
import com.bsco.app.service.ActivityContentService;
import com.bsco.app.service.ActivityPeriodService;
import com.bsco.app.service.AlbumService;
import com.bsco.app.service.FilesService;
import com.bsco.framework.service.impl.EntityServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityPeriodServiceImpl extends EntityServiceImpl<ActivitiesPeriod> implements ActivityPeriodService {

	@Autowired
	private AlbumService abService;
	@Autowired
	private FilesService fileService;
	@Autowired
	private ActivityContentService acService;
	@Override
	public void delActivity_info(String periodId, HttpServletRequest request,
			ModelMap map) {
		// TODO Auto-generated method stub
		
		//删除照片
		List<Album> list_a=abService.find("from Album where activitiesPeriod.activitiesPeriodId=?", Integer.parseInt(periodId));
		String ids="";
		for(Album a : list_a)
		{
			if(a.getFileId()!=null)
			{
				ids+=a.getFileId();
			}
		}
		if(ids!=null&&!"".equals(ids))
		{
			ids=ids.substring(0, ids.length()-1);
		}
		if(ids!=null&&!"".equals(ids))
		{
			fileService.createQuery("delete from Files where fileId in ("+ids+")").executeUpdate();
		}
		//删除相册
		abService.createQuery("delete from Album where activitiesPeriod.activitiesPeriodId=?").setParameter(0, Integer.parseInt(periodId)).executeUpdate();
		//删除视频
		ActivitiesPeriod ap=this.findById(Integer.parseInt(periodId));
		ids=ap.getFileId();
		if(ids!=null&&!"".equals(ids))
		{
			fileService.createQuery("delete from Files where fileId in ("+ids+")").executeUpdate();
		}
		//删除新闻
		acService.createQuery("delete from ActivitiesContent where activitiesPeriod.activitiesPeriodId=?").setParameter(0, Integer.parseInt(periodId)).executeUpdate();
		this.delete(Integer.parseInt(periodId));
	}

}
