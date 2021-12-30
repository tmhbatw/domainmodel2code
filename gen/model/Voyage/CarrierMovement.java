package gen.Voyage;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregatePart(aggregateRootType = Voyage.class)
@ValueObject

public class CarrierMovement{

	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Location departmentLocation;
	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Location arriveLocation;
	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Date departureTime;
	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Date arriveTime;
	
	
	 
	 
	 
	 
		
		
	//生成值对象的含参构造函数
	public CarrierMovement(Location departmentLocation,Location arriveLocation,Date departureTime,Date arriveTime){
	 
		this.departmentLocation=departmentLocation;
	 
		this.arriveLocation=arriveLocation;
	 
		this.departureTime=departureTime;
	 
		this.arriveTime=arriveTime;
	
	}
	

	//生成值对象的无参构造函数
	public CarrierMovement(){
		this.name = CarrierMovement;
	}
	
	



	//该值对象属性所对应的getter、setter方法
	 
	 
	public Location getDepartmentLocation(){
	
		return this.departmentLocation;
		
	}
	
	public void setDepartmentLocation(Location cur){
	
		this.departmentLocation=departmentLocation;
	
	}
		
	 
	 
	public Location getArriveLocation(){
	
		return this.arriveLocation;
		
	}
	
	public void setArriveLocation(Location cur){
	
		this.arriveLocation=arriveLocation;
	
	}
		
	 
	 
	public Date getDepartureTime(){
	
		return this.departureTime;
		
	}
	
	public void setDepartureTime(Date cur){
	
		this.departureTime=departureTime;
	
	}
		
	 
	 
	public Date getArriveTime(){
	
		return this.arriveTime;
		
	}
	
	public void setArriveTime(Date cur){
	
		this.arriveTime=arriveTime;
	
	}
		
}

