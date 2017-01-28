package com.tarena.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Role;

@Controller
@Scope("prototype")
public class ToAddAdminAction {
	
	@Resource
	private IRoleDao roleDao;

	private List<Role> roleList;

	public String execute() {
		try {
			roleList = roleDao.findByPage(1, Integer.MAX_VALUE);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
