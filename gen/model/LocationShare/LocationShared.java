package gen.LocationShare;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@ValueObject

public class LocationShared{

	 
	
	//值对象所包含的属性
	@DomainAttribute
	private String portCode;
	
	
	 
		
		
	//生成值对象的含参构造函数
	public LocationShared(String portCode){
	 
		this.portCode=portCode;
	
	}
	

	//生成值对象的无参构造函数
	public LocationShared(){
		this.name = LocationShared;
	}
	
	



	//该值对象属性所对应的getter、setter方法
	 
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}

