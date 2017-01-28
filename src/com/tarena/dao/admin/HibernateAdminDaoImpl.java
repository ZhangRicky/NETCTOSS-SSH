package com.tarena.dao.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tarena.dao.BaseDao;
import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

@Repository
public class HibernateAdminDaoImpl 
	extends BaseDao implements IAdminDao {

	@Override
	public List<Admin> findByPage(Integer roleId, Integer privilegeId,
			final int page, final int pageSize) throws DAOException {
		final List<Object> params = new ArrayList<Object>();
		final StringBuffer sb = new StringBuffer();
		sb.append("from Admin where id in ( ");
		sb.append("select a.id from Admin a ");
		sb.append("left join a.roles r ");
		sb.append("left join r.rolePrivileges p ");
		sb.append("where 1=1 ");
		if(roleId != null) {
			sb.append("and r.id=? ");
			params.add(roleId);
		}
		if(privilegeId != null) {
			sb.append("and p.id.privilegeId=? ");
			params.add(privilegeId);
		}
		sb.append(") ");
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				try {
					Query query = session.createQuery(sb.toString());
					for(int i=0;i<params.size();i++) {
						query.setParameter(i, params.get(i));
					}
					query.setFirstResult((page-1)*pageSize);
					query.setMaxResults(pageSize);
					return query.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Override
	public int findTotalPage(Integer roleId, Integer privilegeId, int pageSize)
			throws DAOException {
		final List<Object> params = new ArrayList<Object>();
		final StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Admin where id in ( ");
		sb.append("select a.id from Admin a ");
		sb.append("left join a.roles r ");
		sb.append("left join r.rolePrivileges p ");
		sb.append("where 1=1 ");
		if(roleId != null) {
			sb.append("and r.id=? ");
			params.add(roleId);
		}
		if(privilegeId != null) {
			sb.append("and p.id.privilegeId=? ");
			params.add(privilegeId);
		}
		sb.append(") ");
		List list = getHibernateTemplate().find(
				sb.toString(), params.toArray());
		int rows = Integer.valueOf(list.get(0).toString());
		if(rows%pageSize==0) {
			return rows/pageSize;
 		} else {
 			return rows/pageSize+1;
 		}
	}

	@Override
	public void addAdmin(Admin admin) throws DAOException {
		admin.setEnrollDate(new Date(System.currentTimeMillis()));
		try {
			getHibernateTemplate().save(admin);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetPassword(String[] ids) throws DAOException {
		if(ids == null) 
			return;
		for(String id : ids) {
			Admin a = (Admin) getHibernateTemplate()
					.load(Admin.class, Integer.valueOf(id));
			a.setPassword("123456");
			try {
				getHibernateTemplate().update(a);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Integer> findPrivilegeIdsByAdmin(Integer adminId)
			throws DAOException {
		String hql = "select p.id.privilegeId from Admin a " +
				"left join a.roles r " +
				"left join r.rolePrivileges p " +
				"where a.id=? ";
		
		List list = getHibernateTemplate().find(hql, adminId);
		List<Integer> ids = new ArrayList<Integer>();
		for(Object obj : list) {
			ids.add(Integer.valueOf(obj.toString()));
		}
		return ids;
	}

	@Override
	public Admin findByCode(String code) throws DAOException {
		String hql = "from Admin where adminCode=?";
		List<Admin> list = getHibernateTemplate().find(hql, code);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Admin findById(int id) throws DAOException {
		return (Admin) getHibernateTemplate().load(Admin.class, id);
	}

	@Override
	public void update(Admin admin) throws DAOException {
		try {
			getHibernateTemplate().update(admin);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		Admin admin = new Admin();
		admin.setId(id);
		getHibernateTemplate().delete(admin);
	}
	
}
