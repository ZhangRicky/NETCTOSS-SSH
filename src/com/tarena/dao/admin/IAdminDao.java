package com.tarena.dao.admin;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

public interface IAdminDao {

	List<Admin> findByPage(Integer roleId, Integer privilegeId, 
			int page, int pageSize) throws DAOException;

	int findTotalPage(Integer roleId, Integer privilegeId, 
			int pageSize) throws DAOException;

	void addAdmin(Admin admin) throws DAOException;

	void resetPassword(String[] ids) throws DAOException;

	List<Integer> findPrivilegeIdsByAdmin(Integer adminId) throws DAOException;
	
	Admin findByCode(String code) throws DAOException;
	
	Admin findById(int id) throws DAOException;
	
	void update(Admin admin) throws DAOException;
	
	void delete(int id) throws DAOException;

}
