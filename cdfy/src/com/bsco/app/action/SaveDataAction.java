package com.bsco.app.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.Files;
import com.bsco.app.model.SaveItem;
import com.bsco.app.model.SaveItemFiles;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.FilesService;
import com.bsco.app.service.SaveItemFilesService;
import com.bsco.app.service.SaveItemService;
import com.bsco.app.service.logic.SaveDataServiceLogic;
import com.bsco.framework.upload.FileRepository;
import com.bsco.framework.util.RequestUtils;

@Controller
public class SaveDataAction {
	
	@Autowired 
	private SaveItemService saveItemService;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FilesService filesService;
	@Autowired
	private SaveItemFilesService saveItemFilesService;
	
	@Autowired
	private SaveDataServiceLogic saveDataServiceLogic;
	
	
	/**
	 * 跳转到创建项目页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("saveItemPage.do")
	public String saveItemPage(HttpServletRequest request,ModelMap map){
			return "saveData/createSaveData";
	}
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("saveItemList.do")
	public String saveItemList(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/saveDataList";
	}
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("saveDataById.do")
	public String saveDataById(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/saveDataById";
	}
	
	/**
	 * 保存
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("saveDataItem.do")
	@Authority(roles = {"manager"})
	public String save(String saveItemName,String saveItemType,String saveItemGrade,String saveItemNo,
			String saveItemNumber,String declarationBatch,String itemAddress,String protectionUnit,String remark,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		try {
			this.saveDataServiceLogic.save(saveItemName, saveItemType, saveItemGrade, saveItemNo, saveItemNumber, declarationBatch, itemAddress, protectionUnit, remark, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
			return "redirect:saveItemList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	/**
	 * 修改
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("updateDataItem.do")
	@Authority(roles = {"manager"})
	public String updateDataItem(String itemId,String saveItemName,String saveItemType,String saveItemGrade,String saveItemNo,
			String saveItemNumber,String declarationBatch,String itemAddress,String protectionUnit,String remark,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		
		try {
			this.saveDataServiceLogic.update(itemId, saveItemName, saveItemType, saveItemGrade, saveItemNo, saveItemNumber, declarationBatch, itemAddress, protectionUnit, remark, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
			return "redirect:saveItemList.do";
	}
	
	@RequestMapping("delete.do")
	@Authority(roles = {"manager"})
	public String delete(String itemId,HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		try {
			this.saveDataServiceLogic.delete(itemId);
		} catch (Exception e) {
			return "error";
		}
		return "redirect:saveItemList.do";
	}
	
	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("update.do")
	public String update(HttpServletRequest request,ModelMap model){
		
		return "saveData/update";
	}
	/**
	 * 查看音频
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getAudio.do")
		public String getAudio(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/getFilesAudio";
	}
	/**
	 * 查看视频
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getVideoBySaveItem.do")
		public String getVideo(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/getFilesVideo";
	}
	/**
	 * 查看照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getImg.do")
		public String getImg(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/getFilesImg";
	}
	
	/**
	 * 查看其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getOther.do")
		public String getOther(HttpServletRequest request,ModelMap model){
		model.addAllAttributes(RequestUtils.getRequestMap(request));
		return "saveData/getFilesOther";
	}
}
