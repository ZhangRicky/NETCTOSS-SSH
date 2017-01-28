package com.tarena.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDao extends HibernateDaoSupport {

	@Resource
	public void setSF(SessionFactory sf) {
		super.setSessionFactory(sf);
	}

	protected boolean notNull(Object obj) {
		return obj != null && obj.toString().length() > 0;
	}

}
