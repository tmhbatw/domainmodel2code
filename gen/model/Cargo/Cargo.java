package gen.Cargo;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;
import gen.annotation.AggregateRoot;
import gen.annotation.AggregatePart;

@AggregateRoot
@Entity(identifier = "trackingId")

public class Cargo{
	 
	
	//实体属性
	@DomainAttribute
	private String trackingId;
	 
		
	//实体的含参构造函数
	public Cargo(String trackingId){
	 
		this.trackingId=trackingId;
	
	}
	//该实体的不含参构造函数
//	public Cargo(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public String getTrackingId(){
	
		return this.trackingId;
		
	}
	
	public void setTrackingId(String cur){
	
		this.trackingId=trackingId;
	
	}
		
}
