package gen.Voyage;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "voyageNumber")

public class Voyage{


	 
	
	//所生成的实体属性
	@DomainAttribute
	private String voyageNumber;
	
	 
		
		
	//含参构造函数
	public Voyage(String voyageNumber){
	 
		this.voyageNumber=voyageNumber;
	
	}
	
	//不含参构造函数
	public Voyage(){
	
	}
	



	//属性的getter与setter方法
	 
	 
	public String getVoyageNumber(){
	
		return this.voyageNumber;
		
	}
	
	public void setVoyageNumber(String cur){
	
		this.voyageNumber=voyageNumber;
	
	}
		
}
