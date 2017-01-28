package com.tarena.entity;

import java.io.Serializable;

/**
 * 角色权限中间表的联合主键
 */
public class RolePrivilegeId implements Serializable {

	private static final long serialVersionUID = 3600786070743001095L;

	private Integer roleId;
	private Integer privilegeId;

	public RolePrivilegeId() {

	}

	public RolePrivilegeId(Integer roleId, Integer privilegeId) {
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

}
