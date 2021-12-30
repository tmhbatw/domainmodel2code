//生成共享内核模块的包注解
@SharedKernel(name = "CustomerShare", oneContextName = "Cargo", theOtherContextName = "Customer")

	 
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "Customer", downStreamContextType = DownStreamContextType.Default) 
	 
//所生成的上游限界上下文映射关系
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.Default) 

package gen.model.CustomerShare;

import gen.annotation.SharedKernel;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;