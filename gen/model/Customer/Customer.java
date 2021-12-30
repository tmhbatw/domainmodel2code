package gen.Customer;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@Entity(identifier = "customerId")

public class Customer{


	 
	
	//所生成的实体属性
	@DomainAttribute
	private String name;
	 
	
	//所生成的实体属性
	@DomainAttribute
	private String customerId;
	
	 
	 
		
		
	//含参构造函数
	public Customer(String name,String customerId){
	 
		this.name=name;
	 
		this.customerId=customerId;
	
	}
	
	//不含参构造函数
	public Customer(){
	
	}
	



	//属性的getter与setter方法
	 
	 
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
