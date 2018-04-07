package com.bsco.app.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants.STAUTS;
import com.bsco.app.service.UserService;
import com.bsco.framework.util.RequestUtils;
import com.mongodb.util.Util;

/**
 * 用户管理操作类
 * @author yzy
 *
 */
@Controller
public class UsersAction {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 创建用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/createUser.do")
	public String createUser(String username,String password,String role,String sex,String loginname,HttpServletRequest request,ModelMap map){
		Users user=new Users();
		user.setName(username);
		user.setPassWord(Util.hexMD5(password.getBytes()));
		user.setSex(sex);
		user.setUserName(loginname);
		user.setRole(role);
		user.setStauts(STAUTS.y);
		userService.save(user);
		return "redirect:usersList.do";
	}
	/**
	 * 跳转到添加用户界面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/create.do")
	public String create(HttpServletRequest request,ModelMap map){
		return "user/createUser";
	}
	/**
	 * 用户列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/usersList.do")
	public String usersList(HttpServletRequest request,ModelMap map){
		map.addAllAttributes(RequestUtils.getRequestMap(request));
		return "user/usersList";
	}
	/**
	 * 删除用户
	 * @param id
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping("/deleteUser.do")
	public String deleteUser(String id,HttpServletRequest request,ModelMap map){
		Users user=userService.findById(Integer.parseInt(id));
		try {
			if(user !=null){
				userService.delete(user);
			}
			return "redirect:usersList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
     * 验证帐号不能重复
     * @param userId
     * @return
     */
    @RequestMapping("/accounts.do")
 	@ResponseBody
 	public Object accounts(@RequestParam("loginname") String userId) {
 		if (userService.getCount("from Users s where s.userName =?", userId) == 0) {
 			return true;
 		}
 		return false;
 	}
	
}
