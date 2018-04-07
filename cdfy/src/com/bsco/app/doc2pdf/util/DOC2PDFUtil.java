package com.bsco.app.doc2pdf.util;

import java.io.File;
import java.net.ConnectException;
import java.util.Date;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * @author hetao
 * http://www.cnblogs.com/star-studio/archive/2011/12/09/2282483.html
 *
 */
public class DOC2PDFUtil extends java.lang.Thread  {
     private File inputFile;// 需要转换的文件   
      private File outputFile;// 输出的文件   
      
      public DOC2PDFUtil(File inputFile, File outputFile) {   
         this.inputFile = inputFile;   
         this.outputFile = outputFile;  
     }   
      
     public void docToPdf() {   
         Date start = new Date();   
           
      // 链接 一个运行在8100端口的OpenOffice.org 实例
         OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);   
         try {   
             connection.connect();   
          // 创建一个converter对象并转换格式
             DocumentConverter converter = new OpenOfficeDocumentConverter(connection);   
             converter.convert(inputFile, outputFile);   
         } catch (ConnectException cex) {   
             cex.printStackTrace();   
         } finally {   
             // close the connection   
             if (connection != null) {   
            	// 关闭连接
                 connection.disconnect();   
                 connection = null;   
             }   
         }   
     }   
      
     /**  
       * 由于服务是线程不安全的，所以……需要启动线程  
        */  
     public void run() {   
         this.docToPdf();   
     }   
      
     public File getInputFile() {   
         return inputFile;   
     }   
      
     public void setInputFile(File inputFile) {   
         this.inputFile = inputFile;   
     }   
      
     public File getOutputFile() {   
         return outputFile;   
     }   
      
     public void setOutputFile(File outputFile) {   
         this.outputFile = outputFile;   
     }  
      
     /**
       * 测试main方法
        * @param args
       */
     public static void main(String[] args) {
         File inputFile = new File("f://test//test1.xlsx");
         File outputFile = new File("f://test//test1.pdf");
         DOC2PDFUtil dp=new DOC2PDFUtil(inputFile,outputFile);
         dp.start();
     }  
}