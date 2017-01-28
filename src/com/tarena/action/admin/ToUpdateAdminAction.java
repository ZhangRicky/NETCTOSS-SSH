package com.tarena.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Admin;
import com.tarena.entity.Role;

@Controller
@Scope("prototype")
public class ToUpdateAdminAction {
	
	@Resource
	private IAdminDao adminDao;
	
	@Resource
	private IRoleDao roleDao;
	
	private Integer id;
	
	private Admin admin;
	
	private List<Role> roleList;

	public String execute() {
		try {
			admin = adminDao.findById(id);
			roleList = roleDao.findByPage(1, Integer.MAX_VALUE);
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
