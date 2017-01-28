package com.tarena.action.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.service.IServiceDao;
import com.tarena.entity.Service;

@Controller
@Scope("prototype")
public class UpdateServiceAction {
	
	@Resource
	private IServiceDao serDao;
	
	// input
	private Service service;
	
	public String execute() {
		try {
			serDao.updateService(service);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
