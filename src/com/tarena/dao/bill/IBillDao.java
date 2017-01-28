package com.tarena.dao.bill;

import java.util.List;

import com.tarena.condition.BillCondition;
import com.tarena.entity.Bill;
import com.tarena.entity.BillItem;
import com.tarena.entity.ServiceDetail;

public interface IBillDao {

	List<Bill> findBillByPage(BillCondition condition);

	int findBillTotalPage(BillCondition condition);

	Bill findBillById(int id);

	Integer findSumDuration(Integer serviceId, String monthId);
	
	List<ServiceDetail> findServiceDetail(Integer serviceId, String monthId);

	BillItem findBillItemById(int id);

}
