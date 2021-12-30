package gen.Cargo;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregatePart(aggregateRootType = Cargo.class)
@ValueObject

public class DeliverySepcification{

	 
	
	//值对象所包含的属性
	@DomainAttribute
	private Date arriveDeadline;
	
	
	 
		
		
	//生成值对象的含参构造函数
	public DeliverySepcification(Date arriveDeadline){
	 
		this.arriveDeadline=arriveDeadline;
	
	}
	

	//生成值对象的无参构造函数
	public DeliverySepcification(){
		this.name = DeliverySepcification;
	}
	
	



	//该值对象属性所对应的getter、setter方法
	 
	 
	public Date getArriveDeadline(){
	
		return this.arriveDeadline;
		
	}
	
	public void setArriveDeadline(Date cur){
	
		this.arriveDeadline=arriveDeadline;
	
	}
		
}

