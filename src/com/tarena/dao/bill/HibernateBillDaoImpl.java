package com.tarena.dao.bill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tarena.condition.BillCondition;
import com.tarena.dao.BaseDao;
import com.tarena.entity.Bill;
import com.tarena.entity.BillItem;
import com.tarena.entity.ServiceDetail;

@Repository
public class HibernateBillDaoImpl extends BaseDao implements IBillDao {

	@Override
	public List<Bill> findBillByPage(final BillCondition condition) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				List<Object> params = new ArrayList<Object>();
				StringBuffer sb = new StringBuffer();
				sb.append("from Bill where 1=1 ");
				if(notNull(condition.getIdcardNo())) {
					sb.append("and account.idcardNo=? ");
					params.add(condition.getIdcardNo());
				}
				if(notNull(condition.getLoginName())) {
					sb.append("and account.loginName=? ");
					params.add(condition.getLoginName());
				}
				if(notNull(condition.getRealName())) {
					sb.append("and account.realName=? ");
					params.add(condition.getRealName());
				}
				if(notNull(condition.getYear())
						&& notNull(condition.getMonth())) {
					sb.append("and billMonth=? ");
					params.add(condition.getYear() + condition.getMonth());
				}
				
				Query query = session.createQuery(sb.toString());
				for(int i=0;i<params.size();i++) {
					query.setParameter(i, params.get(i));
				}
				
				query.setFirstResult(
					(condition.getPage()-1)*condition.getPageSize());
				query.setMaxResults(condition.getPageSize());
				
				return query.list();
			}
		});
	}

	@Override
	public int findBillTotalPage(BillCondition condition) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Bill where 1=1 ");
		if(notNull(condition.getIdcardNo())) {
			sb.append("and account.idcardNo=? ");
			params.add(condition.getIdcardNo());
		}
		if(notNull(condition.getLoginName())) {
			sb.append("and account.loginName=? ");
			params.add(condition.getLoginName());
		}
		if(notNull(condition.getRealName())) {
			sb.append("and account.realName=? ");
			params.add(condition.getRealName());
		}
		if(notNull(condition.getYear())
				&& notNull(condition.getMonth())) {
			sb.append("and billMonth=? ");
			params.add(condition.getYear() + condition.getMonth());
		}	
		
		List<Object> list = getHibernateTemplate()
				.find(sb.toString(), params.toArray());
		int rows = Integer.valueOf(list.get(0).toString());
		if(rows%condition.getPageSize()==0) {
			return rows/condition.getPageSize();
		} else {
			return rows/condition.getPageSize()+1;
		}
		
	}

	@Override
	public Bill findBillById(int id) {
		Bill bill = (Bill) getHibernateTemplate().load(Bill.class, id);
		Set<BillItem> items = bill.getBillItems();
		for (BillItem item : items) {
			Integer serviceId = item.getService().getId();
			String monthId = item.getBill().getBillMonth();
			Integer sofarDuration = this.findSumDuration(serviceId, monthId);
			item.setSofarDuration(sofarDuration);
		}
		return bill;
	}
	
	@Override
	public List<ServiceDetail> findServiceDetail(
			final Integer serviceId, final String monthId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				// 查询条件中需要匹配日期,Hibernate无法支持该函数,因此改用sql实现
				String sql = "select * from service_detail " +
						"where service_id=? " +
						"and to_char(logout_time,'yyyyMM')=?";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, serviceId);
				query.setParameter(1, monthId);
				query.addEntity(ServiceDetail.class);
				return query.list();
			}
		});
	}
	
	@Override
	public BillItem findBillItemById(int id) {
		return (BillItem) 
				getHibernateTemplate().load(BillItem.class, id);
	}
	
	public static void main(String[] args) {
		String monthId = "201306";
		System.out.println(
			monthId.substring(0, 4) + "-" +
			monthId.substring(4, 6)
		);
	}

	@Override
	public Integer findSumDuration(final Integer serviceId, final String monthId) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				String sql = "select sum(duration) from service_detail " +
						"where service_id=? " +
						"and to_char(logout_time,'yyyyMM')=?";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, serviceId);
				query.setParameter(1, monthId);
				Object obj = query.uniqueResult();
				return obj == null ? null : 
					Integer.valueOf(obj.toString());
			}
		});
	}

}
