package main.gen.model.Cargo;


import main.gen.annotation.AggregateRoot;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.Entity;

@AggregateRoot
@Entity(identifier = "itineraryNumber")

public class Itinerary{
	 
	
	//实体属性
	@DomainAttribute
	private String itineraryNumber;

	Cargo c;
	 
		
	//实体的含参构造函数
	public Itinerary(String itineraryNumber){
	 
		this.itineraryNumber=itineraryNumber;
	
	}
	//该实体的不含参构造函数
//	public Itinerary(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public String getItineraryNumber(){
	
		return this.itineraryNumber;
		
	}
	
	public void setItineraryNumber(String cur){
	
		this.itineraryNumber=itineraryNumber;
	
	}
		
}
