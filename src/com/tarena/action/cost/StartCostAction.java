package com.tarena.action.cost;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.cost.ICostDao;

@Controller
@Scope("prototype")
public class StartCostAction {
	
	private int id;
	private boolean success;
	
	@Resource
	private ICostDao costDao;
	
	public String execute() {
		try {
			costDao.start(id);
			success = true;
		} catch (DAOException e) {
			e.printStackTrace();
			success = false;
			return "error";
		}
		return "success";
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
