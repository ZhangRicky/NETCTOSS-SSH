package com.tarena.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;
import com.tarena.util.DBUtil;

public class AdminDaoImpl implements IAdminDao {
	
	/**
	 * 增加角色
	 */
	@Override
	public void addAdmin(Admin admin) throws DAOException {
		Connection con = DBUtil.getConnection();
		try {
			con.setAutoCommit(false);
			// 新增角色
			String sql = "insert into ADMIN_INFO values(admin_seq.nextval,?,?,?,?,?,SYSDATE)";
			String[] columns = new String[] { "id" };
			PreparedStatement ps = con.prepareStatement(sql, columns);
			ps.setString(1, admin.getAdminCode());
			ps.setString(2, admin.getPassword());
			ps.setString(3, admin.getName());
			ps.setString(4, admin.getTelephone());
			ps.setString(5, admin.getEmail());
			ps.executeUpdate();

			// 取得生成的ID
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int adminId = rs.getInt(1);

			// 新增管理员角色
			sql = "insert into ADMIN_ROLE values(?,?)";
			List<Integer> roleIds = null;//admin.getRoleIds();
			if (roleIds != null) {
				ps = con.prepareStatement(sql);
				for (Integer roleId : roleIds) {
					ps.setInt(1, adminId);
					ps.setInt(2, roleId);
					ps.addBatch();
				}
				ps.executeBatch();
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException("新增管理员失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public List<Admin> findByPage(Integer roleId, Integer privilegeId,
			int page, int pageSize) throws DAOException {
		List<Admin> admins = new ArrayList<Admin>();
		Connection con = DBUtil.getConnection();
		try {
			//1.查询管理员数据
			List<Object> params = new ArrayList<Object>();
			String sql = createFindSQL(privilegeId, roleId, page, pageSize, params);
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Admin admin = createAdmin(rs);
				//2.查询管理员角色
				String sql2 = "select name from role_info where id in (";
				sql2 += "select role_id from admin_role where admin_id=? ";
				sql2 += ")";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setInt(1, admin.getId());
				ResultSet rs2 = ps2.executeQuery();
				String nameStr = "";
				while(rs2.next()){
					String name = rs2.getString(1);
					nameStr += "," +name;
				}
				if(nameStr.length() > 0)
					nameStr = nameStr.replaceFirst(",", "");
//				admin.setRoleNames(nameStr);
				admins.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询管理员失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return admins;
	}

	private String createFindSQL(Integer privilegeId, Integer roleId, int page,
			int pageSize, List<Object> params) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("select a.*,rownum r from admin_info a ");
		sb.append("where id in(");
		sb.append("	select ar.admin_id from admin_role ar ");
		sb.append("	inner join role_privilege rp on ar.role_id=rp.role_id ");
		sb.append("	where 1=1  ");
		if(roleId != null && roleId.toString().length() > 0) {
			sb.append("and ar.role_id=? ");
			params.add(roleId);
		}
		if(privilegeId != null && privilegeId.toString().length() > 0) {
			sb.append("and rp.privilege_id=? ");
			params.add(privilegeId);
		}
		sb.append(") ");

		sb.append("and rownum<? ");
		int nextMin = page*pageSize + 1;
		params.add(nextMin);
		
		sb.append(") where r>? ");
		int lastMax = (page-1)*pageSize;
		params.add(lastMax);
		
		return sb.toString();
	}

	private Admin createAdmin(ResultSet rs) throws SQLException {
		Admin a = new Admin();
		a.setId(rs.getInt("id"));
		a.setAdminCode(rs.getString("ADMIN_CODE"));
		a.setPassword(rs.getString("PASSWORD"));
		a.setName(rs.getString("NAME"));
		a.setTelephone(rs.getString("TELEPHONE"));
		a.setEmail(rs.getString("EMAIL"));
		a.setEnrollDate(rs.getDate("ENROLLDATE"));
		return a;
	}

	@Override
	public int findTotalPage(Integer roleId, Integer privilegeId, int pageSize)
			throws DAOException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from admin_info ");
		sb.append("where id in (");
		
		sb.append("select ar.admin_id from admin_role ar ");
		sb.append("inner join role_privilege rp on ar.role_id=rp.role_id ");
		sb.append("where 1=1 ");
		if(roleId != null && roleId.toString().length() > 0) {
			sb.append("and ar.role_id=? ");
			params.add(roleId);
		}
		if(privilegeId != null && privilegeId.toString().length() > 0) {
			sb.append("and rp.privilege_id=? ");
			params.add(privilegeId);
		}
		
		sb.append(")");
		
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int rows = rs.getInt(1);
				if(rows % pageSize == 0) {
					return rows/pageSize;
				} else {
					return rows/pageSize+1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询总页数失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		
		
		return 0;
	}
	
	@Override
	public void resetPassword(String[] ids) throws DAOException {
		if (ids == null || ids.length == 0)
			return;
		Connection con = DBUtil.getConnection();
		try {
			String sql = "update admin_info set password='123456' where id in (";
			for (int i = 0; i < ids.length; i++) {
				sql += (i == 0) ? "?" : ",?";
			}
			sql += ")";
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < ids.length; i++) {
				ps.setString(i + 1, ids[i]);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("重置密码失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}
	
	/**
	 * 根据角色ID查询该角色的权限ID
	 */
	@Override
	public List<Integer> findPrivilegeIdsByAdmin(Integer adminId)
			throws DAOException {
		if (adminId == null)
			return null;

		StringBuffer sb = new StringBuffer();
		sb.append("select distinct p.PRIVILEGE_ID from admin_info ai ");
		sb.append("left join admin_role ar on ai.id=ar.admin_id ");
		sb.append("left join role_info ri on ar.role_id=ri.id ");
		sb.append("left join role_privilege p on ri.id=p.role_id ");
		sb.append("where ai.id=? order by p.PRIVILEGE_ID ");

		List<Integer> list = new ArrayList<Integer>();
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setInt(1, adminId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询管理员权限失败！", e);
		} finally {
			DBUtil.closeConnection();
		}

		return list;
	}
	
	/**
	 * 根据code查询用户
	 */
	@Override
	public Admin findByCode(String code) throws DAOException {
		if (code == null)
			return null;
		Connection con = DBUtil.getConnection();
		String sql = "select * from admin_info where admin_code=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Admin u = createAdmin(rs);
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询用户失败", e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String name = "员";
		Connection con = DBUtil.getConnection();
		String sql = "select * from role_info where name like '%'||?||'%' ";
		try {
			PreparedStatement ps = 
					con.prepareStatement(sql);
			ps.setObject(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Admin findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Admin admin) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
