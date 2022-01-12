//这个包用于表示一个限界上下文
@BoundedContext(name = "Customer")


	 
//该限界上下文对应的上有限界上下文关系
@UpStreamContext(downStreamContextName = "Cargo", upStreamContextType = UpStreamContextType.Default) 
	 
//该限界上下文对应的上有限界上下文关系
@UpStreamContext(downStreamContextName = "CustomerShare", upStreamContextType = UpStreamContextType.Default) 

package gen.model.Customer;

import gen.annotation.BoundedContext;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;
import gen.annotation.DownStreamContextType;
import gen.annotation.UpStreamContextType;