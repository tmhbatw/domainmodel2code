//这个包用于表示一个限界上下文
@BoundedContext(name = "Voyage")

//该限界上下文所对应的下游限界上下文关系
@DownStreamContext(upStreamContextName = "Location", downStreamContextType = DownStreamContextType.Default) 

	 
//该限界上下文对应的上有限界上下文关系
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.OpenHostService)

package main.gen.model.Voyage;

import main.gen.annotation.*;