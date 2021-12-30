package gen.Cargo;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "itineraryNumber")

public class Itinerary{


	 
	
	//所生成的实体属性
	@DomainAttribute
	private String itineraryNumber;
	
	 
		
		
	//含参构造函数
	public Itinerary(String itineraryNumber){
	 
		this.itineraryNumber=itineraryNumber;
	
	}
	
	//不含参构造函数
	public Itinerary(){
	
	}
	



	//属性的getter与setter方法
	 
	 
	public String getItineraryNumber(){
	
		return this.itineraryNumber;
		
	}
	
	public void setItineraryNumber(String cur){
	
		this.itineraryNumber=itineraryNumber;
	
	}
		
}
