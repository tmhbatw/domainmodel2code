package main.gen.model.Voyage;

import main.gen.annotation.AggregateRoot;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.Entity;

@AggregateRoot
@Entity(identifier = "voyageNumber")

public class Voyage{
	 
	
	//实体属性
	@DomainAttribute
	private String voyageNumber;
	 
		
	//实体的含参构造函数
	public Voyage(String voyageNumber){
	 
		this.voyageNumber=voyageNumber;
	
	}
	//该实体的不含参构造函数
//	public Voyage(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public String getVoyageNumber(){
	
		return this.voyageNumber;
		
	}
	
	public void setVoyageNumber(String cur){
	
		this.voyageNumber=voyageNumber;
	
	}
		
}
