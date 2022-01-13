package main.codeValidation;

import main.metainfo.MyAnnotationParameter;
import main.metainfo.MyField;

import java.util.List;

public class Util {

    public static String getParameterFromList(List<MyAnnotationParameter> list,String name){
        for(MyAnnotationParameter an:list){
            if(an.name.equals(name))
                return an.value;
        }

        return "";
    }

    public static String getFiledFromList(List<MyField> list,String type){
        for(MyField cur:list){
            if(cur.type.equals(type))
                return cur.name;
        }
        return "";
    }
}
