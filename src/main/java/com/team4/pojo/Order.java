package com.team4.pojo;

public class Order {
	private int orderId;
	private String symbol;
	private int traderId;
	private String side;
	private int Qty;
	private float price;
	private String FOK;
	private String condition;
	private String status;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getTraderId() {
		return traderId;
	}
	public void setTraderId(int traderId) {
		this.traderId = traderId;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		Qty = qty;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getFOK() {
		return FOK;
	}
	public void setFOK(String fOK) {
		FOK = fOK;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Order(int orderId, String symbol, int traderId, String side,
			int qty, float price, String fOK, String condition, String status) {
		super();
		this.orderId = orderId;
		this.symbol = symbol;
		this.traderId = traderId;
		this.side = side;
		Qty = qty;
		this.price = price;
		FOK = fOK;
		this.condition = condition;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", symbol=" + symbol
				+ ", traderId=" + traderId + ", side=" + side + ", Qty=" + Qty
				+ ", price=" + price + ", FOK=" + FOK + ", condition="
				+ condition + ", status=" + status + "]";
	}
	
	
}
