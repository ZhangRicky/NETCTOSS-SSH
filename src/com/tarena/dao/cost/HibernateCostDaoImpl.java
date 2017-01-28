package com.tarena.dao.cost;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tarena.dao.BaseDao;
import com.tarena.dao.DAOException;
import com.tarena.entity.Cost;

@Repository
public class HibernateCostDaoImpl 
	extends BaseDao implements ICostDao {

	@Override
	public List<Cost> findAll() throws DAOException {
		String hql = "from Cost";
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Cost> findByPage(
			final int page, final int pageSize) throws DAOException {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				String hql = "from Cost";
				Query query = session.createQuery(hql);
				query.setFirstResult((page-1)*pageSize);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	@Override
	public int findTotalPages(int pageSize) throws DAOException {
		String hql = "select count(*) from Cost";
		List<Long> list = getHibernateTemplate().find(hql);
		int rows = Integer.valueOf(list.get(0).toString());
		if(rows%pageSize==0) {
			return rows/pageSize;
		} else {
			return rows/pageSize+1;
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		Cost c = new Cost();
		c.setId(id);
		getHibernateTemplate().delete(c);
	}

	@Override
	public Cost findByName(Integer id, String name) throws DAOException {
		String hql = "from Cost where name=? ";
		Object[] objs = null;
		if(id != null) {
			hql += "and id != ?";
			objs = new Object[] { name, id };
		} else {
			objs = new Object[] { name };
		}
		List<Cost> list = getHibernateTemplate().find(hql, objs);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Cost findById(Integer id) throws DAOException {
		return (Cost) getHibernateTemplate().load(Cost.class, id);
	}

	@Override
	public void update(Cost cost) throws DAOException {
		try {
			getHibernateTemplate().update(cost);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Cost cost) throws DAOException {
		cost.setStatus("1");
		cost.setCreateTime(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().save(cost);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(int id) throws DAOException {
		Cost cost = (Cost) getHibernateTemplate().get(Cost.class, id);
		cost.setStatus("0");
		getHibernateTemplate().update(cost);
	}

}
