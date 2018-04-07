package com.bsco.app.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.infomodel.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsco.app.model.DataDict;
import com.bsco.app.model.Users;
import com.bsco.app.service.DataDictService;
import com.bsco.app.service.UserService;
import com.bsco.app.parameter.Constants;
import com.bsco.framework.util.RequestUtils;
import com.mongodb.util.Util;

@Controller
public class DataDictAction {

	
	@Autowired
	public DataDictService dataDictService;

	@Autowired
	public UserService userService;
	/**
	 * 创建数据字典
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/createDict.do")
	public String createDict(String dataType,String DataName,String DataCode,HttpServletRequest request,ModelMap map){
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		DataDict dict=new DataDict();
		dict.setCode(DataCode);
		dict.setName(DataName);
		dict.setType(dataType);
		dict.setCreateDate(new Date());
		dict.setCreateId(user.getUserName());
		try {
			dataDictService.save(dict);
			return "redirect:dateDictList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping("/createDictPage.do")
	public String createDictPage(HttpServletRequest request,ModelMap map){
		return "dataDict/createDict";
	}
	/**
	 * 数据字典列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/dateDictList.do")
	public String usersList(HttpServletRequest request,ModelMap map){
		map.addAllAttributes(RequestUtils.getRequestMap(request));
		return "dataDict/dataDictList";
	}
	/**
	 * 删除用户
	 * @param id
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping("/deleteDict.do")
	public String deleteUser(String id,HttpServletRequest request,ModelMap map){
		DataDict data=dataDictService.findById(Integer.parseInt(id));
			try {
				if(data !=null){
					dataDictService.delete(data);
				}
				return "redirect:dateDictList.do";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
	}
	
	@RequestMapping("/updatePage.do")
	public String updatePage( HttpServletRequest request,ModelMap map){
		return "user/updatePSW";
	}
	
	@RequestMapping("/updatePSW.do")
	public String updatePSW(String id,String password, HttpServletRequest request,ModelMap map){
		Users user=userService.findById(Integer.parseInt(id));
		try {
			if(user !=null){
				user.setPassWord(Util.hexMD5(password.getBytes()));
				userService.update(user);
			}
			return "user/success";
		} catch (Exception e) {
			return "error";
		}
	}
	/**
	 * 首页搜索
	 * @param id
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/homeSearch.do")
	public String homeSearch(HttpServletRequest request,ModelMap map){
		return "home";
	}
}
