package com.tarena.action.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.account.IAccountDao;
import com.tarena.entity.Account;

@Controller
@Scope("prototype")
public class SearchAccountAction {
	
	@Resource
	private IAccountDao accDao;

	// 输入属性
	private String idCardNo;

	// 输出属性
	private Account account;

	public String execute() {
		try {
			account = accDao.findByIdCardNo(idCardNo);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
