package com.bsco.app.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.app.model.Album;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.AlbumService;
import com.google.gson.Gson;

@Controller
public class AlbumAction {

	@Autowired
	private AlbumService albumService;
	
	@RequestMapping("/addAlbum.do")
	@Authority(roles = {"manager"})
	public void addAlbum(String periodId,String albumName,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			Album a=new Album();
			a.setAlbumName(albumName);
			a.setCreavateDate(new Date());
			Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
			a.setCreateId(user.getName());
			ActivitiesPeriod ap=new ActivitiesPeriod();
			ap.setActivitiesPeriodId(Integer.parseInt(periodId));
			a.setActivitiesPeriod(ap);
			albumService.save(a);
//			List<Album> list_a=(List<Album>) albumService.queryForListBySql("select * from album where activities_period_id=?", Integer.parseInt(periodId));
//			Gson gson=new Gson();
//			response.setContentType("text/plain; charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(a.getAlbumId());
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
}
