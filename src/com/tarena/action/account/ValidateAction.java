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
	
	// 输入
	private String recommenderIdCardNo; // 推荐人身份证

	// 输出
	private Account account; // 推荐人

	/**
	 * 根据推荐人身份证查询推荐人
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
