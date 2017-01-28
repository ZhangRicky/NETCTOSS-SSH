package com.tarena.action.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.role.IRoleDao;
import com.tarena.entity.Role;

@Controller
@Scope("prototype")
public class AddRoleAction {

	@Resource
	private IRoleDao roleDao;
	
	//  ‰»Î Ù–‘
	private Role role;
	private List<Integer> privilegeIds;

	public String execute() {
//		Set<RolePrivilege> set = new HashSet<RolePrivilege>();
//		if(privilegeIds != null) {
//			for(Integer pid : privilegeIds) {
//				RolePrivilege rp = new RolePrivilege();
//				rp.setId(new RolePrivilegeId(null, pid));
//				set.add(rp);
//			}
//			role.setRolePrivileges(set);
//		}
		try {
			roleDao.insertRole(role);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public List<Integer> getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(List<Integer> privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
