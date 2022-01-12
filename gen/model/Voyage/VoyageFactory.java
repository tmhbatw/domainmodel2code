package gen.Voyage;


import gen.annotation.Factory;

@Factory(creatingDomainObject = Voyage.class)

public class VoyageFactory{
		
		
	//生成该工厂对应的含参构造函数
	public VoyageFactory(){
	
	}
 
	//工厂方法，需要开发人员手动实现
	public void createVoyage(){
	
	}
	//该工厂的setter以及getter方法
}
