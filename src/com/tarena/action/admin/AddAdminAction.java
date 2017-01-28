package com.tarena.action.admin;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.entity.Admin;

@Controller
@Scope("prototype")
public class AddAdminAction {
	
	@Resource
	private IAdminDao adminDao;

	//  ‰»Î Ù–‘
	private Admin admin;

	public String execute() {
		try {
			adminDao.addAdmin(admin);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
