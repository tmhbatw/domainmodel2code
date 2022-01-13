package main.metainfo;

public class MyField {
    public String type;
    public String name;
    public String modifier = "private";

    public MyField(String type,String name){
        this.type=type;
        this.name=name;
    }

    public MyField(String type,String name, String modifier){
        this.type = type;
        this.name = name;
        this.modifier = modifier;
    }


    public String toString(){
        return this.type+":"+this.name+";";
    }
}
