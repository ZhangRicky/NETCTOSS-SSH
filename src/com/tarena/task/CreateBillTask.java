package com.tarena.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 *	自动计费任务，调用自动计费的存储过程
 */
@Component
public class CreateBillTask {

	@Resource
	private SessionFactory sessionFactory;
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 自动计费方法
	 */
	public void calculate() {
		Session session = sessionFactory.openSession();
		Connection con = session.connection();
		CallableStatement cs;
		try {
			/*
			 * 正常情况下应该调用计费存储过程，并且每月月底自动执行一次。
			 * 但为了方便测试及看到效果，这里调用一个简单的存储过程，
			 * 并设置每个几秒便执行一次。
			 * */
			cs = con.prepareCall("call proc1()");
			cs.execute();
			System.out.println(
					"[" + Thread.currentThread().getName() + 
					"]------>执行存储过程proc1");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public void setSessionFactory(SessionFactory sesssionFactory) {
		this.sessionFactory = sesssionFactory;
	}

}
