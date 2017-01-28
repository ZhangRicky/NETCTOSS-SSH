package com.tarena.action.bill;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.dao.bill.IBillDao;
import com.tarena.entity.Bill;

@Controller
@Scope("prototype")
public class FindBillItemAction {
	
	@Resource
	private IBillDao billDao;
	
	private int billId;
	
	private Bill bill;
	
	public String load() {
		bill = billDao.findBillById(billId);
		return "success";
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	

}
