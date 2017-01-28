package com.tarena.action.role;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.entity.Privilege;
import com.tarena.util.PrivilegeReader;

@Controller
@Scope("prototype")
public class ToAddRoleAction {

	//  ‰≥ˆ Ù–‘
	private List<Privilege> privileges;

	public String execute() {
		privileges = PrivilegeReader.getPrivileges();
		return "success";
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
