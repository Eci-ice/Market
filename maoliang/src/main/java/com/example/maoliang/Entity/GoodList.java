package com.example.maoliang.Entity;
import javax.persistence.Entity;

@Entity
public class GoodList {
	private Good g;

	public Good getG() {
		return g;
	}

	public void setG(Good g) {
		this.g = g;
	}

	@Override
	public String toString() {
		return "goodlist [g=" + g + "]";
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
