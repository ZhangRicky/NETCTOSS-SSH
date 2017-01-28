package com.tarena.dao.account;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;

public interface IAccountDao {

	/**
	 * 分页组合查询
	 * 
	 * @param idCardNo
	 *            身份证号
	 * @param realName
	 *            姓名
	 * @param loginName
	 *            登录名
	 * @param status
	 *            状态
	 * @param page
	 *            页码
	 * @param pageSize
	 *            页容量
	 * @return
	 * @throws DAOException
	 */
	List<Account> findByCondition(String idCardNo, String realName,
			String loginName, String status, int page, int pageSize)
			throws DAOException;

	/**
	 * 查询所有的页码
	 * 
	 * @param idCardNo
	 *            身份证
	 * @param realName
	 *            姓名
	 * @param loginName
	 *            登录名
	 * @param status
	 *            状态
	 * @param pageSize
	 *            页容量
	 * @return
	 * @throws DAOException
	 */
	int findTotalPage(String idCardNo, String realName, String loginName,
			String status, int pageSize) throws DAOException;

	/**
	 * 开通
	 * 
	 * @param id
	 * @throws DAOException
	 */
	void startAccount(int id) throws DAOException;

	/**
	 * 暂停
	 * 
	 * @param id
	 * @throws DAOException
	 */
	void pauseAccount(int id) throws DAOException;

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws DAOException
	 */
	void deleteAccount(int id) throws DAOException;

	/**
	 * 根据身份证号查询
	 * 
	 * @param idCardNo
	 *            身份证号
	 * @return
	 * @throws DAOException
	 */
	Account findByIdCardNo(String idCardNo) throws DAOException;

	/**
	 * 新增
	 * 
	 * @param account
	 * @throws DAOException
	 */
	void addAccount(Account account) throws DAOException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	Account findById(int id) throws DAOException;

	/**
	 * 更新
	 * 
	 * @param account
	 * @throws DAOException
	 */
	void updateAccount(Account account) throws DAOException;

}
