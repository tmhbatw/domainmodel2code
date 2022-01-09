package main.metainfo;

import java.util.List;

public class MyClass {
    public String name;

    public List<MyField> fields ;
    public List<MyMethod> methods;
    public List<MyAnnotation> annotations;

    public List<String> associations;
    public List<String> dependency;

    public MyClass(String name, List<MyField> fields,List<MyMethod> methods,List<MyAnnotation> annotations,
                   List<String> associations, List<String> dependency){
        this.name=name;

        this.fields=fields;
        this.methods=methods;
        this.annotations=annotations;
        this.associations=associations;
        this.dependency=dependency;
    }

    public String toString(){
        return "this class name is: "+this.name+", this fields contains: "+ this.fields+", this methods contains: "+
                this.methods+", this annotations contains: "+this.annotations+", this dependency contains: "+this.dependency
                +"this associations contains: "+associations;
    }
}
