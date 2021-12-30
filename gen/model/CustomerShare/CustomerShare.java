package gen.CustomerShare;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@ValueObject

public class CustomerShare{

	 
	
	//值对象所包含的属性
	@DomainAttribute
	private String CustomerId;
	
	
	 
		
		
	//生成值对象的含参构造函数
	public CustomerShare(String CustomerId){
	 
		this.CustomerId=CustomerId;
	
	}
	

	//生成值对象的无参构造函数
	public CustomerShare(){
		this.name = CustomerShare;
	}
	
	



	//该值对象属性所对应的getter、setter方法
	 
	 
	public String getCustomerId(){
	
		return this.CustomerId;
		
	}
	
	public void setCustomerId(String cur){
	
		this.CustomerId=CustomerId;
	
	}
		
}

