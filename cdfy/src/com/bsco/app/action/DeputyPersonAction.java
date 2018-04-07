package com.bsco.app.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.Files;
import com.bsco.app.model.PersonApply;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.DataDictService;
import com.bsco.app.service.DeputyItemService;
import com.bsco.app.service.DeputyPersonService;
import com.bsco.app.service.FilesService;
import com.bsco.app.service.PersonApplyFilesService;
import com.bsco.app.service.PersonApplyService;
import com.bsco.app.service.logic.DeputyPersonServiceLogic;

@Controller
public class DeputyPersonAction {
	
	@Autowired
	private DeputyPersonService deputyPersonService;
	@Autowired
	private PersonApplyService personApplyService;
	@Autowired
	private PersonApplyFilesService personApplyFilesService;
	@Autowired
	private DeputyItemService deputyItemService;
	@Autowired
	private DataDictService dataDictService;
	@Autowired
	private DeputyPersonServiceLogic deputyPersonServiceLogic;
	
	@Autowired
	private FilesService filesService;
	
	/**
	 * 跳转到代表性传承人列表页面 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyPersonList.do")
	public String deputyPersonPage(HttpServletRequest request, ModelMap model){
		return "deputyPerson/deputyPersonList";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("applyDeputyPersonPage.do")
	public String apply(HttpServletRequest request,ModelMap model){
		return "deputyPerson/applyDeputyPerson";
	}
	/**
	 * 项目申报
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("applyDeputyPerson.do")
	@Authority(roles = {"manager"})
	public String apply(String deputyPersonId,String deputyPersonName,String deputyItemName,
			String applyGard,String applyBatch,String deputyPersonDesc,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		
		try {
			deputyPersonServiceLogic.applyPerson(deputyPersonId, deputyPersonName, deputyItemName, applyGard, applyBatch, deputyPersonDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
			return "redirect:deputyPersonList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 根据项目获取项目级别（代表性传承人级别必须小于等于项目级别）
	 * @param deputyItemId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getDeputyItemByGard.do")
	@ResponseBody
	public Map getDeputyItemByGard (String deputyItemId, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT s.DEPUTY_ITEM_GRADE FROM deputy_item s where s.DEPUTY_ITEM_ID ='"+Integer.parseInt(deputyItemId)+"' ");
			Query query =deputyItemService.createSQLQuery(sql.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map> list=query.list();
			
			StringBuffer sqlGard=new StringBuffer();
			sqlGard.append("select CODE,NAME from data_dict s where s.code >= '"+list.get(0).get("DEPUTY_ITEM_GRADE")+"' and s.type ='GARD' ");
			Query queryGard =dataDictService.createSQLQuery(sqlGard.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map> listGard=queryGard.list();
			
			map.put("data", listGard);
			map.put("message", 1);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			map.put("message", "获取数据失败");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 跳转到创建传承人页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("createDeputyPerson.do")
	public String createDeputyPerson(HttpServletRequest request,ModelMap model){
		return "deputyPerson/createDeputyPerson";
	}
	/**
	 * 保存代表性传承人
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("saveDeputyPerson.do")
	@Authority(roles = {"manager"})
	public String save(String death,String deputyPersonName,String deputyItemName,String applyGard,String applyBatch,
			String deputyPersonDesc,String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,HttpServletRequest request,ModelMap model){
		try {
			deputyPersonServiceLogic.save(death,deputyPersonName, deputyItemName, applyGard, applyBatch, deputyPersonDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, model);
			return "redirect:deputyPersonList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@RequestMapping("updateDeputyPersonPage.do")
	public String updateDeputyPersonPage(HttpServletRequest  request,ModelMap model){
		return "deputyPerson/updateDeputyPerson";
	}
	/**
	 * 修改代表性传承人
	 * @param reqest
	 * @param model
	 * @return
	 */
	@RequestMapping("updateDeputyPerson.do")
	public String update(String death,String applyId,String fileId,String deputyPersonId,String deputyPersonName,String id,String applyGard,String applyBatch,
			String deputyPersonDesc,String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,HttpServletRequest request,ModelMap model){
		try {
			String fileIds[]=fileId.split(",");
			if(fileIds.length<=0){
				return "error";
			}else{
				deputyPersonServiceLogic.update(death,applyId,fileId,deputyPersonId, deputyPersonName, id, applyGard, applyBatch, deputyPersonDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, model);
				return "redirect:deputyPersonList.do";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 删除代表性传承人
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteDeputyPerson.do")
	public String delete(String deputyPersonId,HttpServletRequest request,ModelMap model){
		try {
			deputyPersonServiceLogic.delete(deputyPersonId);
			return "redirect:deputyPersonList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 根据id跳转到传承人页面查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyPersonById.do")
	public String deputyItemByItemId(HttpServletRequest request,ModelMap model){
		return "deputyPerson/deputyPersonById";
	}
	/**
	 * 查看申报书列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesBookByPerson.do")
		public String getFilesBook(HttpServletRequest request,ModelMap model){
		return "deputyPerson/getFilesBook";
	}
	
	/**
	 * 查看申报片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesVideoByPerson.do")
		public String getFilesVideo(HttpServletRequest request,ModelMap model){
		return "deputyPerson/getFilesVideo";
	}
	/**
	 * 查看申报照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesImgByPerson.do")
		public String getFilesImg(HttpServletRequest request,ModelMap model){
		return "deputyPerson/getFilesImg";
	}
	
	/**
	 * 查看其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesOtherByPerson.do")
		public String getFilesOther(HttpServletRequest request,ModelMap model){
		return "deputyPerson/getFilesOther";
	}
	
	/**
	 * 动态获取数据(通过传承人级别动态获取申报资料的数据)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDataByGardOnPerson.do")
    @ResponseBody
    public Map getDataByGard(String type,String deputyPersonId,String deputyPersonGard, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT * FROM files qqa WHERE EXISTS ( SELECT FILE_ID FROM person_apply_files ww ");
			sql.append("WHERE ww.APPALY_ID = ( SELECT s.APPLY_ID FROM person_apply s ");
			sql.append("WHERE s.DEPUTY_PERSON_ID = '"+deputyPersonId+"' and ww.FILE_ID=qqa.FILE_ID and s.apply_gard ='"+deputyPersonGard+"') ) and qqa.TYPE ='"+type+"' ");
			Query query =filesService.createSQLQuery(sql.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map> list=query.list();
			map.put("data", list);
			map.put("message", 1);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			map.put("message", "获取数据失败");
			e.printStackTrace();
		}
		return map;
    }
	@RequestMapping("getApplyIdByPerson.do")
	@ResponseBody
	public Map getApplyId(String deputyPersonId,String deputyPersonGard, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("select APPLY_ID from person_apply s where s.APPLY_GARD ="+deputyPersonGard+" AND s.DEPUTY_PERSON_ID="+deputyPersonId+"");
			Query query =filesService.createSQLQuery(sql.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map> list=query.list();
			map.put("data", list);
			map.put("message", 1);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			map.put("message", "获取数据失败");
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 获取传承人个人简介信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonInfo.do")
	public String getPersonInfo(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonInfo";
	}
	
	/**
	 * 获取传承人个人照片信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonImg.do")
	public String getPersonImg(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonImg";
	}
	
	/**
	 * 获取传承人视频信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonVideo.do")
	public String getPersonVideo(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonVideo";
	}
	
	/**
	 * 获取传承人音频信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonAudio.do")
	public String getPersonAudio(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonAudio";
	}
	
	/**
	 * 获取传承人音频信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonText.do")
	public String getPersonText(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonText";
	}
	
	/**
	 * 获取传承人传记信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("getPersonLife.do")
	public String getPersonLife(HttpServletRequest request,ModelMap map){
		return "deputyPerson/getPersonLife";
	}
	
	@RequestMapping("getVideoByPerson.do")
	public String getVideo(HttpServletRequest request,ModelMap model){
		return "deputyPerson/videoPlay";
	}
	
	@RequestMapping("uploadPhoto.do")
	public String uploadPhoto(HttpServletRequest request,ModelMap model){
		return "deputyPerson/uploadPhoto";
	}
	
	@RequestMapping("uploadAudio.do")
	public String uploadAudio(HttpServletRequest request,ModelMap model){
		return "deputyPerson/uploadAudio";
	}
	
	@RequestMapping("uploadVideo.do")
	public String uploadVideo(HttpServletRequest request,ModelMap model){
		return "deputyPerson/uploadVideo";
	}
	
	@RequestMapping("uploadText.do")
	public String uploadText(HttpServletRequest request,ModelMap model){
		return "deputyPerson/uploadText";
	}
	@RequestMapping("uploadLife.do")
	public String uploadLife(HttpServletRequest request,ModelMap model){
		return "deputyPerson/uploadLife";
	}
	
	@RequestMapping("savePhoto.do")
	@Authority(roles = {"manager"})
	public String savePhoto(String businessType,String type,String fileImgName,String deputyPersonId,String fileUrlImg,HttpServletRequest request,ModelMap model){
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String audioName[]=fileImgName.split(",");
		String audioUrl[]=fileUrlImg.split(",");
		if(audioUrl.length>0){
			for(int i=0;i<audioName.length;i++){
				Files fles= new Files();
				fles.setBusinessType(businessType);
				fles.setFileUrl(audioUrl[i]);
				fles.setCreateId(user.getUserName());
				fles.setFileName(audioName[i]);
				fles.setType(type);
				fles.setCreateDate(new Date());
				fles.setDeputyPersonId(Integer.parseInt(deputyPersonId));
				try {
					this.filesService.save(fles);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "redirect:getPersonImg.do?deputyPersonId="+deputyPersonId+"&type=img";
	}
	
	@RequestMapping("saveVideo.do")
	@Authority(roles = {"manager"})
	public String saveVideo(String businessType,String types,String fileVideoName,String deputyPersonId,String fileUrlVideo,HttpServletRequest request,ModelMap model){
		
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String audioName[]=fileVideoName.split(",");
		String audioUrl[]=fileUrlVideo.split(",");
		if(audioUrl.length>0){
			for(int i=0;i<audioName.length;i++){
				Files fles= new Files();
				fles.setBusinessType(businessType);
				fles.setFileUrl(audioUrl[i]);
				fles.setCreateId(user.getUserName());
				fles.setFileName(audioName[i]);
				fles.setType(types);
				fles.setCreateDate(new Date());
				fles.setDeputyPersonId(Integer.parseInt(deputyPersonId));
				try {
					this.filesService.save(fles);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "redirect:getPersonVideo.do?deputyPersonId="+deputyPersonId+"&type=video";
		
	}
	
	@RequestMapping("saveAudio.do")
	@Authority(roles = {"manager"})
	public String saveAudio(String businessType,String types,String fileName,String deputyPersonId,String fileUrl,HttpServletRequest request,ModelMap model){
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		
		String audioName[]=fileName.split(",");
		String audioUrl[]=fileUrl.split(",");
		if(audioUrl.length>0){
			for(int i=0;i<audioName.length;i++){
				Files fles= new Files();
				fles.setBusinessType(businessType);
				fles.setFileUrl(audioUrl[i]);
				fles.setCreateId(user.getUserName());
				fles.setFileName(audioName[i]);
				fles.setType(types);
				fles.setCreateDate(new Date());
				fles.setDeputyPersonId(Integer.parseInt(deputyPersonId));
				try {
					this.filesService.save(fles);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "redirect:getPersonAudio.do?deputyPersonId="+deputyPersonId+"&type=audio";
	}
	
	@RequestMapping("saveText.do")
	@Authority(roles = {"manager"})
	public String saveText(String businessType,String types,String fileName,String deputyPersonId,String fileUrl,HttpServletRequest request,ModelMap model){
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String audioName[]=fileName.split(",");
		String audioUrl[]=fileUrl.split(",");
		if(audioUrl.length>0){
			for(int i=0;i<audioName.length;i++){
				Files fles= new Files();
				fles.setBusinessType(businessType);
				fles.setFileUrl(audioUrl[i]);
				fles.setCreateId(user.getUserName());
				fles.setFileName(audioName[i]);
				fles.setType(types);
				fles.setCreateDate(new Date());
				fles.setDeputyPersonId(Integer.parseInt(deputyPersonId));
				try {
					this.filesService.save(fles);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "redirect:getPersonText.do?deputyPersonId="+deputyPersonId+"&type=text";
	}
	
	@RequestMapping("saveLife.do")
	@Authority(roles = {"manager"})
	public String saveLife(String businessType,String types,String fileName,String deputyPersonId,String fileUrl,HttpServletRequest request,ModelMap model){
		Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		String audioName[]=fileName.split(",");
		String audioUrl[]=fileUrl.split(",");
		if(audioUrl.length>0){
			for(int i=0;i<audioName.length;i++){
				Files fles= new Files();
				fles.setBusinessType(businessType);
				fles.setFileUrl(audioUrl[i]);
				fles.setCreateId(user.getUserName());
				fles.setFileName(audioName[i]);
				fles.setType(types);
				fles.setCreateDate(new Date());
				fles.setDeputyPersonId(Integer.parseInt(deputyPersonId));
				try {
					this.filesService.save(fles);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "redirect:getPersonLife.do?deputyPersonId="+deputyPersonId+"&type=life";
	}
	/**
	 * 删除文件
	 * @param filesId
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("deleteFiles.do")
	@Authority(roles = {"manager"})
	public String deleteFiles(String deputyPersonId,String type,String filesId,HttpServletRequest request,ModelMap map){
		String url="";
		Files files= this.filesService.findById(Integer.parseInt(filesId));
		   if(files !=null){
		    	this.filesService.delete(files);
		    }
		   if(type.equals("img")){
			   url="redirect:getPersonImg.do";
		   }else if(type.equals("audio")){
			   url="redirect:getPersonAudio.do";
		   }else if(type.equals("text")){
			   url="redirect:getPersonText.do";
		   }else if(type.equals("video")){
			   url="redirect:getPersonVideo.do";
		   }else if(type.equals("life")){
			   url="redirect:getPersonLife.do";
		   }
		return url;
		  
	}
}
