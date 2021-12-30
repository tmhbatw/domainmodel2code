package gen.Cargo;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregatePart(aggregateRootType = Itinerary.class)
@ValueObject

public class Leg{

	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Date loadTime;
	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Date unloadTime;
	
	
	 
	 
		
		
	//生成值对象的含参构造函数
	public Leg(Date loadTime,Date unloadTime){
	 
		this.loadTime=loadTime;
	 
		this.unloadTime=unloadTime;
	
	}
	

	//生成值对象的无参构造函数
	public Leg(){
		this.name = Leg;
	}
	
	



	//该值对象属性所对应的getter、setter方法
	 
	 
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

