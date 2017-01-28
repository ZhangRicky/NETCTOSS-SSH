package com.tarena.dao.service;

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
import com.tarena.entity.Service;
import com.tarena.entity.ServiceUpdateBak;

@Repository
public class HibernateServiceDaoImpl 
	extends BaseDao implements IServiceDao {

	@Override
	public List<Service> findByCondition(
			String osUserName, String unixHost,
			String idCardNo, String status, 
			final int page, final int pageSize)
			throws DAOException {
		final List<Object> params = new ArrayList<Object>();
		final StringBuffer sb = new StringBuffer();
		sb.append("from Service where 1=1 ");
		if (osUserName != null 
				&& osUserName.length() > 0) {
			sb.append("and osUserName=? ");
			params.add(osUserName);
		}
		if (unixHost != null 
				&& unixHost.length() > 0) {
			sb.append("and unixHost=? ");
			params.add(unixHost);
		}
		if (idCardNo != null 
				&& idCardNo.length() > 0) {
			sb.append("and account.idcardNo=? ");
			params.add(idCardNo);
		}
		if (status != null 
				&& status.length() > 0) {
			sb.append("and status=? ");
			params.add(status);
		}
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
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
	public int findTotalPage(String osUserName, String unixHost,
			String idCardNo, String status, int pageSize) throws DAOException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Service where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (osUserName != null 
				&& osUserName.length() > 0) {
			sb.append("and osUserName=? ");
			params.add(osUserName);
		}
		if (unixHost != null 
				&& unixHost.length() > 0) {
			sb.append("and unixHost=? ");
			params.add(unixHost);
		}
		if (idCardNo != null 
				&& idCardNo.length() > 0) {
			sb.append("and account.idcardNo=? ");
			params.add(idCardNo);
		}
		if (status != null 
				&& status.length() > 0) {
			sb.append("and status=? ");
			params.add(status);
		}
		
		List list = getHibernateTemplate().find(
				sb.toString(), params.toArray());
		int rows = Integer.valueOf(list.get(0).toString());
		
		if(rows%pageSize==0){
			return rows/pageSize;
		} else {
			return rows/pageSize+1;
		}
		
	}

	@Override
	public void addService(Service service) throws DAOException {
		service.setStatus("0");
		service.setCreateDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().save(service);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Service findById(int id) throws DAOException {
		return (Service) getHibernateTemplate().load(Service.class, id);
	}

	@Override
	public void updateService(Service service) throws DAOException {
		ServiceUpdateBak bak = new ServiceUpdateBak();
		bak.setServiceId(service.getId());
		bak.setCostId(service.getCost().getId());
		try {
			getHibernateTemplate().save(bak);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pauseByAccount(int accountId) throws DAOException {
		String hql = "from Service where account.id=?";
		List<Service> list = getHibernateTemplate().find(hql, accountId);
		if(list != null && list.size() > 0) {
			for(Service s : list) {
				s.setStatus("1");
				s.setPauseDate(new Date(System.currentTimeMillis()));
				getHibernateTemplate().update(s);
			}
		}
	}

	@Override
	public void startService(int id) throws DAOException {
		Service s = (Service) 
				getHibernateTemplate().get(Service.class, id);
		s.setStatus("0");
		s.setPauseDate(null);
		try {
			getHibernateTemplate().update(s);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pauseService(int id) throws DAOException {
		Service s = (Service) 
				getHibernateTemplate().get(Service.class, id);
		s.setStatus("1");
		s.setPauseDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().update(s);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteService(int id) throws DAOException {
		Service s = (Service) 
				getHibernateTemplate().get(Service.class, id);
		s.setStatus("2");
		s.setCloseDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().update(s);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
