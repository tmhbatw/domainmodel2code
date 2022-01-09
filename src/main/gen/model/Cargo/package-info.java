//这个包用于表示一个限界上下文
@BoundedContext(name = "Cargo")

//该限界上下文所对应的下游限界上下文关系
@DownStreamContext(upStreamContextName = "Customer", downStreamContextType = DownStreamContextType.Default) 
//该限界上下文所对应的下游限界上下文关系
@DownStreamContext(upStreamContextName = "CustomerShare", downStreamContextType = DownStreamContextType.Default) 
//该限界上下文所对应的下游限界上下文关系
@DownStreamContext(upStreamContextName = "Voyage", downStreamContextType = DownStreamContextType.Conformist)

@Partnership(anotherPartnershipContext = "Customer")


package main.gen.model.Cargo;

import main.gen.annotation.*;
