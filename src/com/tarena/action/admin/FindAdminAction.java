package com.tarena.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Admin;
import com.tarena.entity.Privilege;
import com.tarena.entity.Role;
import com.tarena.util.PrivilegeReader;

@Controller
@Scope("prototype")
public class FindAdminAction {
	
	@Resource
	private IAdminDao adminDao;
	
	@Resource
	private IRoleDao roleDao;

	// 输入属性
	private Integer privilegeId;
	private Integer roleId;
	private int page = 1;
	private int pageSize;
	private int totalPage;

	// 输出属性
	private List<Admin> admins;//列表显示数据
	private List<Privilege> privileges;//用于初始化查询条件"模块"
	private List<Role> roles;//用于初始化查询条件"角色"

	public String execute() {
		try {
			admins = adminDao.findByPage(roleId, privilegeId, page, pageSize);
			totalPage = adminDao.findTotalPage(roleId, privilegeId, pageSize);
			
			privileges = PrivilegeReader.getPrivileges();
			roles = roleDao.findByPage(1, Integer.MAX_VALUE);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
