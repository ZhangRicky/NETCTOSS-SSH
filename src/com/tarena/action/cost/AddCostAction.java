package com.tarena.action.cost;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tarena.dao.DAOException;
import com.tarena.dao.cost.ICostDao;
import com.tarena.entity.Cost;

@Controller
@Scope("prototype")
public class AddCostAction {

	//  ‰»Î
	private Cost cost;
	
	@Resource
	private ICostDao costDao;

	public String execute() {
		try {
			costDao.insert(cost);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

}
