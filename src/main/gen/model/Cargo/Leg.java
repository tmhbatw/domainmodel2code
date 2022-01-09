package main.gen.model.Cargo;


import main.gen.annotation.AggregatePart;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.ValueObject;

import java.util.Date;

@AggregatePart(aggregateRootType = Itinerary.class)
@ValueObject
public class Leg{
	 
	//生成值对象的属性
	@DomainAttribute
	private Date loadTime;
	 
	//生成值对象的属性
	@DomainAttribute
	private Date unloadTime;
	 
	 
		
		
	//用于生成值对象的含参构造函数
	public Leg(Date loadTime,Date unloadTime){
	 
		this.loadTime=loadTime;
	 
		this.unloadTime=unloadTime;
	
	}
	//用于生成值对象的不含参构造函数，如果值对象本身不含参数，那么可以直接删除
//	public Leg(){
//
//	}


	//值对象属性的getter以及setter方法
	 
	public Date getLoadTime(){
	
		return this.loadTime;
		
	}
	
	public void setLoadTime(Date cur){
	
		this.loadTime=loadTime;
	
	}
		
	 
	public Date getUnloadTime(){
	
		return this.unloadTime;
		
	}
	
	public void setUnloadTime(Date cur){
	
		this.unloadTime=unloadTime;
	
	}
		
}

