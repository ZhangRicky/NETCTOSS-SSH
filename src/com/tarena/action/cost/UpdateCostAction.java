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
public class UpdateCostAction {

	//  ‰»Î
	private Cost cost;
	private int page;

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

	public String execute() {
		try {
			costDao.update(cost);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
