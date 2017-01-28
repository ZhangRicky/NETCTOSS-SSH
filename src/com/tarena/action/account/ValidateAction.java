package com.tarena.action.account;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.account.IAccountDao;
import com.tarena.entity.Account;

@Controller
@Scope("prototype")
public class ValidateAction {
	
	@Resource
	private IAccountDao accDao;
	
	// ����
	private String recommenderIdCardNo; // �Ƽ������֤

	// ���
	private Account account; // �Ƽ���

	/**
	 * �����Ƽ������֤��ѯ�Ƽ���
	 */
	public String searchRecommender() {
		try {
			account = accDao.findByIdCardNo(
					recommenderIdCardNo);
		} catch (DAOException e) {
			e.printStackTrace();
			account = null;
		}
		return "success";
	}

	public String getRecommenderIdCardNo() {
		return recommenderIdCardNo;
	}

	public void setRecommenderIdCardNo(String recommenderIdCardNo) {
		this.recommenderIdCardNo = recommenderIdCardNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
