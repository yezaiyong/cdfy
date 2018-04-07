/**
 * 
 */
package com.bsco.app.parameter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class Constants {

	public static final String QUERY_STRING_SEP = "?";
	public static final String DFS_URL = "http://192.168.1.107:8080/";
	public static final String DEFAULT_HEAD_URL = "web/images/shou_ye/touxiang_mo.jpg";
	public static final String TOPIC_REGEX = "#([^\\s]+)#";
	public static final String URL_REGEX = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final String USERID_IN_SESSION = "USERID_IN_SESSION";
	public static final String USERID_IN_COOKIE = "USERID_IN_COOKIE";
	public static final String ROLE_IN_REQUEST = "ROLE";
	public static final String ORG_IN_REQUEST = "ORG";
	public static final String USER_IN_REQUEST = "USER";
	public static final String LOGIN_USER = "LOGINUSER";
	public static final String AUTHCODE = "authcode";
	public static final String MESSAGE = "message";
	public static final String UPLOAD_PATH = "/upload";
	public static final String PHOTO_PATH = "/photo";
	public static final String RESOURCE_PATH = "/resource";
	public static final String SYSTEM_INFO="system_info";
	// 创建用户初始密码
	public static final String BEGIN_PASSWORD = "202cb962ac59075b964b07152d234b70";

	public static String getFollowerKey(String userId) {
		return userId + "_USER_FOLLOWER";
	}

	public static String getFollowingKey(String userId) {
		return userId + "_USER_FOLLOWING";
	}

	public static byte[] getUserObjectKey(String userId) {
		return ("USER_OBJECT_" + userId).getBytes();
	}

	public static byte[] getUserSessionKey(String sessionId) {
		return ("USER_SESSION_" + sessionId).getBytes();
	}



	
	
	
	//保护性资料项目或者代表性项目
	public static enum STAUTS {
		y , //可用
		n //不可用
	}
	
	//保护性资料项目或者代表性项目
	public static enum BUSINESS_TYPE {
		pro , //保护性项目
		deputy, //代表性项目
		heir,//传承人
		unit //传承单位
	}
	
	//保护性资料项目或者代表性项目
	public static enum ITEM_TYPE {
		pro , //保护性项目
		deputy //代表性项目
	}
	//传承单位类别
	public static enum UNIT_TYPE {
		chuanxisuo , //传习所
		shengchanxing,//生产性保护示范基地
		other//其他
	}
	//项目级别
	public static enum ITEM_GARD {
		country , //国家级
		province,//省级
		city//市级
	}

	//法规与文件类型
		public static enum FILES_Type {
			pub_doc , //公布文件
			tran_doc,//收发文件
			law_doc//法规文件
		}
		
	// 用户类型
	public static enum USER_TYPE {
		common("普通用户", "home.do"), 
		highgrade("高级用户", "home.do"), 
		manager("系统管理员", "homeManager.do");

		USER_TYPE(String desc, String mainPage) {
			this.desc = desc;
			this.mainPage = mainPage;
		}

		private final String desc;
		private final String mainPage;

		public String getDesc() {
			return desc;
		}

		public String getMainPage() {
			return mainPage;
		}

	}

	public static enum PRIV_TYPE {
		pub("公开"), pri("私有"), fri("好友可见");

		PRIV_TYPE(String desc) {
			this.desc = desc;
		}

		private final String desc;

		public String getDesc() {
			return desc;
		}
	}

	public static Map<String, USER_TYPE> USER_TYPE_ENUM_MAP = new HashMap<String, Constants.USER_TYPE>();
	static {
		for (USER_TYPE type : USER_TYPE.values()) {
			USER_TYPE_ENUM_MAP.put(type.name(), type);
		}
	}

	// 状态
	public static enum Status {
		y, // 启用,一读
		n, // 禁用,未读
		d // 删除
	}

	// 推荐
	public static enum RECOMMEND {
		y, n
	}

	// 性别
	public static enum Sex {
		m("男"), // 男
		f("女");// 女
		Sex(String desc) {
			this.desc = desc;
		}

		private final String desc;

		public String getDesc() {
			return desc;
		}
	}

	// 消息类型
	public static enum MESSAGE_TYPE {
		Private, // 私信
		Public, // 公共
		Global // 系统
		/*
		p, // 点对点消息
		b, // 广播消息,整站公告
		c, // 班级公告
		xm, // 项目公告
		xk, // 学科公告
		ss, // 市级公告
		q// 区县公告
		*/
	}
	
	public static enum BROADCAST_TYPE {
		team, // 班级,辅导员
		subject, // 学科,专家
		project, // 项目
		area // 地区
	}

	public static enum REPORT_TYPE {
		team, // 班级,辅导员
		subject, // 学科,专家
		project, // 项目
		area // 地区
	}

	// 分页大小
	public static final int DEFAULT_PAGE_SIZE = 10;

	// 课程类型
	public static enum COURSE_TYPE {
		book("课程"), chapter("章节"), lesson("课件");
		COURSE_TYPE(String desc) {
			this.desc = desc;
		}

		private final String desc;

		public String getDesc() {
			return desc;
		}
	}

	// 课件类型
	public static enum COURSE_LESSON_TYPE {
		docx, txt, xlsx, pptx, pdf, swf, jpg, png, gif, jpeg, bmp
	}

	// 课件格式
	public static enum C_TYPE {
		视频, 音频, 文本,网络地址
	}

	public static Map<String, COURSE_TYPE> COURSE_TYPE_ENUM_MAP = new HashMap<String, Constants.COURSE_TYPE>();
	static {
		for (COURSE_TYPE type : COURSE_TYPE.values()) {
			COURSE_TYPE_ENUM_MAP.put(type.name(), type);
		}
	}

	public static enum DYNAMIC_TYPE {
		zhuanzai, // 转载日志
		zhuanbo, // 转播微博
		comment, // 评论
		mention, // 提到
		privateTo, // 私信
		publicTo, // 公告
		attention// 关注
	}

	public static enum DYNAMIC_STATUS {
		y, n
	}

	public static final String ORGCODE_SEP = ",";

	
	public static final String SITEORG_IN_REQUEST = "C"+"U"+"R"+"_"+"O"+"R"+"G";

}
