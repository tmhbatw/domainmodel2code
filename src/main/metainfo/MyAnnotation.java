package main.metainfo;

import java.util.List;

public class MyAnnotation {

    public String name;

    public List<MyAnnotationParameter> parameters;

    public MyAnnotation(String name,List<MyAnnotationParameter> parameters){
        this.name = name;

        this.parameters = parameters;
    }

    public String toString(){
        return "annotation name is: "+this.name+", this.parameters is: "+parameters;
    }

}
