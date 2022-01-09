package main.gen.model.Cargo;

import main.gen.annotation.AggregatePart;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.Entity;

import java.util.Date;

@AggregatePart(aggregateRootType = Cargo.class)
@Entity(identifier = "handlingEventId")

public class HandlingEvent{
	 
	
	//实体属性
	@DomainAttribute
	private Date completionTime;
	 
	
	//实体属性
	@DomainAttribute
	private String handlingEventId;
	 
	 
		
	//实体的含参构造函数
	public HandlingEvent(Date completionTime,String handlingEventId){
	 
		this.completionTime=completionTime;
	 
		this.handlingEventId=handlingEventId;
	
	}
	//该实体的不含参构造函数
//	public HandlingEvent(){
//	
//	}
	//实体属性的getter以及setter方法
	 
	public Date getCompletionTime(){
	
		return this.completionTime;
		
	}
	
	public void setCompletionTime(Date cur){
	
		this.completionTime=completionTime;
	
	}
		
	 
	public String getHandlingEventId(){
	
		return this.handlingEventId;
		
	}
	
	public void setHandlingEventId(String cur){
	
		this.handlingEventId=handlingEventId;
	
	}
		
}
