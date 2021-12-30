//生成共享内核模块的包注解
@SharedKernel(name = "LocationShare", oneContextName = "Location", theOtherContextName = "Cargo")

	 
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "Location", downStreamContextType = DownStreamContextType.Default) 

package gen.model.LocationShare;

import gen.annotation.SharedKernel;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;