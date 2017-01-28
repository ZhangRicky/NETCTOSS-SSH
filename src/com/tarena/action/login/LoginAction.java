package com.tarena.action.login;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.action.BaseAction;
import com.tarena.dao.DAOException;
import com.tarena.dao.admin.IAdminDao;
import com.tarena.entity.Admin;

/**
 * 用户登录
 * @author Ricky
 *
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	
	@Resource
	private IAdminDao adminDao;

	// 输入
	private Admin admin;
	private String imageCode;

	// 输出
	private String errorMsg;
	
	/**
	 * 根据前台提交的用户信息进行验证，
	 * 验证不通过，则返回login.jsp
	 * 验证通过，同时查询出用户所拥有的权限，同时加入session，方便后续使用
	 */
	public String execute() {
		//校验验证码
		String code = (String) session.get("imageCode");
		if (code == null || imageCode == null 
				|| !code.equalsIgnoreCase(imageCode)) {
			errorMsg = "请输入正确的验证码！";
			return "fail";
		}
		
		Admin u = null;		//用户
		try {
			u = adminDao.findByCode(admin.getAdminCode());
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}

		if(u == null) {
			errorMsg = "用户名错误";
			return "fail";
		} else if (!u.getPassword().equals(admin.getPassword())){
			errorMsg = "密码错误";
			return "fail";
		} else {
			session.put("user", u);			//将用户放入到session
			try {
				//查询登录用户有权限的模块
				List<Integer> privilegeIds = adminDao
						.findPrivilegeIdsByAdmin(u.getId());
				Collections.sort(privilegeIds);
				//将用户的权限ID加入到session中，后续使用
				session.put("privilegeIds", privilegeIds);
			} catch (DAOException e) {
				e.printStackTrace();
				return "error";
			}
			return "success";
		}
		
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

}
