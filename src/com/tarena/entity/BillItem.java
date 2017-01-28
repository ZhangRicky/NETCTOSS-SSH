package com.tarena.entity;

public class BillItem {

	private Integer itemId;
	private Double cost;
	private Bill bill;
	private Service service;
	
	private Integer sofarDuration;
	
	public Integer getItemId() {
		return itemId;
	}

	public Integer getSofarDuration() {
		return sofarDuration;
	}

	public void setSofarDuration(Integer sofarDuration) {
		this.sofarDuration = sofarDuration;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
