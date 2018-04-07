package com.bsco.framework.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件存储
 * 
 * 
 * 
 */
public class FileRepository implements ServletContextAware {
	private Logger log = LoggerFactory.getLogger(FileRepository.class);

	public File storeByExt(String path, String ext, MultipartFile file)
			throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return dest;
	}

	public File storeByFilename(String filename, MultipartFile file)
			throws IOException {
		File dest = new File(ctx.getRealPath(filename));
		store(file, dest);
		return dest;
	}

	public File storeByExt(String path, String ext, File file)
			throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return dest;
	}

	public File storeByFilename(String filename, File file)
			throws IOException {
		File dest = new File(ctx.getRealPath(filename));
		store(file, dest);
		return dest;
	}

	public void store(MultipartFile file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			file.transferTo(dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	private void store(File file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			FileUtils.copyFile(file, dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}
	
	public String download(String url, String path) throws Exception {
		String ext = FilenameUtils.getExtension(url).toLowerCase();
		String filename = UploadUtils.generateFilename(ext);
		try {
			HttpClient client = new HttpClient();
			GetMethod get = new GetMethod(url);
			client.executeMethod(get);
			File storeFile = retrieve(path+filename);
			UploadUtils.checkDirAndCreate(storeFile.getParentFile());
			FileOutputStream output = new FileOutputStream(storeFile);
			output.write(get.getResponseBody());
			output.close();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
        return filename;
	}
	
	public File retrieve(String name) {
		return new File(ctx.getRealPath(name));
	}

	private ServletContext ctx;

	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}
}
