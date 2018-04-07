package com.bsco.app.parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class CommentVo {
	private String COMMENT_ID;
	private String CREATE_DATE;
	private String CREATOR_IDENTITY_ID;
	private String CREATOR_LOGIN_NAME;
	private String CREATOR_NICK_NAME;
	private String SOURCE_ID;
	private String SOURCE_TYPE;
	private String STATUS;
	private String PARENT_ID;
	private String CONTENT;
	private String LEVEL;
	private String ISLEAF;
	private String PID;
	private String USERID;
	private String TITLE;
	private String PHOTO;
	private String PUSERID;
	private String ORG_ID;
	public String getORG_ID() {
		return ORG_ID;
	}
	public void setORG_ID(String oRG_ID) {
		ORG_ID = oRG_ID;
	}
	public String getCOMMENT_ID() {
		return COMMENT_ID;
	}
	public void setCOMMENT_ID(String cOMMENTID) {
		COMMENT_ID = cOMMENTID;
	}
	
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATEDATE) {
		CREATE_DATE = cREATEDATE;
	}
	public String getCREATOR_IDENTITY_ID() {
		return CREATOR_IDENTITY_ID;
	}
	public void setCREATOR_IDENTITY_ID(String cREATORIDENTITYID) {
		CREATOR_IDENTITY_ID = cREATORIDENTITYID;
	}
	public String getCREATOR_NICK_NAME() {
		return CREATOR_NICK_NAME;
	}
	public void setCREATOR_NICK_NAME(String cREATORNICKNAME) {
		CREATOR_NICK_NAME = cREATORNICKNAME;
	}
	public String getSOURCE_ID() {
		return SOURCE_ID;
	}
	public void setSOURCE_ID(String sOURCEID) {
		SOURCE_ID = sOURCEID;
	}
	public String getSOURCE_TYPE() {
		return SOURCE_TYPE;
	}
	public void setSOURCE_TYPE(String sOURCETYPE) {
		SOURCE_TYPE = sOURCETYPE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENTID) {
		PARENT_ID = pARENTID;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getISLEAF() {
		return ISLEAF;
	}
	public void setISLEAF(String iSLEAF) {
		ISLEAF = iSLEAF;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getPHOTO() {
		return PHOTO;
	}
	public void setPHOTO(String pHOTO) {
		PHOTO = pHOTO;
	}
	public String getPUSERID() {
		return PUSERID;
	}
	public void setPUSERID(String pUSERID) {
		PUSERID = pUSERID;
	}
	public String getCREATOR_LOGIN_NAME() {
		return CREATOR_LOGIN_NAME;
	}
	public void setCREATOR_LOGIN_NAME(String cREATORLOGINNAME) {
		CREATOR_LOGIN_NAME = cREATORLOGINNAME;
	}
	
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	
	
	
	
	
	//oracle.sql.Clob类型转换成String类型
    public static String ClobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 得到流
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            try {
                s = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        reString = sb.toString();
        return reString;
    }
}
