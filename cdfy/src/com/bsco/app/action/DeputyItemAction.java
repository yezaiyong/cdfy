package com.bsco.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bsco.app.service.DeputyItemService;
import com.bsco.app.service.FilesService;
import com.bsco.app.service.ItemApplyFilesService;
import com.bsco.app.service.ItemApplyService;
import com.bsco.app.service.logic.DeputyItemServiceLogic;
import com.bsco.framework.upload.FileRepository;
import com.bsco.framework.upload.UploadUtils;
import com.bsco.app.annotation.Authority;
import com.bsco.app.model.Files;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.parameter.JsonResponse;

@Controller
public class DeputyItemAction {
	
	@Autowired
	private DeputyItemService deputyItemService;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FilesService filesService;
	@Autowired
	private ItemApplyFilesService itemApplyFilesService;
	@Autowired
	private ItemApplyService itemApplyService;
	@Autowired
	private DeputyItemServiceLogic deputyItemServiceLogic;
	
	
	/**
	 * 跳转到创建项目页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("deputyItemPage.do")
	@Authority(roles = {"manager"})
	public String deputyItemPage(HttpServletRequest request,ModelMap map){
			return "createItem/createProject1";
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("uploadFiles.do")
	@ResponseBody
	public JsonResponse uploadFiles(MultipartHttpServletRequest request, ModelMap model) {
		Users users = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
		JsonResponse resp = new JsonResponse();
		String origName = "";
		try {
			List<Map> files = new ArrayList<Map>();
			Map<String, MultipartFile> fileMap = request.getFileMap();

			for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
				MultipartFile multipartFile = entry.getValue();
				origName = multipartFile.getOriginalFilename();
				String filePath = UploadUtils.generateFilename("upload", FilenameUtils.getExtension(origName));
				//fileRepository.store(multipartFile, new File(filePath));
				fileRepository.storeByFilename(filePath, multipartFile);
				// 裁剪图片
				// imageScale.resizeFix(file, file, 150, 150);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", FilenameUtils.getBaseName(origName));
				map.put("fileSize", multipartFile.getSize() / 1024);
				map.put("filePath", filePath);
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
	
	/**
	 * 保存
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("save.do")
	@Authority(roles = {"manager"})
	public String save(String deputyItemName,String deputyItemType,String deputyItemGrade,String deputyItemNo,
			String deputyItemNumber,String declarationBatch,String itemAddress,String protectionUnit,String remark,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		try {
			this.deputyItemServiceLogic.save(deputyItemName, deputyItemType, deputyItemGrade, deputyItemNo, deputyItemNumber, declarationBatch, itemAddress, protectionUnit, remark, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
			return "redirect:deputyItemList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 项目申报
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("applySave.do")
	@Authority(roles = {"manager"})
	public String apply(String deputyItemId,String deputyItemName,String deputyItemType,String deputyItemGrade,String deputyItemNo,
			String deputyItemNumber,String declarationBatch,String itemAddress,String protectionUnit,String remark,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		
		try {
			deputyItemServiceLogic.apply(deputyItemId, deputyItemName, deputyItemType, deputyItemGrade, deputyItemNo, deputyItemNumber, declarationBatch, itemAddress, protectionUnit, remark, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
			return "redirect:deputyItemList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping("updateDeputyItem.do")
	@Authority(roles = {"manager"})
	public String applyUpdate(String applyId,String fileId,String deputyItemId,String deputyItemName,String deputyItemType,String deputyItemGrade,String deputyItemNo,
			String deputyItemNumber,String declarationBatch,String itemAddress,String protectionUnit,String remark,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		
		try {
			String fileIds[]=fileId.split(",");
			if(fileIds.length<=0){
				return "error";
			}else{
				deputyItemServiceLogic.update(applyId,fileId,deputyItemId, deputyItemName, deputyItemType, deputyItemGrade, deputyItemNo, deputyItemNumber, declarationBatch, itemAddress, protectionUnit, remark, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
				return "redirect:deputyItemList.do";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 跳转到代表性项目修改页面
	 */
	@RequestMapping("updateDeputyItemPage.do")
	@Authority(roles = {"manager"})
	public String updateDeputyItemPage(HttpServletRequest request,ModelMap model){
		return "createItem/updateDeputyItemPage";
	}
	
	/**
	 * 删除
	 * @param itemId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteItem.do")
	@Authority(roles = {"manager"})
	public String deleteItem(String deputyItemId,HttpServletRequest request,ModelMap model){
		try {
			deputyItemServiceLogic.delete(deputyItemId);
			return "redirect:deputyItemList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 跳转到项目列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyItemList.do")
	public String deputyItemList(HttpServletRequest request,ModelMap model){
		return "deputyItem/deputyItemList";
	}
	
	/**
	 * 根据id跳转到项目页面查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyItemByItemId.do")
	public String deputyItemByItemId(HttpServletRequest request,ModelMap model){
		return "deputyItem/deputyItemByItemId";
	}
	
	/**
	 * 查看申报书列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesBook.do")
		public String getFilesBook(HttpServletRequest request,ModelMap model){
		return "deputyItem/getFilesBook";
	}
	
	/**
	 * 查看申报片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesVideo.do")
		public String getFilesVideo(HttpServletRequest request,ModelMap model){
		return "deputyItem/getFilesVideo";
	}
	/**
	 * 查看申报照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesImg.do")
		public String getFilesImg(HttpServletRequest request,ModelMap model){
		return "deputyItem/getFilesImg";
	}
	
	/**
	 * 查看其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesOther.do")
		public String getFilesOther(HttpServletRequest request,ModelMap model){
		return "deputyItem/getFilesOther";
	}
	
	/**
	 * 跳转到申报页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("apply.do")
	@Authority(roles = {"manager"})
	public String apply(HttpServletRequest request,ModelMap model){
		return "createItem/applyProject";
	}
	
	/**
	 * 查看其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getVideo.do")
		public String getVideo(HttpServletRequest request,ModelMap model){
		return "deputyItem/videoPlay";
	}
	/**
	 * 下载附件
	 * @param filesId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download.do")
	public void downloadReportAnnex(String filesId,HttpServletRequest request, HttpServletResponse response) {
		Files files = filesService.findById(Integer.parseInt(filesId));
		String filesUrl = files.getFileUrl();
		try {
			if(filesUrl!=null)
	    	{
		        String realpath=request.getSession().getServletContext().getRealPath("")+"\\"+filesUrl;
		        File file=new File(realpath);
		
		        response.setContentType("application/x-msdownload");
		
		        response.setContentLength((int)file.length());
		
		        String fileType=filesUrl.substring(filesUrl.lastIndexOf("."),filesUrl.length());
		        
		        response.setHeader("Content-Disposition","attachment;filename="+java.net.URLEncoder.encode(files.getFileName(), "UTF-8")+fileType);
		        
		        FileInputStream fis=new FileInputStream(file);
		        BufferedInputStream buff=new BufferedInputStream(fis);
		
		        byte [] b=new byte[1024];//相当于我们的缓存
		
		        long k=0;//该值用于计算当前实际下载了多少字节
		
		        //从response对象中得到输出流,准备下载
		
		        OutputStream myout=response.getOutputStream();
		
		        //开始循环下载
		
		        while(k<file.length()){
		
		            int j=buff.read(b,0,1024);
		            k+=j;
		
		            //将b中的数据写到客户端的内存
		            myout.write(b,0,j);
		
		        }
		        //将写入到客户端的内存的数据,刷新到磁盘
		        myout.flush();
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 动态获取数据(通过项目级别动态获取申报资料的数据)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDataByGard.do")
    @ResponseBody
    public Map getDataByGard(String type,String deputyItemId,String deputyItemGard, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT * FROM files qqa WHERE EXISTS ( SELECT FILE_ID FROM item_apply_files ww ");
			sql.append("WHERE ww.APPALY_ID = ( SELECT s.APPLY_ID FROM item_apply s ");
			sql.append("WHERE s.DEPUTY_ITEM_ID = '"+deputyItemId+"' and ww.FILE_ID=qqa.FILE_ID and s.apply_gard ='"+deputyItemGard+"') ) and qqa.TYPE ='"+type+"' ");
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
	
	@RequestMapping("getApplyId.do")
	@ResponseBody
	public Map getApplyId(String deputyItemId,String deputyItemGard, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("select APPLY_ID from item_apply s where s.APPLY_GARD ="+deputyItemGard+" AND s.DEPUTY_ITEM_ID="+deputyItemId+"");
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
}

