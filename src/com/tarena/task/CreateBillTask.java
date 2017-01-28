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
 *	�Զ��Ʒ����񣬵����Զ��ƷѵĴ洢����
 */
@Component
public class CreateBillTask {

	@Resource
	private SessionFactory sessionFactory;
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * �Զ��Ʒѷ���
	 */
	public void calculate() {
		Session session = sessionFactory.openSession();
		Connection con = session.connection();
		CallableStatement cs;
		try {
			/*
			 * ���������Ӧ�õ��üƷѴ洢���̣�����ÿ���µ��Զ�ִ��һ�Ρ�
			 * ��Ϊ�˷�����Լ�����Ч�����������һ���򵥵Ĵ洢���̣�
			 * ������ÿ�������ִ��һ�Ρ�
			 * */
			cs = con.prepareCall("call proc1()");
			cs.execute();
			System.out.println(
					"[" + Thread.currentThread().getName() + 
					"]------>ִ�д洢����proc1");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public void setSessionFactory(SessionFactory sesssionFactory) {
		this.sessionFactory = sesssionFactory;
	}

}
