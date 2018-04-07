package com.bsco.app.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.Files;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.FilesService;
import com.bsco.framework.upload.FileRepository;
import com.bsco.framework.upload.UploadUtils;

@Controller
public class FilesAction {

	@Autowired
	private FilesService fileService;
	@Autowired
	private FileRepository fileRepository;
	@RequestMapping("/showLoginImg_info.do")
	public String showLoginImg_info(HttpServletRequest request,ModelMap model)
	{
		List<Files>list_f=(List<Files>) fileService.queryForListBySql("select * from files where business_type='loginImg'");
		if(list_f==null||list_f.size()<=0)
		{
			Users user = (Users) request.getAttribute(Constants.USER_IN_REQUEST);
			for(int i=1;i<=6;i++)
			{
				Files f=new Files();
				f.setCreateId(user.getUserName());
				f.setCreateDate(new Date());
				f.setBusinessType("loginImg");
				f.setFileName("默认"+i);
				f.setType("第"+i+"张");
				fileService.save(f);
			}
			list_f=(List<Files>) fileService.queryForListBySql("select * from files where business_type='loginImg'");
		}
		model.put("list_f", list_f);
		return "loginImg/loginImgInfo";
	}
	
	@RequestMapping("/toUpdateLoginImg.do")
	@Authority(roles = {"manager"})
	public String toUpdateLoginImg(String fileId,HttpServletRequest request,ModelMap model)
	{
		List<Files> f=(List<Files>) fileService.queryForListBySql("select * from files where business_type='loginImg' and FILE_ID=?", Integer.parseInt(fileId));
		model.put("file", f.get(0));
		return "loginImg/updateLoginImg";
	}
	
	@RequestMapping("/updateLoginImg.do")
	@Authority(roles = {"manager"})
	public String updateLoginImg(String fileId,String title,MultipartFile file,HttpServletRequest request,ModelMap model)
	{
		if (file == null || file.getSize() == 0) {
			fileService.update("update Files set fileName=?,createDate=? where fileId=?", title,new Date(),Integer.parseInt(fileId));
			return "redirect:showLoginImg_info.do";
		} 
		else
		{
			String origName = file.getOriginalFilename();
			File destFile = null;
			try {
				String ext = FilenameUtils.getExtension(origName).toLowerCase(
						Locale.ENGLISH);
				String filename = UploadUtils.generateFilename(ext);
				String path = "upload/" + filename;
				destFile = fileRepository.storeByFilename(path, file);
				fileService.update("update Files set fileName=?,fileUrl=?,createDate=? where fileId=?", title,path,new Date(),Integer.parseInt(fileId));
				return "redirect:showLoginImg_info.do";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		List<Files> f=(List<Files>) fileService.queryForListBySql("select * from files where business_type='loginImg' and FILE_ID=?", Integer.parseInt(fileId));
		model.put("file", f.get(0));
		return "loginImg/updateLoginImg";
	}
	
}
