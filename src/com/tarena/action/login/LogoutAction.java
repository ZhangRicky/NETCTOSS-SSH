package com.tarena.action.login;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.action.BaseAction;

@Controller
@Scope("prototype")
public class LogoutAction extends BaseAction {

	private boolean pass;

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String execute() {
		//ɾ��session
		ServletActionContext.getRequest().getSession().invalidate();
		pass = true;
		return "success";
	}

}
