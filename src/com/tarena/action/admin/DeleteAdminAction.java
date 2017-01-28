package com.tarena.action.admin;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;

@Controller
@Scope("prototype")
public class DeleteAdminAction {
	
	@Resource
	private IAdminDao adminDao;
	
	private Integer id;
	
	public String execute() {
		try {
			adminDao.delete(id);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
