package gen.Location;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;
import gen.annotation.AggregateRoot;
import gen.annotation.AggregatePart;


@Entity(identifier = "portCode")

public class Location{
	 
	
	//实体属性
	@DomainAttribute
	private String portCode;
	 
		
	//实体的含参构造函数
	public Location(String portCode){
	 
		this.portCode=portCode;
	
	}
	//该实体的不含参构造函数
//	public Location(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}
