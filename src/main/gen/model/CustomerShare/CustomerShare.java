package main.gen.model.CustomerShare;


import main.gen.annotation.DomainAttribute;
import main.gen.annotation.ValueObject;


@ValueObject
public class CustomerShare{
	 
	//生成值对象的属性
	@DomainAttribute
	private String CustomerId;
	 
		
		
	//用于生成值对象的含参构造函数
	public CustomerShare(String CustomerId){
	 
		this.CustomerId=CustomerId;
	
	}
	//用于生成值对象的不含参构造函数，如果值对象本身不含参数，那么可以直接删除
//	public CustomerShare(){
//
//	}


	//值对象属性的getter以及setter方法
	 
	public String getCustomerId(){
	
		return this.CustomerId;
		
	}
	
	public void setCustomerId(String cur){
	
		this.CustomerId=CustomerId;
	
	}
		
}

