package gen.Voyage;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;
import gen.annotation.AggregatePart;

@AggregatePart(aggregateRootType = Voyage.class)
@ValueObject
public class CarrierMovement{
	 
	//生成值对象的属性
	@DomainAttribute
	private Location departmentLocation;
	 
	//生成值对象的属性
	@DomainAttribute
	private Location arriveLocation;
	 
	//生成值对象的属性
	@DomainAttribute
	private Date departureTime;
	 
	//生成值对象的属性
	@DomainAttribute
	private Date arriveTime;
	 
	 
	 
	 
		
		
	//用于生成值对象的含参构造函数
	public CarrierMovement(Location departmentLocation,Location arriveLocation,Date departureTime,Date arriveTime){
	 
		this.departmentLocation=departmentLocation;
	 
		this.arriveLocation=arriveLocation;
	 
		this.departureTime=departureTime;
	 
		this.arriveTime=arriveTime;
	
	}
	//用于生成值对象的不含参构造函数，如果值对象本身不含参数，那么可以直接删除
//	public CarrierMovement(){
//
//	}


	//值对象属性的getter以及setter方法
	 
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

