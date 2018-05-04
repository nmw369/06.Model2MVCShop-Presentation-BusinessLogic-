package com.model2.mvc.service.domain;

import java.sql.Date;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


public class Purchase {
	
	private User buyer;
	private String dlvyAddr;
	private String dlvyDate;
	private String dlvyRequest;
	private Date orderDate;
	private String paymentOption;
	private Product purchaseProd;
	private String receiverName;
	private String receiverPhone;
	private String tranCode;
	private int tranNo;
	private int sEA;
	
	public Purchase(){
	}
	
	
	
	public int getsEA() {
		return sEA;
	}



	public void setsEA(int sEA) {
		this.sEA = sEA;
	}



	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public String getdlvyAddr() {
		return dlvyAddr;
	}
	public void setdlvyAddr(String dlvyAddr) {
		this.dlvyAddr = dlvyAddr;
	}
	public String getdlvyDate() {
		return dlvyDate;
	}
	public void setdlvyDate(String dlvyDate) {
		this.dlvyDate = dlvyDate;
	}
	public String getdlvyRequest() {
		return dlvyRequest;
	}
	public void setdlvyRequest(String dlvyRequest) {
		this.dlvyRequest = dlvyRequest;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption.trim();
	}
	public Product getPurchaseProd() {
		return purchaseProd;
	}
	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode.trim();
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
	@Override
	public String toString() {
		return "Purchase [buyer=" + buyer + ", dlvyAddr=" + dlvyAddr
				+ "\n, dlvyDate=" + dlvyDate + ", dlvyRequest=" + dlvyRequest
				+ "\n, orderDate=" + orderDate + ", paymentOption=" + paymentOption 
				+ "\n, purchaseProd=" + purchaseProd + ", receiverName=" + receiverName 
				+ "\n, receiverPhone=" + receiverPhone + ", tranCode=" + tranCode 
				+ "\n, tranNo=" + tranNo +"::"+sEA+"]";
	}
}