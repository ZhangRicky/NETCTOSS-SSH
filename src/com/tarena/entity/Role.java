package com.tarena.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role {

	private Integer id;
	private String name;
	private Set<RolePrivilege> rolePrivileges;

	public Set<RolePrivilege> getRolePrivileges() {
		return rolePrivileges;
	}

	public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *	����һ��ģ��ID,���乹��Ϊ����Set<RolePrivilege>
	 */
	public void setPrivilegeIds(List<Integer> privilegeIds) {
		if(privilegeIds == null)
			return;
		Set<RolePrivilege> set = new HashSet<RolePrivilege>();
		for(Integer pid : privilegeIds) {
			RolePrivilege rp = new RolePrivilege();
			rp.setId(new RolePrivilegeId(null, pid));
			set.add(rp);
		}
		setRolePrivileges(set);
	}
	
	/**
	 * �Ӷ���Set<RolePrivilege>����ȡ��ȫ����privilegeId
	 */
	public List<Integer> getPrivilegeIds() {
		Set<RolePrivilege> set = getRolePrivileges();
		if(set == null)
			return null;
		List<Integer> list = new ArrayList<Integer>();
		for(RolePrivilege rp : set) {
			list.add(rp.getId().getPrivilegeId());
		}
		return list;
	}

}
