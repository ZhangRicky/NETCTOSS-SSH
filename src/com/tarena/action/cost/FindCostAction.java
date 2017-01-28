package com.tarena.action.cost;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.DAOException;
import com.tarena.dao.cost.ICostDao;
import com.tarena.entity.Cost;

@Controller
@Scope("prototype")
public class FindCostAction {

	private int page = 1;
	private int totalPages;
	private int pageSize;
	private List<Cost> feeList;
	
	@Resource
	private ICostDao costDao;
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Cost> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<Cost> feeList) {
		this.feeList = feeList;
	}

	public String execute() {
		try {
			feeList = costDao.findByPage(page, pageSize);
			totalPages = costDao.findTotalPages(pageSize);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
