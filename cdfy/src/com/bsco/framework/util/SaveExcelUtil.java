/**
 * 
 */
package com.bsco.framework.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

public class SaveExcelUtil 
{
//	private static Log log = LogFactory.getLog(ExcelUtil.class);
	private static String shtName = "";
	private static String[] cNames = null;
	private static String[] cLabels = null;
	private static int rpp = 200;
	private static HSSFCellStyle style = null;

	/**
	 * 通过给定的Sql导出Excel文件到Response输出流，需要指定Connection 
	 * 
	 * @param response
	 *            HttpServletResponse Response 
	 * @param conn
	 *            Connection 指定的数据库连接
	 * @param sqlStr
	 *            String 查询的Sql语句
	 * @param sheetName
	 *            String 导出的Excel Sheet名称 
	 * @param columnNames
	 *            String[] 导出的 Excel 列名称
	 * @param rowPerPage
	 *            int 每一个Sheet页的行数 
	 * @throws SQLException
	 *             48.
	 */
	public static void export(Connection conn, String sqlStr, String sheetName, String columnNames[],
			int rowPerPage, OutputStream out) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = conn.prepareStatement(sqlStr);
		rs = ps.executeQuery();

		ResultSetMetaData rsmd = rs.getMetaData();
		if (rowPerPage <= 10000 && rowPerPage >= 1) {
			rpp = rowPerPage;
		}
		if (!"".equals(sheetName) && null != sheetName) {
			shtName = sheetName;
		} else {
			shtName = rsmd.getTableName(0);
		}
		cNames = getColumnNames(rsmd);
		if (null != columnNames) {
			cLabels = columnNames; // compare( columnNames ) ;
		} else {
			cLabels = cNames;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFSheet sheet = createSheet(wb, 1);
		setSheetColumnTitle(sheet);
		int rowCnt = 0;
		int sheetNum = 2;

		while (rs.next()) {
			if (rowCnt == rpp) {
				sheet = createSheet(wb, sheetNum);
				setSheetColumnTitle(sheet);
				rowCnt = 0;
				sheetNum++;
			}
			HSSFRow row = sheet.createRow(rowCnt + 1);
			for (int i = 0; i < cNames.length; i++) {
				HSSFCell cell = row.createCell((short) i);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				String val = rs.getString(cNames[i]);
				if (null == val) {
					val = "";
				}
				cell.setCellValue(val);
			}
			rowCnt++;
		}
		try {
//			OutputStream os = response.getOutputStream();
//			response.reset();
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-disposition", "attachment; filename="+getFileName(shtName));
//			wb.write(os);
//			String savePath = "d:\\";
//			OutputStream fos = new FileOutputStream(new File(savePath,sheetName));
//			fos.close();
			wb.write(out);
		} catch (IOException ex) {
			ex.printStackTrace();
//			log.info("Export Excel file error ! " + ex.getMessage());
		}
	}
	
	public static void export(Session session, String hql, String sheetName, String columnNames[],
			int rowPerPage, OutputStream out) throws SQLException {
		Query query = session.createQuery(hql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		
		if (rowPerPage <= 10000 && rowPerPage >= 1) {
			rpp = rowPerPage;
		}
		if (!"".equals(sheetName) && null != sheetName) {
			shtName = sheetName;
		} else {
			shtName = "";
		}
		cNames = query.getReturnAliases();
		if (null != columnNames) {
			cLabels = columnNames; // compare( columnNames ) ;
		} else {
			cLabels = cNames;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFSheet sheet = createSheet(wb, 1);
		setSheetColumnTitle(sheet);
		int rowCnt = 0;
		int sheetNum = 2;

		for(Map<String, Object> map : list) {
			if (rowCnt == rpp) {
				sheet = createSheet(wb, sheetNum);
				setSheetColumnTitle(sheet);
				rowCnt = 0;
				sheetNum++;
			}
			HSSFRow row = sheet.createRow(rowCnt + 1);
			for (int i = 0; i < cNames.length; i++) {
				HSSFCell cell = row.createCell((short) i);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				Object val = map.get(cNames[i]);
				if (null == val) {
					val = "";
				}
				cell.setCellValue(val.toString());
			}
			rowCnt++;
		}
		try {
//			OutputStream os = response.getOutputStream();
//			response.reset();
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-disposition", "attachment; filename="+getFileName(shtName));
//			wb.write(os);
//			String savePath = "d:\\";
//			OutputStream fos = new FileOutputStream(new File(savePath,sheetName));
//			fos.close();
			wb.write(out);
		} catch (IOException ex) {
			ex.printStackTrace();
//			log.info("Export Excel file error ! " + ex.getMessage());
		}
	}

	/**
	 *设置Sheet页的列属性
	 * @param sht
	 * HSSFSheet 124.
	 */
	private static void setSheetColumnTitle(HSSFSheet sht) {
		HSSFRow row = sht.createRow(0);
		for (int i = 0; i < cLabels.length; i++) {
			HSSFCell cell = row.createCell((short) (i));
//			cell.setEncoding(HSSFCell.ENCODING_COMPRESSED_UNICODE);
			cell.setCellValue(cLabels[i]);
			cell.setCellStyle(style);
		}
	}

	/**
	 *  获得源数据中的列名称
	 * @param rsmd
	 * ResultSetMetaData
	 * @return String[] 139.
	 */
	private static String[] getColumnNames(ResultSetMetaData rsmd) {
		try {
			StringBuffer result = new StringBuffer("");
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				result.append(rsmd.getColumnLabel(i)).append(",");
			}
			if (result.length() > 0) {
				return result.substring(0, result.length() - 1).toString().split(",");
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 *创建一个Sheet页并返回该对象 
	 * @param wb
	 * HSSFWorkbook 
	 * @param seq int 
	 * @return HSSFSheet 
	 */
	private static HSSFSheet createSheet(HSSFWorkbook wb, int seq) {
		int sup = seq * rpp;
		int sub = (seq - 1) * rpp + 1;
		if (sub < 1) {
			sub = 1;
		}
		return wb.createSheet(shtName + "(" + sub + "-" + sup + ")");
	}
	
	private static String getFileName(String tableName) {
		return tableName + new java.util.Date().getTime() + ".xls";
	}
}

