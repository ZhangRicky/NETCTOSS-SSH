package com.tarena.dao.role;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.tarena.dao.BaseDao;
import com.tarena.dao.DAOException;
import com.tarena.entity.Role;
import com.tarena.entity.RolePrivilege;

@Repository
public class HibernateRoleDaoImpl 
	extends BaseDao implements IRoleDao {

	@Override
	public void insertRole(Role role) throws DAOException {
		try {
			getHibernateTemplate().save(role);
			Set<RolePrivilege> set = role.getRolePrivileges();
			if(set != null) {
				for(RolePrivilege rp : set) {
					rp.getId().setRoleId(role.getId());
					getHibernateTemplate().save(rp);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Role findById(int id) throws DAOException {
		return (Role) getHibernateTemplate().load(Role.class, id);
	}

	@Override
	public void updateRole(Role role) throws DAOException {
		try {
			getHibernateTemplate().update(role);
			
			String hql = "from RolePrivilege where id.roleId=?";
			List<RolePrivilege> list = getHibernateTemplate().find(hql, role.getId());
			if(list != null) {
				for(RolePrivilege rp : list) {
					getHibernateTemplate().delete(rp);
				}
			}
			
			Set<RolePrivilege> set = role.getRolePrivileges();
			if(set != null) {
				for(RolePrivilege rp : set) {
					rp.getId().setRoleId(role.getId());
					getHibernateTemplate().save(rp);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Role> findByPage(
			final int page, final int pageSize) throws DAOException {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				String hql = "from Role";
				Query query = session.createQuery(hql);
				query.setFirstResult((page-1)*pageSize);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	@Override
	public int findTotalPage(int pageSize) throws DAOException {
		String hql = "select count(*) from Role";
		List list = getHibernateTemplate().find(hql);
		int rows = Integer.valueOf(list.get(0).toString());
		if(rows%pageSize==0){
			return rows/pageSize;
		} else {
			return rows/pageSize+1;
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		try {
			Role role = this.findById(id);
			getHibernateTemplate().delete(role);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
