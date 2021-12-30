package gen.Location;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@Entity(identifier = "portCode")

public class Location{


	 
	
	//所生成的实体属性
	@DomainAttribute
	private String portCode;
	
	 
		
		
	//含参构造函数
	public Location(String portCode){
	 
		this.portCode=portCode;
	
	}
	
	//不含参构造函数
	public Location(){
	
	}
	



	//属性的getter与setter方法
	 
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}
