package com.example.maoliang.Entity;
import javax.persistence.Entity;

@Entity
public class OrderList {
	private Order or;

	public Order getOr() {
		return or;
	}

	public void setOr(Order or) {
		this.or = or;
	}
	
}
