package com.tarena.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Admin {

	private Integer id;
	private String adminCode;
	private String password;
	private String name;
	private String telephone;
	private String email;
	private Date enrollDate;

	private Set<Role> roles;
	
	public String getRoleNames() {
		if(roles.isEmpty())
			return null;
		String name = "";
		for(Role r : roles) {
			name += "," + r.getName();
		}
		return name.replaceFirst(",", "");
	}

	public void setRoleIds(List<Integer> roleIds) {
		if(roleIds.isEmpty())
			return;
		Set<Role> set = new HashSet<Role>();
		for(Integer id : roleIds) {
			Role r = new Role();
			r.setId(id);
			set.add(r);
		}
		setRoles(set);
	}
	
	public List<Integer> getRoleIds() {
		Set<Role> set = getRoles();
		if(set.isEmpty())
			return null;
		List<Integer> list = new ArrayList<Integer>();
		for(Role r : set) {
			list.add(r.getId());
		}
		return list;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

}
