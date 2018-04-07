package com.bsco.app.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bsco.framework.upload.DFSFileUtils;
import com.bsco.framework.util.AuthCodeUtils;
import com.bsco.framework.util.CookieUtils;
import com.bsco.framework.util.ResponseUtils;
import com.bsco.app.context.FrontContextInterceptor;
import com.bsco.app.doc2pdf.util.HttpUploadFileUtil;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.parameter.Constants.USER_TYPE;
import com.bsco.app.parameter.JsonResponse;
import com.bsco.app.service.SystemInfoService;
import com.bsco.app.service.UserService;
import com.mongodb.util.Util;

@Controller
public class LoginAction {
	
	private static final Integer EXPIRY = 7 * 24 * 60 * 60;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemInfoService systemInfoService;
	//@Autowired
	//private UserServiceLogic userServiceLogic;
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,ModelMap model) {
		Users users = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
		//已经登录
		if(request.getAttribute(Constants.USER_IN_REQUEST) != null) {
			return "redirect:center.do";
		}
		model.put("systemInfo", systemInfoService.getSystemInfoById(1).getName());
		//return "redirect:"+FrontContextInterceptor.loginUrl;
		return "login";
	}
	
	@RequestMapping("/info.do")
	public String info(HttpServletRequest request,
			HttpServletResponse response) {
		return "info";
	}
	
	@RequestMapping(value = "/hasAccount.do")
	@ResponseBody
	public Object hasAccount(String userId) {
		if (userService.findById(userId) == null) {
			return true;
		}
		return false;
	}
	
//	@RequestMapping("/addguanzhu.do")
//	public void addguanzhu(@RequestParam String uid, HttpServletRequest request,
//			HttpServletResponse response) {
//		Users user = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
//		int i = userServiceLogic.guanzhu(user.getUserId(), uid);
//		ResponseUtils.renderText(response, String.valueOf(i));
//	}
	
	@RequestMapping("/center.do")
	public String home(HttpServletRequest request,
			HttpServletResponse response) {
		Users user = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
		USER_TYPE role = (USER_TYPE) request.getAttribute(Constants.ROLE_IN_REQUEST);
		//return "redirect:"+role.getMainPage();
		return "home";
	}
	
	/*@RequestMapping("/uploadHead.do")
	@ResponseBody
	public JsonResponse upload(MultipartHttpServletRequest request, String albumId) {
		//Users users = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
		JsonResponse resp = new JsonResponse();
		try {
			List<Map> files = new ArrayList<Map>();
			Map<String, MultipartFile> fileMap = request.getFileMap();
			for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
				MultipartFile multipartFile = entry.getValue();
				String origName = multipartFile.getOriginalFilename();
				String fileId = DFSFileUtils.uploadFile(multipartFile.getInputStream(), origName,"attach",new Attach());
				/*
				String ext = FilenameUtils.getExtension(origName).toLowerCase();
				String fileName = UploadUtils.generateFilename(ext);
				File file = fileRepository.storeByFilename(Constants.PHOTO_PATH
						+ fileName, multipartFile);
				.........
				//裁剪图片
				//imageScale.resizeFix(file, file, 150, 150);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", fileId);
				map.put("fileSize", multipartFile.getSize() / 1024);
				files.add(map);
			}
			resp.setObj(files);
			resp.setMessage("上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setState(false);
			resp.setMessage("上传失败");
		}
		return resp;
	}
	
	@RequestMapping("/saveinfo.do")
	public String saveinfo(@ModelAttribute Users dto, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		Users persistent = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
		if (StringUtils.isNotEmpty(dto.getUserPassword())) {
			persistent.setUserPassword(Util.hexMD5(dto.getUserPassword().getBytes()));
		}
		persistent.setDescription(dto.getDescription());
		persistent.setHeadPhotoUrl(dto.getHeadPhotoUrl());
		persistent.setSpaceName(dto.getSpaceName());
		persistent.setTitle(dto.getTitle());
		persistent.setAddress(dto.getAddress());
		persistent.setCompany(dto.getCompany());
		persistent.setName(dto.getName());
		persistent.setMobile(dto.getMobile());
		persistent.setSex(dto.getSex());
		persistent.setJobtitle(dto.getJobtitle());
		persistent.setJobunit(dto.getJobunit());
		persistent.setQq(dto.getQq());
		persistent.setEmail(dto.getEmail());
		persistent.setZipcode(dto.getZipcode());
		persistent.setPhone(dto.getPhone());
		persistent.setIdentityId(dto.getIdentityId());
		//persistent.setIsMingshi(dto.getIsMingshi());
		if(persistent.getRegDate() == null) {
			persistent.setRegDate(new Date());//注册时间
		}
		userService.update(persistent);
		return "redirect:center.do";
	}
	*/
	/*@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Organization org = (Organization)request.getAttribute(Constants.ORG_IN_REQUEST);
		CookieUtils.cancelCookie(request, response, Constants.USERID_IN_COOKIE);
		if(org != null) {
			if(org.getOrgType() == ORG_TYPE.school) {
				return "redirect:"+FrontContextInterceptor.loginUrl;
			} else if(org.getOrgType() == ORG_TYPE.college) {
				return "redirect:collegeHome.action?collegeType="+org.getOrgId();
			} else {
				return "redirect:"+FrontContextInterceptor.loginUrl;
			}
		} else {
			return "redirect:"+FrontContextInterceptor.loginUrl;
		}
	}
	*/
	@RequestMapping("/dologin.do")
	@ResponseBody
	public JSONObject dologin(@RequestParam(defaultValue="") String username, @RequestParam(defaultValue="") String password, Integer rememberMe, HttpServletRequest request, HttpSession session, HttpServletResponse response, ModelMap model) {
		JSONObject json =new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		map.put("passWord", Util.hexMD5(password.getBytes()));
		Users user =userService.queryForObjectByFields(map);
		if (user != null) {
			if(user.getStauts().name().equals("n")){
				json.put("code", "201");
				json.put("msg", "账户登录失败，请联系管理员");
				return json;
			}
			session.setAttribute(Constants.USERID_IN_SESSION, user.getId());
			json.put("code", "200");
			json.put("msg", "登录成功");
			return json;//"redirect:center.do";
		}else{
			json.put("code", "201");
			json.put("msg", "用户名或密码错误!");
		}
//		model.addAttribute("msg", "用户名或密码错误!");
		return json;//"redirect:login.do";
	}
	
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request,
			HttpSession session, 
			HttpServletResponse response) {
		session.removeAttribute(Constants.USERID_IN_SESSION);
		session.invalidate();
		return "redirect:login.do";
	}
	/**
	 * 远程服务器文件下载
	 * @param resourceId
	 * @param request
	 * @param response
	 
	@RequestMapping("/file.do")
	public void file(HttpServletRequest request,String path, HttpServletResponse response) {
		try {
			Resources resource = (Resources) this.resourceService.queryForObject("from Resources t where t.resourceUrl = ? ", "file.do?path="+path);
			HttpUploadFileUtil.downFile(path, response,resource);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * 远程服务器文件下载
	 * @param resourceId
	 * @param request
	 * @param response
	 
	@RequestMapping("/attach.do")
	public void attach(HttpServletRequest request,String path, HttpServletResponse response) {
		try {
			Attach resource = (Attach) this.resourceService.queryForObject("from Attach t where t.filepath = ? ", "attach.do?path="+path);
			HttpUploadFileUtil.downFile2(path, response,resource);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/mymessage.do")
	@ResponseBody
	public Object mymessage(HttpServletRequest request, ModelMap model) {
		Users user = (Users)request.getAttribute(Constants.USER_IN_REQUEST);
		Organization org = (Organization)request.getAttribute(Constants.ORG_IN_REQUEST);
		String orgId = "%";
		if(org != null) {
			orgId = org.getOrgId();
		}
//		Date lastDate = user.getLastDate();
//		user.setLastDate(new Date());
//		userService.update(user);
//		if(lastDate == null) {
//			lastDate = new Date();
//		}
		Map map = new HashMap();
		map.put("guanzhu", userService.queryForObject("select count(*) as guanzhu FROM Follower b WHERE b.id.userId=?", user.getUserId()));
		map.put("sixin", userService.queryForObject("select count(*) as sixin from Message a where (a.messagetext.msgType='Private' and a.id.recId=? and a.status='n') or (a.messagetext.msgType='Public' and a.messagetext.msgGroup='"+orgId+"') or (a.messagetext.msgType='Global')", user.getUserId()));
		map.put("zhuanzai", userService.queryForObject("select count(*) as zhuanzai from Stream a where a.streamClass='zhuanzai' and a.stream.users.userId=?", user.getUserId()));
		map.put("pinglun", userService.queryForObject("select count(*) as pinglun from Comments a where a.destUsers.userId=?", user.getUserId()));
		map.put("gonggao", userService.queryForObject("select count(*) as gonggao from Broadcast a where exists (select 1 from UserOrg b where a.users.userId=b.id.userId and b.organization.orgCode like '%,"+orgId+",%')"));
		return map;
	}
	*/
	public static void main(String[] args) {
		String s = "topic.csdn.net/u/20120604/22/2479ec15-887a-4a7f-9ca6-042d37214302.html";
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		Matcher m = p.matcher(s);
		if(m.find()){
		      System.out.println(m.group());
		}
	}
	
}
