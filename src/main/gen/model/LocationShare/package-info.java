//该包对应一个共享内核
@SharedKernel(name = "LocationShare", oneContextName = "Location", theOtherContextName = "Cargo")

	 
//共享内核所包含的下游限界上下文
@DownStreamContext(upStreamContextName = "Location", downStreamContextType = DownStreamContextType.Default) 


package main.gen.model.LocationShare;

import main.gen.annotation.DownStreamContext;
import main.gen.annotation.DownStreamContextType;
import main.gen.annotation.SharedKernel;
