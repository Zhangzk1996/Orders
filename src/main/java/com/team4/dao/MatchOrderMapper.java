package com.team4.dao;

import java.util.List;

import com.team4.pojo.MatchOrder;

public interface MatchOrderMapper {
	
	public void addMatchOrder(MatchOrder matchOrder);
	
	public MatchOrder getMatchOrderById(int matchOrderId);
	
	public List<MatchOrder> getAllMatchOrders();
	
	public int getMachOrderCount();
	
}
