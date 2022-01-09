package main.gen.model.Customer;

import main.gen.annotation.DomainAttribute;
import main.gen.annotation.Entity;


@Entity(identifier = "customerId")

public class Customer{
	 
	
	//实体属性
	@DomainAttribute
	private String name;
	 
	
	//实体属性
	@DomainAttribute
	private String customerId;
	 
	 
		
	//实体的含参构造函数
	public Customer(String name,String customerId){
	 
		this.name=name;
	 
		this.customerId=customerId;
	
	}
	//该实体的不含参构造函数
//	public Customer(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public String getName(){
	
		return this.name;
		
	}
	
	public void setName(String cur){
	
		this.name=name;
	
	}
		
	 
	public String getCustomerId(){
	
		return this.customerId;
		
	}
	
	public void setCustomerId(String cur){
	
		this.customerId=customerId;
	
	}
		
}
