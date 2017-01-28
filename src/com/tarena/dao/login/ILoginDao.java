package com.tarena.dao.login;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

public interface ILoginDao {

	Admin findByCodeAndPassword(String code, String password)
			throws DAOException;

}
