package com.bsco.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsco.app.annotation.Authority;
import com.bsco.app.service.FilesService;
import com.bsco.app.service.logic.DeputyUnitServiceLogic;

@Controller
public class DeputyUnitAction {
	
	@Autowired 
	private DeputyUnitServiceLogic deputyUnitServiceLogic;
	@Autowired
	private FilesService filesService;
	
	/**
	 * 传承单位列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyUnitList.do")
	public String deputyUnitList(HttpServletRequest request,ModelMap model){
		return "deputyUnit/deputyUnitList";
	}
	
	/**
	 * 跳转到申请页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("applyDeputyUnitPage.do")
	public String applyDeputyUnitPage(HttpServletRequest request,ModelMap model){
		return "deputyUnit/applyDeputyUnit";
	}
	/**
	 * 跳转到创建传承单位页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("createDeputyUnit.do")
	public String createDeputyUnit(HttpServletRequest request,ModelMap model){
		return "deputyUnit/createDeputyUnit";
	}
	
	/**
	 * 保存传承单位
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("saveDeputyUnit.do")
	@Authority(roles = {"manager"})
	public  String saveDeputyUnit(String deputyUnitName,String deputyItemName,String itemAddress,String deputyUnitGard,String deputyUnitBatch,String deputyUnitType,
			String deputyUnitDesc,String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,HttpServletRequest request,ModelMap model){
		try {
			this.deputyUnitServiceLogic.save(deputyUnitName, deputyItemName, itemAddress,deputyUnitGard, deputyUnitBatch,deputyUnitType, deputyUnitDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, model);
			return "redirect:deputyUnitList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 修改传承单位
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateDeputyUnit.do")
	@Authority(roles = {"manager"})
	public String updateDeputyUnit(String applyId,String fileId,String deputyUnitId,String deputyUnitName,String id,String itemAddress,String applyGard,String applyBatch,String deputyUnitType,
			String deputyUnitDesc,String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,HttpServletRequest request,ModelMap model){
		try {
			String fileIds[]=fileId.split(",");
			if(fileIds.length<=0){
				return "error";
			}else{
				this.deputyUnitServiceLogic.update(applyId,fileId,deputyUnitId, deputyUnitName, id, itemAddress, applyGard, applyBatch, deputyUnitType, deputyUnitDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, model);
				return "redirect:deputyUnitList.do";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 跳转到修改传承单位页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateDeputyUnitPage.do")
	public String updateDeputyUnitPage(HttpServletRequest request,ModelMap model){
		return "deputyUnit/updateDeputyUnit";
	}
	/**
	 * 申报传承单位
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("applyDeputyUnit.do")
	@Authority(roles = {"manager"})
	public String applyDeputyUnit(String deputyUnitId,String deputyUnitName,String deputyItemName,String itemAddress,String deputyUnitType,
			String deputyUnitGrade,String deputyUnitBatch,String deputyUnitDesc,
			String fileName,String fileUrl,String type,String businessType,String fileVideoName,String fileUrlVideo,String typeVideo,
			String fileImgName,String fileUrlImg,String typeImg,String fileOtherName,
			String fileUrlOther,String typeOther,
			HttpServletRequest request,ModelMap map){
		try {
			this.deputyUnitServiceLogic.apply(deputyUnitId, deputyUnitName, deputyItemName, itemAddress, deputyUnitGrade, deputyUnitBatch, deputyUnitType, deputyUnitDesc, fileName, fileUrl, type, businessType, fileVideoName, fileUrlVideo, typeVideo, fileImgName, fileUrlImg, typeImg, fileOtherName, fileUrlOther, typeOther, request, map);
			return "redirect:deputyUnitList.do";
		} catch (Exception e) {
			return "error";
		}
	}
	
	/**
	 * 删除传承单位
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteDeputyUnit.do")
	@Authority(roles = {"manager"})
	public String deleteDeputyUnit(String deputyUnitId, HttpServletRequest request,ModelMap model){
		try {
			this.deputyUnitServiceLogic.delete(deputyUnitId);
			return "redirect:deputyUnitList.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 根据id跳转到传承单位页面查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deputyUnitById.do")
	public String deputyUnitById(HttpServletRequest request,ModelMap model){
		return "deputyUnit/deputyUnitById";
	}
	/**
	 * 查看申报书列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesBookByUnit.do")
		public String getFilesBook(HttpServletRequest request,ModelMap model){
		return "deputyUnit/getFilesBook";
	}
	
	/**
	 * 查看申报片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesVideoByUnit.do")
		public String getFilesVideo(HttpServletRequest request,ModelMap model){
		return "deputyUnit/getFilesVideo";
	}
	/**
	 * 查看申报照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesImgByUnit.do")
		public String getFilesImg(HttpServletRequest request,ModelMap model){
		return "deputyUnit/getFilesImg";
	}
	
	/**
	 * 查看其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getFilesOtherByUnit.do")
		public String getFilesOther(HttpServletRequest request,ModelMap model){
		return "deputyUnit/getFilesOther";
	}
	
	/**
	 * 动态获取数据(通过传承人级别动态获取申报资料的数据)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDataByGardOnUnit.do")
    @ResponseBody
    public Map getDataByGard(String type,String deputyUnitId,String deputyUnitGard, HttpServletRequest request, ModelMap model){
		Map<String, Object> map=null;
		try {
			map = new HashMap<String, Object>();
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT * FROM files qqa WHERE EXISTS ( SELECT FILE_ID FROM unit_apply_files ww ");
			sql.append("WHERE ww.APPALY_ID = ( SELECT s.APPLY_ID FROM unit_apply s ");
			sql.append("WHERE s.DEPUTY_UNIT_ID = '"+deputyUnitId+"' and ww.FILE_ID=qqa.FILE_ID and s.apply_gard ='"+deputyUnitGard+"') ) and qqa.TYPE ='"+type+"' ");
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
		@RequestMapping("getApplyIdByUnit.do")
		@ResponseBody
		public Map getApplyId(String deputyUnitId,String deputyUnitGard, HttpServletRequest request, ModelMap model){
			Map<String, Object> map=null;
			try {
				map = new HashMap<String, Object>();
				StringBuffer sql=new StringBuffer();
				sql.append("select APPLY_ID from unit_apply s where s.APPLY_GARD ="+deputyUnitGard+" AND s.DEPUTY_UNIT_ID="+deputyUnitId+"");
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

		@RequestMapping("getVideoByUnit.do")
		public String getVideo(HttpServletRequest request,ModelMap model){
			return "deputyUnit/videoPlay";
		}
}
