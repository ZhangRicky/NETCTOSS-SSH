package com.tarena.dao;

import com.tarena.dao.account.AccountDaoImpl;
import com.tarena.dao.account.IAccountDao;
import com.tarena.dao.admin.AdminDaoImpl;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.dao.cost.HibernateCostDaoImpl;
import com.tarena.dao.cost.ICostDao;
import com.tarena.dao.login.ILoginDao;
import com.tarena.dao.login.LoginDaoImpl;
import com.tarena.dao.role.IRoleDao;
import com.tarena.dao.role.RoleDaoImpl;
import com.tarena.dao.service.IServiceDao;
import com.tarena.dao.service.ServiceDaoImpl;

public class DAOFactory {

	private static ICostDao costDao = new HibernateCostDaoImpl();

//	private static ILoginDao loginDao = new LoginDaoImpl();

	private static IAccountDao accountDao = new AccountDaoImpl();

	private static IServiceDao serviceDao = new ServiceDaoImpl();

	private static IRoleDao roleDao = new RoleDaoImpl();

	private static IAdminDao adminDao = new AdminDaoImpl();

	public static ICostDao getCostDAO() {
		return costDao;
	}

//	public static ILoginDao getLoginDAO() {
//		return loginDao;
//	}

	public static IAccountDao getAccountDAO() {
		return accountDao;
	}

	public static IServiceDao getServiceDAO() {
		return serviceDao;
	}

	public static IRoleDao getRoleDAO() {
		return roleDao;
	}

	public static IAdminDao getAdminDAO() {
		return adminDao;
	}

}
