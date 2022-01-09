package main.gen.model.Cargo;

import main.gen.annotation.AggregateRoot;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.Entity;
import main.gen.model.Location.Location;

import java.util.List;

@AggregateRoot
@Entity(identifier = "trackingId")

public class Cargo{
	 
	
	//实体属性
	@DomainAttribute
	private String trackingId;

	List<Location> list;

	Itinerary a;
		
	//实体的含参构造函数
	public Cargo(String trackingId){
	 
		this.trackingId=trackingId;
	
	}
	//该实体的不含参构造函数
	public Cargo(){

	}
	//实体属性的getter以及setter方法
	 
	String getTrackingId(){
	
		return this.trackingId;
		
	}
	
	public void setTrackingId(String cur){
	
		this.trackingId=trackingId;
	
	}
		
}
