//生成限界上下文的包注解
@BoundedContext(name = "Location")
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "LocationShare", upStreamContextType = UpStreamContextType.Default) 
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "Voyage", upStreamContextType = UpStreamContextType.Default) 


package gen.model.Location;

import gen.annotation.BoundedContext;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;