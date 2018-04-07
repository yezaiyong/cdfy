package com.bsco.framework.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DFSFileUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(DFSFileUtils.class);

	public static byte[] getFileBuffer(InputStream inStream)
			throws IOException {
		byte[] buffer = new byte[256000 * 1024];
		byte[] fileBuffer = new byte[(int) inStream.available()];

		int count = 0;
		int length = 0;

		while ((length = inStream.read(buffer)) != -1) {
			for (int i = 0; i < length; ++i) {
				fileBuffer[count + i] = buffer[i];
			}
			count += length;
		}
		return fileBuffer;
	}

	public static String uploadFile(InputStream inStream, String uploadFileName ,String type)
			throws IOException, MyException {
		int available = inStream.available();
		byte[] fileBuff = getFileBuffer(inStream);
		String fileId = "";
		String fileExtName = "";
		if (uploadFileName.contains(".")) {
			fileExtName = uploadFileName.substring(uploadFileName
					.lastIndexOf(".") + 1);
		} else {
			logger.warn("Fail to upload file, because the format of filename is illegal.");
			return fileId;
		}

		String classPath = DFSFileUtils.class.getResource("").getFile();
		String configFilePath = classPath + File.separator + "fdfs_client.conf";
		
		ClientGlobal.init(configFilePath);
		// 建立连接
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		// 设置元信息
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", uploadFileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(available));
		// 上传文件
		Map map = null;
		try {
			fileId = client.upload_file1(fileBuff, fileExtName, metaList);
		     //map = HttpUploadFileUtil.upload(inStream, uploadFileName, true ,type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		//	trackerServer.close();
		}
		System.out.println("文件上传路径"+map.get("fileUrl"));
		return (String) map.get("fileUrl");
	}

	public static void deleteFile(String file_id) throws Exception {
		String classPath = DFSFileUtils.class.getResource("").getFile();
		String configFilePath = classPath + File.separator + "fdfs_client.conf";

		ClientGlobal.init(configFilePath);

		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();

		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer,
				storageServer);
		storageClient.delete_file1(file_id);
	}

	public static FileInfo getFileInfo(String file_id) throws Exception {
		String classPath = DFSFileUtils.class.getResource("").getFile();
		String configFilePath = classPath + File.separator + "fdfs_client.conf";

		ClientGlobal.init(configFilePath);

		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();

		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer,
				storageServer);
		return storageClient.get_file_info1(file_id);
	}

	public static void downloadFile(String file_id, OutputStream out)
			throws IOException, MyException {
		String classPath = DFSFileUtils.class.getResource("").getFile();
		String configFilePath = classPath + File.separator + "fdfs_client.conf";

		ClientGlobal.init(configFilePath);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer,
				storageServer);

		byte[] bytes = storageClient.download_file1(file_id);
		out.write(bytes);
	}

	public static void downloadFile(String file_id, HttpServletResponse response)
			throws IOException, MyException {
		String classPath = DFSFileUtils.class.getResource("").getFile();
		String configFilePath = classPath + File.separator + "fdfs_client.conf";

		ClientGlobal.init(configFilePath);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer,
				storageServer);
		NameValuePair[] metaList = storageClient.get_metadata1(file_id);
		String fileName = metaList[2].getValue();
		response.setContentType("binary/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gb2312"),
						"iso8859-1"));
		ServletOutputStream servletOutputStream = response.getOutputStream();
		byte[] bytes = storageClient.download_file1(file_id);
		servletOutputStream.write(bytes);
	}

	public static void main(String[] args) throws IOException, MyException {
		File file = new File("C:/Users/JACKLI/Pictures/0.jpg");
		InputStream in = new FileInputStream(file);
		//String fileId = DFSFileUtils.uploadFile(in, file.getName());
		//System.out.println(fileId);
	}
}
