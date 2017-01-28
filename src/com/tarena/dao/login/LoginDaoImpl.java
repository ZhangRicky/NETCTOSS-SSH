package com.tarena.dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;
import com.tarena.util.DBUtil;

public class LoginDaoImpl implements ILoginDao {

	private String findByCodeAndPasswordSql = "select * from ADMIN_INFO where "
			+ "ADMIN_CODE=? AND PASSWORD=?";

	@Override
	public Admin findByCodeAndPassword(String code, String password)
			throws DAOException {
		if (code == null || password == null)
			return null;
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con
					.prepareStatement(findByCodeAndPasswordSql);
			ps.setString(1, code);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Admin u = createUser(rs);
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("≤È—Ø”√ªß ß∞‹", e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	private Admin createUser(ResultSet rs) throws SQLException {
		Admin u = new Admin();
		u.setId(rs.getInt("id"));
		u.setAdminCode(rs.getString("admin_code"));
		u.setPassword(rs.getString("password"));
		u.setName(rs.getString("name"));
		return u;
	}

	public static void main(String[] args) throws Exception {
		Admin user = new LoginDaoImpl().findByCodeAndPassword("lhh", "123");
		System.out.println(user);
	}

}
