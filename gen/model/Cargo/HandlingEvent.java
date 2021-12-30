package gen.Cargo;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregatePart(aggregateRootType = Cargo.class)
@Entity(identifier = "handlingEventId")

public class HandlingEvent{


	 
	
	//所生成的实体属性
	@DomainAttribute
	private Date completionTime;
	 
	
	//所生成的实体属性
	@DomainAttribute
	private String handlingEventId;
	
	 
	 
		
		
	//含参构造函数
	public HandlingEvent(Date completionTime,String handlingEventId){
	 
		this.completionTime=completionTime;
	 
		this.handlingEventId=handlingEventId;
	
	}
	
	//不含参构造函数
	public HandlingEvent(){
	
	}
	



	//属性的getter与setter方法
	 
	 
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
