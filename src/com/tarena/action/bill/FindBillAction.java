package com.tarena.action.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tarena.condition.BillCondition;
import com.tarena.dao.bill.IBillDao;
import com.tarena.entity.Bill;

@Controller
@Scope("prototype")
public class FindBillAction {

	@Resource
	private IBillDao billDao;

	private BillCondition condition;
	private List<Bill> bills;
	private int totalPage;
	private List<String> years;

	public String load() {
		bills = billDao.findBillByPage(condition);
		totalPage = billDao.findBillTotalPage(condition);
		years = initYears();
		return "success";
	}

	private List<String> initYears() {
		List<String> years = new ArrayList<String>();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int year = Integer.valueOf(today.substring(0, 4));
		years.add(String.valueOf(year));
		for (int i = 0; i < 4; i++) {
			years.add(String.valueOf(--year));
		}
		return years;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public BillCondition getCondition() {
		return condition;
	}

	public void setCondition(BillCondition condition) {
		this.condition = condition;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
