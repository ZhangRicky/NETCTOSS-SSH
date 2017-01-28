package com.tarena.action.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Privilege;
import com.tarena.entity.Role;
import com.tarena.util.PrivilegeReader;

@Controller
@Scope("prototype")
public class ToUpdateRoleAction {
	
	@Resource
	private IRoleDao roleDao;

	// 输入属性
	private Integer id;

	// 输出属性
	private List<Privilege> privileges;
	private Role role;

	public String execute() {
		privileges = PrivilegeReader.getPrivileges();

		try {
			role = roleDao.findById(id);
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

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
