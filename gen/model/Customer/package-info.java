//生成限界上下文的包注解
@BoundedContext(name = "Customer")
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.Default) 
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "CustomerShare", upStreamContextType = UpStreamContextType.Default) 


package gen.model.Customer;

import gen.annotation.BoundedContext;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;