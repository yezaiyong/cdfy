package com.bsco.app.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.ActivitiesPeriod;
import com.bsco.app.model.Files;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.parameter.JsonResponse;
import com.bsco.app.service.FilesService;
import com.bsco.app.service.YxResourcesService;
import com.bsco.framework.upload.FileRepository;
import com.bsco.framework.upload.UploadUtils;

@Controller
public class YxResourcesAction {
	
	@Autowired
	private YxResourcesService yxservice;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FilesService fileService;
	
	@RequestMapping("/YxResources_List.do")
	public String YxResources_List(HttpServletRequest request ,ModelMap model)
	{
		return "yxresource/yxresources";
	}
	
	@RequestMapping("/toAddYxResource.do")
	@Authority(roles = {"manager"})
	public String toAddYxResource(String type)
	{
		return "yxresource/addYxResource";
	}
	
	@RequestMapping("/uploadYxResources.do")
	@ResponseBody
	public JsonResponse uploadYxResources(MultipartHttpServletRequest request, HttpServletResponse response,
			ModelMap model, String newfilename) {
		Users user=(Users)request.getAttribute(Constants.USER_IN_REQUEST);
		JsonResponse resp = new JsonResponse();
		String origName = "";
		try {
			List<Map> files = new ArrayList<Map>();
			 Map<String, MultipartFile> fileMap = request.getFileMap();
			 for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet())
			 {
				MultipartFile multipartFile = entry.getValue();
				origName = multipartFile.getOriginalFilename();
//				String filePath = DFSFileUtils.uploadFile(
//						multipartFile.getInputStream(), origName,"yxresources"); 
				String filePath = UploadUtils.generateFilename("upload", FilenameUtils.getExtension(origName));
				fileRepository.storeByFilename(filePath, multipartFile);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", origName);
				map.put("fileSize", multipartFile.getSize() / 1024);
				map.put("filePath", filePath);
				files.add(map);
				
				Files f=new Files();
				if (StringUtils.isNotEmpty(newfilename)) {
					f.setFileName(newfilename);
				} else {
					f.setFileName(FilenameUtils.getBaseName(origName));
				}
				String ext=FilenameUtils.getExtension(origName);
				if("jpg".equals(ext)||"png".equals(ext)||"gif".equals(ext)||"JPG".equals(ext)||"PNG".equals(ext)||"GIF".equals(ext))
				{
					f.setType("img");
				}
				else
				{
					f.setType("video");
				}
				f.setFileUrl(filePath);
				f.setCreateId(user.getName());
				f.setBusinessType("other");
				f.setCreateDate(new Date());
				yxservice.save(f);
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
	
	@RequestMapping("/tovideoPlay_yx.do")
	public String tovideoPlay(String fileId,HttpServletRequest request ,ModelMap model)
	{
		Files f=fileService.findById(Integer.parseInt(fileId));
		model.put("file", f);
		return "yxresource/videoPlay";
	}
	

}
