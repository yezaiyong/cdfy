package com.bsco.app.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsco.app.model.ActivitiesContent;
import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.app.model.ProActivities;
import com.bsco.app.service.ActivityContentService;
import com.bsco.app.service.ActivityPeriodService;
import com.bsco.app.service.ActivityService;


@Controller
public class ActivityNewsAction {
	
	@Autowired
	private ActivityContentService acService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityPeriodService apService;
	
	@RequestMapping("/showActivityNew.do")
	public String showActivityNew(String periodId,String newId,HttpServletRequest request ,ModelMap model)
	{
		ActivitiesContent ac=acService.findById(Integer.parseInt(newId));
		ActivitiesPeriod ap=new ActivitiesPeriod();
		ap.setActivitiesPeriodId(Integer.parseInt(periodId));
		List<ActivitiesContent> list_news=acService.find("from ActivitiesContent where activitiesPeriod=?", ap);
		int nextId=Integer.parseInt(newId);
		int lastId=Integer.parseInt(newId);
		for(int i=0;i<list_news.size();i++)
		{
			int id=list_news.get(i).getActivitiesContentId();
			if(newId.equals(id+""))
			{
				if(i==0)
				{
					if(list_news.size()>1)
					{
						lastId=list_news.get(i+1). getActivitiesContentId();
					}
				}
				else if(i==list_news.size()-1)
				{
					nextId=list_news.get(i-1).getActivitiesContentId();
				}
				else
				{
					lastId=list_news.get(i+1).getActivitiesContentId();
					nextId=list_news.get(i-1).getActivitiesContentId();
				}
					
			}
		}
		List<ProActivities> list_pro=activityService.getAll();
		ActivitiesPeriod p=apService.findById(Integer.parseInt(periodId));
		model.put("list_pro", list_pro);
		model.put("activity", p.getProActivities());
		model.put("news", ac);
		model.put("nextId", nextId);
		model.put("lastId", lastId);
		model.put("aperiod", p);
		return "activity/activityNew";
	}

}
