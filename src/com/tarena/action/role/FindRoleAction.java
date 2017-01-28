package com.tarena.action.role;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Role;

@Controller
@Scope("prototype")
public class FindRoleAction {
	
	@Resource
	private IRoleDao roleDao;

	//  ‰≥ˆ Ù–‘
	private int page=1;
	private int pageSize;
	private List<Role> roles;
	private int totalPage;

	public String execute() {
		try {
			roles = roleDao.findByPage(page, pageSize);
			totalPage = roleDao.findTotalPage(pageSize);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
