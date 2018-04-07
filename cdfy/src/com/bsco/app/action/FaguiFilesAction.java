package com.bsco.app.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bsco.app.annotation.Authority;
import com.bsco.app.model.TextFiels;
import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.FaguiFilesService;
import com.bsco.framework.upload.FileRepository;
import com.bsco.framework.upload.UploadUtils;

@Controller
public class FaguiFilesAction {

	@Autowired
	private FaguiFilesService fgService;
	
	@Autowired
	private FileRepository fileRepository;
	
	@RequestMapping("/faguiFiles_List.do")
	public String faguiFiles_List(HttpServletRequest request ,ModelMap model)
	{
		return "fgfile/legal_documents";
	}
	@RequestMapping("/toAddFgfile.do")
	@Authority(roles = {"manager"})
	public String toAdd(String type)
	{
		if("'法律法规文件'".equals(type))
		{
			return "fgfile/addfile_2";
		}
		return "fgfile/addfile_1";
	}
	
	@RequestMapping("/delFgfiels.do")
	@Authority(roles = {"manager"})
	public void delFgfiels(String id,HttpServletResponse response)
	{
		try {
			fgService.delete(Integer.parseInt(id));
			response.getWriter().print("success");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/addFgfiels.do")
	@Authority(roles = {"manager"})
	public String addFgfiels(String type,String textFilesName,String category,String state,String department,String createDate,String gard,MultipartFile file,
			MultipartHttpServletRequest request, HttpServletResponse response,
			ModelMap model)
	{
		TextFiels fiels=new TextFiels();
		if (file == null || file.getSize() == 0) {
		} else {
			String origName = file.getOriginalFilename();
			File destFile = null;
			try {
					int index = origName.lastIndexOf(".");
					//String title = index < 0 ? origName : origName.substring(0, index);
					String ext = FilenameUtils.getExtension(origName).toLowerCase(
							Locale.ENGLISH);
					String filename = UploadUtils.generateFilename(ext);
					String path = "upload/" + filename;
					destFile = fileRepository.storeByFilename(path, file);
					Users user=(Users)request.getAttribute(Constants.USER_IN_REQUEST);
					fiels.setCreateId(user.getName());
					fiels.setPath(path);
					fiels.setCategory(category);
					fiels.setType(type);
					fiels.setTextFilesName(textFilesName);
					fiels.setState(state);
					fiels.setDepartment(department);
					fiels.setGard(gard);
					fiels.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(createDate));
					fgService.save(fiels);
					return "redirect:succeed.do";
			} catch (Exception e) {
				e.printStackTrace();
				if (destFile != null && destFile.exists()) {
					destFile.delete();
				}
			}
		}
		return "redirect:failed.do";
	}
	@RequestMapping("/succeed.do")
	public String succeed()
	{
		return "fgfile/succeed";
	}
	@RequestMapping("/failed.do")
	public String failed()
	{
		return "fgfile/failed";
	}
	@RequestMapping("/downTextFiels.do")
    public String downTextFiels(String id,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	TextFiels t=fgService.findById(Integer.parseInt(id));
    	String path=t.getPath();
    	if(path!=null)
    	{
//	        System.out.println("fileName:"+fileName);
	        String realpath=request.getSession().getServletContext().getRealPath("")+"\\"+path;
//	        System.out.println("path:"+realpath);
	        //创建file对象
	        File file=new File(realpath);
	
	        //设置response的编码方式
	        response.setContentType("application/x-msdownload");
	
	        //写明要下载的文件的大小
	        response.setContentLength((int)file.length());
	
	        //得到当前下载文件的格式
	        
	        String fileType=path.substring(path.lastIndexOf("."),path.length());
	        
	        //设置附加文件名
	        response.setHeader("Content-Disposition","attachment;filename="+java.net.URLEncoder.encode(t.getTextFilesName(), "UTF-8")+fileType);
	        
	
	        //读出文件到i/o流
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
			return null;
    }
}
