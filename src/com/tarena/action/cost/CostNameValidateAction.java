package com.tarena.action.cost;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.cost.ICostDao;
import com.tarena.entity.Cost;

@Controller
@Scope("prototype")
public class CostNameValidateAction {
	
	private Integer id;
	private String name;
	private boolean pass;
	
	@Resource
	private ICostDao costDao;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute() {
		Cost cost = null;
		try {
			cost = costDao.findByName(id, name);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}

		if (cost == null) {
			pass = true;
		} else {
			pass = false;
		}

		return "success";
	}

}
