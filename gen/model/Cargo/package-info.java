//生成限界上下文的包注解
@BoundedContext(name = "Cargo")
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "Customer", downStreamContextType = DownStreamContextType.Default) 
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "CustomerShare", downStreamContextType = DownStreamContextType.Default) 
//所生成的下游限界上下文映射关系
@DownStreamContext(upStreamContextName = "Voyage", downStreamContextType = DownStreamContextType.Default) 

package gen.model.Cargo;
import gen.annotation.BoundedContext;
import gen.annotation.Partnership;
import gen.annotation.DownStreamContext;
import gen.annotation.UpStreamContext;