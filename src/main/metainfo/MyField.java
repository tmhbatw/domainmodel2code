package main.metainfo;

public class MyField {
    public String type;
    public String name;

    public MyField(String type,String name){
        this.type=type;
        this.name=name;
    }


    public String toString(){
        return this.type+":"+this.name+";";
    }
}
