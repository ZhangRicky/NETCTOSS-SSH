package com.tarena.dao.account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tarena.dao.BaseDao;
import com.tarena.dao.DAOException;
import com.tarena.entity.Account;

@Repository
public class HibernateAccountDaoImpl 
	extends BaseDao implements IAccountDao {

	@Override
	public List<Account> findByCondition(final String idCardNo, final String realName,
			final String loginName, final String status, final int page, final int pageSize)
			throws DAOException {
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				StringBuffer sb = 
						new StringBuffer("from Account where 1=1 ");
				final List<Object> params = new ArrayList<Object>();
				if(idCardNo != null && idCardNo.length() > 0) {
					sb.append("and idcardNo=? ");
					params.add(idCardNo);
				} 
				if(realName != null && realName.length() > 0) {
					sb.append("and realName=? ");
					params.add(realName);
				}
				if(loginName != null && loginName.length() > 0) {
					sb.append("and loginName=? ");
					params.add(loginName);
				}
				if(status != null && status.length() > 0) {
					sb.append("and status=? ");
					params.add(status);
				}
				Query query = session.createQuery(sb.toString());
				for(int i=0;i<params.size();i++) {
					query.setParameter(i, params.get(i));
				}
				query.setFirstResult((page-1)*pageSize);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
		
	}

	@Override
	public int findTotalPage(String idCardNo, String realName,
			String loginName, String status, int pageSize) throws DAOException {
		StringBuffer sb = 
				new StringBuffer("select count(*) from Account where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(idCardNo != null && idCardNo.length() > 0) {
			sb.append("and idcardNo=? ");
			params.add(idCardNo);
		} 
		if(realName != null && realName.length() > 0) {
			sb.append("and realName=? ");
			params.add(realName);
		}
		if(loginName != null && loginName.length() > 0) {
			sb.append("and loginName=? ");
			params.add(loginName);
		}
		if(status != null && status.length() > 0) {
			sb.append("and status=? ");
			params.add(status);
		}
		List list = getHibernateTemplate().find(
				sb.toString(), params.toArray());
		int rows = Integer.valueOf(list.get(0).toString());
		if(rows % pageSize == 0) {
			return rows/pageSize;
		} else {
			return rows/pageSize+1;
		}
	}

	@Override
	public void startAccount(int id) throws DAOException {
		Account a = (Account) 
				getHibernateTemplate().get(Account.class, id);
		a.setStatus("0");
		a.setPauseDate(null);
		try {
			getHibernateTemplate().update(a);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pauseAccount(int id) throws DAOException {
		Account a = (Account) 
				getHibernateTemplate().get(Account.class, id);
		a.setStatus("1");
		a.setPauseDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().update(a);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAccount(int id) throws DAOException {
		Account a = (Account) 
				getHibernateTemplate().get(Account.class, id);
		a.setStatus("2");
		a.setCloseDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().update(a);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Account findByIdCardNo(String idCardNo) 
			throws DAOException {
		String hql = "from Account where idcardNo=? ";
		List<Account> list = getHibernateTemplate().find(hql, idCardNo);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void addAccount(Account account) throws DAOException {
		account.setStatus("0");
		account.setCreateDate(
				new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().save(account);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Account findById(int id) throws DAOException {
		Account a = (Account) 
				getHibernateTemplate().load(Account.class, id);
		if(a.getRecommenderId() != null) {
			Account r = (Account) getHibernateTemplate()
					.load(Account.class, a.getRecommenderId());
			a.setRecommenderIdCardNo(r.getIdcardNo());
		}
		return a;
	}

	@Override
	public void updateAccount(Account account) 
			throws DAOException {
		try {
			getHibernateTemplate().update(account);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
