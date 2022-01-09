package main.gen.model.Cargo;


import main.gen.annotation.AggregatePart;
import main.gen.annotation.DomainAttribute;
import main.gen.annotation.ValueObject;

import java.util.Date;

@AggregatePart(aggregateRootType = Cargo.class)
@ValueObject
public class DeliverySepcification{
	 
	//生成值对象的属性
	@DomainAttribute
	private Date arriveDeadline;
	 
		
		
	//用于生成值对象的含参构造函数
	public DeliverySepcification(Date arriveDeadline){
	 
		this.arriveDeadline=arriveDeadline;
	
	}
	//用于生成值对象的不含参构造函数，如果值对象本身不含参数，那么可以直接删除
//	public DeliverySepcification(){
//
//	}


	//值对象属性的getter以及setter方法
	 
	public Date getArriveDeadline(){
	
		return this.arriveDeadline;
		
	}
	
	public void setArriveDeadline(Date cur){
	
		this.arriveDeadline=arriveDeadline;
	
	}
		
}

