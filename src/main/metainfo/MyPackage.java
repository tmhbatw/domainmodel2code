package main.metainfo;

import java.util.List;

public class MyPackage {
    public String name;

    public List<String> className;
    public List<MyAnnotation> annotations;

    public MyPackage(String name,List<String> className,List<MyAnnotation> annotations){
        this.name=name;
        this.className=className;
        this.annotations=annotations;
    }

    public String toString(){
        return "package name is: "+name+", contain class is: "+className+", annotations is: "+annotations;
    }
}
