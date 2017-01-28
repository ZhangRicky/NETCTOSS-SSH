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
 * �û���¼
 * @author Ricky
 *
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	
	@Resource
	private IAdminDao adminDao;

	// ����
	private Admin admin;
	private String imageCode;

	// ���
	private String errorMsg;
	
	/**
	 * ����ǰ̨�ύ���û���Ϣ������֤��
	 * ��֤��ͨ�����򷵻�login.jsp
	 * ��֤ͨ����ͬʱ��ѯ���û���ӵ�е�Ȩ�ޣ�ͬʱ����session���������ʹ��
	 */
	public String execute() {
		//У����֤��
		String code = (String) session.get("imageCode");
		if (code == null || imageCode == null 
				|| !code.equalsIgnoreCase(imageCode)) {
			errorMsg = "��������ȷ����֤�룡";
			return "fail";
		}
		
		Admin u = null;		//�û�
		try {
			u = adminDao.findByCode(admin.getAdminCode());
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}

		if(u == null) {
			errorMsg = "�û�������";
			return "fail";
		} else if (!u.getPassword().equals(admin.getPassword())){
			errorMsg = "�������";
			return "fail";
		} else {
			session.put("user", u);			//���û����뵽session
			try {
				//��ѯ��¼�û���Ȩ�޵�ģ��
				List<Integer> privilegeIds = adminDao
						.findPrivilegeIdsByAdmin(u.getId());
				Collections.sort(privilegeIds);
				//���û���Ȩ��ID���뵽session�У�����ʹ��
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
