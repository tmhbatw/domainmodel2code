package main.metainfo;

import java.util.List;

public class MyMethod {

    public String returnType;
    public String methodName;
    public List<MyField> parameters;

    public MyMethod(String returnType,String methodName,List<MyField> parameters){
        this.returnType=returnType;
        this.methodName=methodName;
        this.parameters=parameters;
    }

    public String toString(){
        return "this method name is: "+this.methodName+", this returnType is: "+this.returnType+
                ", this parameters is: "+this.parameters;
    }

}
