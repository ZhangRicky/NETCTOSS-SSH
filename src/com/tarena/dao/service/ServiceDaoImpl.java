package com.tarena.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Service;
import com.tarena.util.DBUtil;
import com.tarena.vo.ServiceVO;

public class ServiceDaoImpl implements IServiceDao {

//	@Override
//	public List<ServiceVO> findByCondition(String osUserName, String unixHost,
//			String idCardNo, String status, int page, int pageSize)
//			throws DAOException {
//		List<Object> paramList = new ArrayList<Object>();
//		StringBuffer sb = new StringBuffer();
//		sb.append("select s.*,a.IDCARD_NO,a.REAL_NAME,c.NAME,c.DESCR,rownum r from SERVICE s ");
//		sb.append("inner join ACCOUNT a on a.id=s.ACCOUNT_ID ");
//		sb.append("inner join COST c on c.id=s.COST_ID ");
//		sb.append("where 1=1 ");
//		if (osUserName != null && osUserName.length() > 0) {
//			sb.append("and s.OS_USERNAME=? ");
//			paramList.add(osUserName);
//		}
//		if (unixHost != null && unixHost.length() > 0) {
//			sb.append("and s.UNIX_HOST=? ");
//			paramList.add(unixHost);
//		}
//		if (idCardNo != null && idCardNo.length() > 0) {
//			sb.append("and a.IDCARD_NO=? ");
//			paramList.add(idCardNo);
//		}
//		if (status != null && !status.equals("-1")) {
//			sb.append("and s.STATUS=? ");
//			paramList.add(status);
//		}
//
//		String sql = "select * from (" + sb.toString() + " and rownum<? "
//				+ ") where r>? ";
//
//		int nextMin = page * pageSize + 1;
//		int lastMax = (page - 1) * pageSize;
//		paramList.add(nextMin);
//		paramList.add(lastMax);
//
//		Connection con = DBUtil.getConnection();
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			for (int i = 0; i < paramList.size(); i++) {
//				Object obj = paramList.get(i);
//				ps.setObject(i + 1, obj);
//			}
//			ResultSet rs = ps.executeQuery();
//			List<ServiceVO> list = null;
//			while (rs.next()) {
//				ServiceVO a = createServiceVO(rs);
//				if (list == null)
//					list = new ArrayList<ServiceVO>();
//				list.add(a);
//			}
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException("查询业务信息失败！", e);
//		} finally {
//			DBUtil.closeConnection();
//		}
//	}

	private ServiceVO createServiceVO(ResultSet rs) throws SQLException {
		ServiceVO s = new ServiceVO();
		s.setId(rs.getInt("ID"));
//		s.setAccountId(rs.getInt("ACCOUNT_ID"));
		s.setUnixHost(rs.getString("UNIX_HOST"));
		s.setOsUserName(rs.getString("OS_USERNAME"));
		s.setLoginPassword(rs.getString("LOGIN_PASSWD"));
		s.setStatus(rs.getString("STATUS"));
		s.setCreateDate(rs.getDate("CREATE_DATE"));
		s.setPauseDate(rs.getDate("PAUSE_DATE"));
		s.setCloseDate(rs.getDate("CLOSE_DATE"));
//		s.setCostId(rs.getInt("COST_ID"));
		s.setIdcardNo(rs.getString("IDCARD_NO"));
		s.setRealName(rs.getString("REAL_NAME"));
		s.setCostName(rs.getString("NAME"));
		s.setDescr(rs.getString("DESCR"));
		return s;
	}
	
	private Service createService(ResultSet rs) throws SQLException {
		Service s = new Service();
		s.setId(rs.getInt("ID"));
//		s.setAccountId(rs.getInt("ACCOUNT_ID"));
		s.setUnixHost(rs.getString("UNIX_HOST"));
		s.setOsUserName(rs.getString("OS_USERNAME"));
		s.setLoginPassword(rs.getString("LOGIN_PASSWD"));
		s.setStatus(rs.getString("STATUS"));
		s.setCreateDate(rs.getDate("CREATE_DATE"));
		s.setPauseDate(rs.getDate("PAUSE_DATE"));
		s.setCloseDate(rs.getDate("CLOSE_DATE"));
//		s.setCostId(rs.getInt("COST_ID"));
		return s;
	}

	@Override
	public int findTotalPage(String osUserName, String unixHost,
			String idCardNo, String status, int pageSize) throws DAOException {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from SERVICE s ");
		sb.append("inner join ACCOUNT a on a.id=s.ACCOUNT_ID ");
		sb.append("inner join COST c on c.id=s.COST_ID ");
		sb.append("where 1=1 ");
		if (osUserName != null && osUserName.length() > 0) {
			sb.append("and s.OS_USERNAME=? ");
			paramList.add(osUserName);
		}
		if (unixHost != null && unixHost.length() > 0) {
			sb.append("and s.UNIX_HOST=? ");
			paramList.add(unixHost);
		}
		if (idCardNo != null && idCardNo.length() > 0) {
			sb.append("and a.IDCARD_NO=? ");
			paramList.add(idCardNo);
		}
		if (status != null && !status.equals("-1")) {
			sb.append("and s.STATUS=? ");
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
			throw new DAOException("查询业务信息失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return 0;
	}

	@Override
	public void addService(Service service) throws DAOException {
		if (service == null)
			return;
		String sql = "insert into SERVICE"
				+ "(ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,COST_ID) "
				+ "values(service_seq.nextval,?,?,?,?,'0',SYSDATE,?)";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setObject(1, service.getAccountId());
			ps.setObject(2, service.getUnixHost());
			ps.setObject(3, service.getOsUserName());
			ps.setObject(4, service.getLoginPassword());
//			ps.setObject(5, service.getCostId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("新增业务信息失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public int[] findServiceIdsByAccountId(int id) throws DAOException {
		String sql = "select id from SERVICE where ACCOUNT_ID=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			List<Integer> list = new ArrayList<Integer>();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}

			int[] ids = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = list.get(i);
			}
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	@Override
	public Service findById(int id) throws DAOException {
		String sql = "select * from SERVICE where ID=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareCall(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return createService(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询业务账号失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	@Override
	public void updateService(Service service) 
			throws DAOException {
		if(service == null)
			return;
		String sql = "insert into SERVICE_UPDATE_BAK values(SERVICE_UPDATE_BAK_SEQ.NEXTVAL,?,?)";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareCall(sql);
			ps.setInt(1, service.getId());
//			ps.setInt(2, service.getCostId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("更新业务账号失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public void pauseByAccount(int accountId) throws DAOException {
		
	}

	@Override
	public void startService(int id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseService(int id) throws DAOException {
		
	}

	@Override
	public void deleteService(int id) throws DAOException {
		
	}

	@Override
	public List<Service> findByCondition(String osUserName, String unixHost,
			String idCardNo, String status, int page, int pageSize)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
