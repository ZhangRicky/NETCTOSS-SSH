package com.tarena.action.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.cost.ICostDao;
import com.tarena.dao.service.IServiceDao;
import com.tarena.entity.Cost;
import com.tarena.entity.Service;

@Controller
@Scope("prototype")
public class ToUpdateServiceAction {
	
	@Resource
	private IServiceDao serDao;
	
	@Resource
	private ICostDao costDao;
	
	// input
	private int id;
	
	// output
	private Service service;
	private List<Cost> costs;
	
	public String execute() {
		try {
			service = serDao.findById(id);
			costs = costDao.findAll();
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public List<Cost> getCosts() {
		return costs;
	}
	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}
	

}
