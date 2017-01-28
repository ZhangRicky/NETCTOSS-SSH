package com.tarena.action.role;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Role;

@Controller
@Scope("prototype")
public class UpdateRoleAction {
	
	@Resource
	private IRoleDao roleDao;

	private Role role;

	public String execute() {
		try {
			roleDao.updateRole(role);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
