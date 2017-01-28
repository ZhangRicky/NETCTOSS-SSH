package com.tarena.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;
import com.tarena.util.DBUtil;

public class AccountDaoImpl implements IAccountDao {

	@Override
	public List<Account> findByCondition(String idCardNo, String realName,
			String loginName, String status, int page, int pageSize)
			throws DAOException {
		// 参数值
		List<Object> paramList = new ArrayList<Object>();
		// 子查询SQL
		StringBuffer sb = new StringBuffer();
		sb.append("select a.*,rownum r from account a where 1=1 ");
		if (idCardNo != null && idCardNo.length() > 0) {
			sb.append("and IDCARD_NO=? ");
			paramList.add(idCardNo);
		}
		if (realName != null && realName.length() > 0) {
			sb.append("and REAL_NAME=? ");
			paramList.add(realName);
		}
		if (loginName != null && loginName.length() > 0) {
			sb.append("and LOGIN_NAME=? ");
			paramList.add(loginName);
		}
		if (status != null && status.length() > 0) {
			sb.append("and STATUS=? ");
			paramList.add(status);
		}

		// 完整的SQL
		String sql = "select * from (" + sb.toString() + ") where r<? and r>? ";

		int nextMin = page * pageSize + 1;
		int lastMax = (page - 1) * pageSize;
		paramList.add(nextMin);
		paramList.add(lastMax);

		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < paramList.size(); i++) {
				Object obj = paramList.get(i);
				ps.setObject(i + 1, obj);
			}
			ResultSet rs = ps.executeQuery();
			List<Account> list = null;
			while (rs.next()) {
				Account a = createAccount(rs);
				if (list == null)
					list = new ArrayList<Account>();
				list.add(a);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询账务信息失败！", e);
		} finally {
			DBUtil.closeConnection();
		}

	}

	private Account createAccount(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setId(rs.getInt("ID"));
		a.setRecommenderId(rs.getObject("RECOMMENDER_ID")==null?null:rs.getInt("RECOMMENDER_ID"));
//		a.setRecommenderId(rs.getInt("RECOMMENDER_ID"));
		a.setLoginName(rs.getString("LOGIN_NAME"));
		a.setLoginPassword(rs.getString("LOGIN_PASSWD"));
		a.setStatus(rs.getString("STATUS"));
		a.setCreateDate(rs.getDate("CREATE_DATE"));
		a.setPauseDate(rs.getDate("PAUSE_DATE"));
		a.setCloseDate(rs.getDate("CLOSE_DATE"));
		a.setRealName(rs.getString("REAL_NAME"));
		a.setIdcardNo(rs.getString("IDCARD_NO"));
//		Date birthday = rs.getDate("BIRTHDATE");
//		if (birthday != null)
//			a.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").format(birthday));
		a.setBirthdate(rs.getDate("BIRTHDATE"));
		a.setGender(rs.getString("GENDER"));
		a.setOccupation(rs.getString("OCCUPATION"));
		a.setTelephone(rs.getString("TELEPHONE"));
		a.setEmail(rs.getString("EMAIL"));
		a.setMailaddress(rs.getString("MAILADDRESS"));
		a.setZipcode(rs.getString("ZIPCODE"));
		a.setQq(rs.getString("QQ"));
		a.setLastLoginTime(rs.getDate("LAST_LOGIN_TIME"));
		a.setLastLoginIp(rs.getString("LAST_LOGIN_IP"));
		return a;
	}

	@Override
	public int findTotalPage(String idCardNo, String realName,
			String loginName, String status, int pageSize) throws DAOException {
		// 参数值
		List<Object> paramList = new ArrayList<Object>();
		// 查询SQL
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from account where 1=1 ");
		if (idCardNo != null && idCardNo.length() > 0) {
			sb.append("and IDCARD_NO=? ");
			paramList.add(idCardNo);
		}
		if (realName != null && realName.length() > 0) {
			sb.append("and REAL_NAME=? ");
			paramList.add(realName);
		}
		if (loginName != null && loginName.length() > 0) {
			sb.append("and LOGIN_NAME=? ");
			paramList.add(loginName);
		}
		if (status != null && !status.equals("-1")) {
			sb.append("and STATUS=? ");
			paramList.add(status);
		}

		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());
			for (int i = 0; i < paramList.size(); i++) {
				Object obj = paramList.get(i);
				ps.setObject(i + 1, obj);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int rows = rs.getInt(1);
				if (rows % pageSize == 0) {
					return rows / pageSize;
				} else {
					return rows / pageSize + 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询账务信息失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return 0;
	}

	@Override
	public void startAccount(int id) throws DAOException {
		String sql = "update account set status=0, pause_date=null where id=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public void pauseAccount(int id) throws DAOException {
		String sql = "update account set status=1, pause_date=sysdate where id=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public void deleteAccount(int id) throws DAOException {
		String sql = "update account set status=2, CLOSE_DATE=sysdate where id=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public Account findByIdCardNo(String idCardNo) throws DAOException {
		if (idCardNo == null || idCardNo.length() == 0)
			return null;
		String sql = "select * from account where IDCARD_NO=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, idCardNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account a = createAccount(rs);
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	@Override
	public void addAccount(Account account) throws DAOException {
		if (account == null)
			return;
		Connection con = DBUtil.getConnection();
		try {
			String sql = "insert into ACCOUNT "
					+ "values(account_seq.nextval,?,?,?,'0',sysdate,null,null,?,?,?,?,?,?,?,?,?,?,null,null)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, account.getRecommenderId());
			ps.setObject(2, account.getLoginName());
			ps.setObject(3, account.getLoginPassword());
			ps.setObject(4, account.getRealName());
			ps.setObject(5, account.getIdcardNo());
			ps.setObject(6, account.getBirthdate());
			ps.setObject(7, account.getGender());
			ps.setObject(8, account.getOccupation());
			ps.setObject(9, account.getTelephone());
			ps.setObject(10, account.getEmail());
			ps.setObject(11, account.getMailaddress());
			ps.setObject(12, account.getZipcode());
			ps.setObject(13, account.getQq());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("新增账务账号失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public Account findById(int id) throws DAOException {
		String sql = "select * from account where id=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account a = createAccount(rs);
				if(a.getRecommenderId() != null) {
					// 多查询出一个字段，因此单独处理一下
					String sql2 = "select idcard_no from account where id=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, a.getRecommenderId());
					ResultSet rs2 = ps2.executeQuery();
					if(rs2.next()) {
						a.setRecommenderIdCardNo(rs2.getString("idcard_no"));
					}
				}
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	@Override
	public void updateAccount(Account account) throws DAOException {
		if (account == null)
			return;
		Connection con = DBUtil.getConnection();
		try {
			String sql = "update account set "
					+ "REAL_NAME=?,TELEPHONE=?,RECOMMENDER_ID=?,EMAIL=?,"
					+ "OCCUPATION=?,GENDER=?,MAILADDRESS=?,ZIPCODE=?,QQ=? ";
			if (account.getLoginPassword() != null) {
				sql += ",LOGIN_PASSWD=? ";
			}
			sql += " where ID=? ";

			PreparedStatement ps = con.prepareStatement(sql);
			int index = 1;
			ps.setObject(index++, account.getRealName());
			ps.setObject(index++, account.getTelephone());
			ps.setObject(index++, account.getRecommenderId());
			ps.setObject(index++, account.getEmail());
			ps.setObject(index++, account.getOccupation());
			ps.setObject(index++, account.getGender());
			ps.setObject(index++, account.getMailaddress());
			ps.setObject(index++, account.getZipcode());
			ps.setObject(index++, account.getQq());
			if (account.getLoginPassword() != null) {
				ps.setObject(index++, account.getLoginPassword());
			}
			ps.setObject(index++, account.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("更新账务账号失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

}
