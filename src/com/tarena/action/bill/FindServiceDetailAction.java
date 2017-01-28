package com.tarena.action.bill;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tarena.dao.bill.IBillDao;
import com.tarena.entity.BillItem;
import com.tarena.entity.ServiceDetail;

@Controller
@Scope("prototype")
public class FindServiceDetailAction {

	@Resource
	private IBillDao billDao;

	private Integer billItemId;
	private BillItem billItem;
	private List<ServiceDetail> serviceDetails;

	public String load() {
		billItem = billDao.findBillItemById(billItemId);
		serviceDetails = billDao.findServiceDetail(
				billItem.getService().getId(), 
				billItem.getBill().getBillMonth());
		return "success";
	}

	public Integer getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(Integer billItemId) {
		this.billItemId = billItemId;
	}

	public BillItem getBillItem() {
		return billItem;
	}

	public void setBillItem(BillItem billItem) {
		this.billItem = billItem;
	}

	public List<ServiceDetail> getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(List<ServiceDetail> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

}
