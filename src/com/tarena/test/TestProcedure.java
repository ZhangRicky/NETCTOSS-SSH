package com.tarena.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Test;

import com.tarena.util.DBUtil;

public class TestProcedure {

	@Test
	public void test1() throws SQLException {
		Connection con = DBUtil.getConnection();
		CallableStatement cs = con.prepareCall("call sumsub(?,?,?,?)");
		cs.setInt(1, 8);
		cs.setInt(2, 6);
		cs.registerOutParameter(3, java.sql.Types.INTEGER);
		cs.registerOutParameter(4, java.sql.Types.INTEGER);
		cs.execute();
		System.out.println(cs.getInt(3));
		System.out.println(cs.getInt(4));
	}

	@Test
	public void test2() throws SQLException {
		Connection con = DBUtil.getConnection();
		CallableStatement cs = con.prepareCall("call proc2(?)");
		cs.setInt(1, 200);
		cs.registerOutParameter(1, Types.INTEGER);
		cs.execute();
		System.out.println(cs.getInt(1));
	}

}
