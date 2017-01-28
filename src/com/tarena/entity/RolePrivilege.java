package com.tarena.entity;

import com.tarena.util.PrivilegeReader;

public class RolePrivilege {

	private RolePrivilegeId id;

	public RolePrivilegeId getId() {
		return id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return PrivilegeReader
			.getPrivilegeNameById(id.getPrivilegeId());
	}

}
