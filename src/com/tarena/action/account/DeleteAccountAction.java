package com.tarena.action.account;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.account.IAccountDao;

@Controller
@Scope("prototype")
public class DeleteAccountAction {
	
	@Resource
	private IAccountDao accDao;
	
	private int id;
	private boolean success;

	public String execute() {
		try {
			accDao.deleteAccount(id);
			success = true;
		} catch (DAOException e) {
			e.printStackTrace();
			success = false;
		}
		return "success";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


}
