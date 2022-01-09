package main.gen.model.LocationShare;


import main.gen.annotation.DomainAttribute;
import main.gen.annotation.ValueObject;


@ValueObject
public class LocationShared{
	 
	//生成值对象的属性
	@DomainAttribute
	private String portCode;
	 
		
		
	//用于生成值对象的含参构造函数
	public LocationShared(String portCode){
	 
		this.portCode=portCode;
	
	}
	//用于生成值对象的不含参构造函数，如果值对象本身不含参数，那么可以直接删除
//	public LocationShared(){
//
//	}


	//值对象属性的getter以及setter方法
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}

