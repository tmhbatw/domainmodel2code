package gen.Cargo;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "trackingId")

public class Cargo{
	//所生成的实体属性
	@DomainAttribute
	private String trackingId;	
	//含参构造函数
	public Cargo(String trackingId){
	 
		this.trackingId=trackingId;
	
	}		
}
