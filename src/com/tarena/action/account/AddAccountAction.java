package com.tarena.action.account;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.account.IAccountDao;
import com.tarena.entity.Account;

@Controller
@Scope("prototype")
public class AddAccountAction {
	
	@Resource
	private IAccountDao accDao;

	//  ‰»Î
	private Account account;

	private boolean pass;

	public String execute() {
		try {
			accDao.addAccount(account);
			pass = true;
		} catch (DAOException e) {
			e.printStackTrace();
			pass = false;
		}
		return "success";
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

}
