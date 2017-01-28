package com.tarena.dao.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Service;

public interface IServiceDao {

	List<Service> findByCondition(String osUserName, String unixHost,
			String idCardNo, String status, int page, int pageSize)
			throws DAOException;

	int findTotalPage(String osUserName, String unixHost, String idCardNo,
			String status, int pageSize) throws DAOException;

	void addService(Service service) throws DAOException;
	
	void startService(int id) throws DAOException;
	
	void pauseService(int id) throws DAOException;
	
	void deleteService(int id) throws DAOException;
	
	void pauseByAccount(int accountId) throws DAOException;
	
	Service findById(int id) throws DAOException;
	
	void updateService(Service service) throws DAOException;
	
}
