package com.tarena.action.account;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.account.IAccountDao;
import com.tarena.dao.service.IServiceDao;

@Controller
@Scope("prototype")
public class PauseAccountAction {
	
	@Resource
	private IAccountDao accDao;
	
	@Resource
	private IServiceDao serDao;

	private int id;
	private boolean success;

	public String execute() {
		try {
			//暂停当前账务账号
			accDao.pauseAccount(id);
			//暂停下属业务账号
			serDao.pauseByAccount(id);
			success = true;
		} catch (DAOException e) {
			e.printStackTrace();
			success = false;
			return "error";
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
