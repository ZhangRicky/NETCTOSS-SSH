package com.tarena.action.admin;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;

@Controller
@Scope("prototype")
public class ResetPasswordAction {
	
	@Resource
	private IAdminDao adminDao;

	// 输入属性
	private String ids;

	// 输出属性
	private boolean pass;

	public String execute() {
		try {
			adminDao.resetPassword(ids.split(","));
			pass = true;
		} catch (DAOException e) {
			e.printStackTrace();
			pass = false;
		}
		return "success";
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

}
