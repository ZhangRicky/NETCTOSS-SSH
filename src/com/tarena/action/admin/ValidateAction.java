package com.tarena.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.entity.Admin;

/**
 * 验证Action，进行一些业务的验证
 */
@Controller("adminValidateAction")
@Scope("prototype")
public class ValidateAction {
	
	@Resource
	private IAdminDao adminDao;

	// input
	private String adminCode; // 账号

	// output
	private Map<String, Object> info = new HashMap<String, Object>(); // 输出内容

	public String execute() {
		try {
			Admin admin = adminDao.findByCode(adminCode);
			if (admin == null) {
				//没找到管理员，不重复
				info.put("isRepeat", false);
				info.put("message", "有效的管理员账号");
			} else {
				//找到了管理员，重复了
				info.put("isRepeat", true);
				info.put("message", "管理员账号已存在");
			}
			
			info.put("success", true);
		} catch (DAOException e) {
			e.printStackTrace();
			info.put("success", false);
			info.put("message", "系统发生异常，请联系系统管理员");
		}
		return "success";
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public String getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

}
