package com.tarena.action.cost;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDao;
import com.tarena.entity.Cost;

@Controller
@Scope("prototype")
public class ToUpdateCostAction {

	//  ‰»Î
	private int id;
	private int page;

	//  ‰≥ˆ
	private Cost cost;
	
	@Resource
	private ICostDao costDao;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String execute() {
		try {
			cost = costDao.findById(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
