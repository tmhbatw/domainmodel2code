//该包对应一个共享内核
@SharedKernel(name = "LocationShare", oneContextName = "Location", theOtherContextName = "Cargo")

	 
//共享内核所包含的下游限界上下文
@DownStreamContext(upStreamContextName = "Location", downStreamContextType = DownStreamContextType.Default) 


package gen.model.LocationShare;

import gen.annotation.SharedKernel;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;
import gen.annotation.DownStreamContextType;
import gen.annotation.UpStreamContextType;