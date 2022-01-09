package main.metainfo;

public class MyAnnotationParameter {

    public String name;
    public String value;

    public MyAnnotationParameter(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String toString(){
        return this.name+":"+this.value;
    }
}
