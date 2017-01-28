package com.tarena.action.role;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;

@Controller
@Scope("prototype")
public class DeleteRoleAction {

	@Resource
	private IRoleDao roleDao;

	private Integer id;

	public String execute() {
		try {
			roleDao.delete(id);
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
