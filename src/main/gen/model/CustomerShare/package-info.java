//该包对应一个共享内核
@SharedKernel(name = "CustomerShare", oneContextName = "Cargo", theOtherContextName = "Customer")

	 
//共享内核所包含的下游限界上下文
@DownStreamContext(upStreamContextName = "Customer", downStreamContextType = DownStreamContextType.Default) 

	 
//共享内核所包含的上有限界上下文
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.Default) 

package main.gen.model.CustomerShare;

import main.gen.annotation.*;