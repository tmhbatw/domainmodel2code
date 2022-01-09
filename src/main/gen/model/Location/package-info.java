//这个包用于表示一个限界上下文
@BoundedContext(name = "Location")


	 
//该限界上下文对应的上有限界上下文关系
@UpStreamContext(downStreamContextName = "LocationShare", upStreamContextType = UpStreamContextType.Default) 
	 
//该限界上下文对应的上有限界上下文关系
@UpStreamContext(downStreamContextName = "Voyage", upStreamContextType = UpStreamContextType.Default) 

package main.gen.model.Location;

import main.gen.annotation.BoundedContext;
import main.gen.annotation.UpStreamContext;
import main.gen.annotation.UpStreamContextType;