//生成限界上下文的包注解
@BoundedContext(name = "Voyage")
	 
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "Location", downStreamContextType = DownStreamContextType.Default) 
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.Default) 


package gen.model.Voyage;

import gen.annotation.BoundedContext;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;