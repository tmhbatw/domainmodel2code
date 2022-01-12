package main.codeValidation;

import main.Code2Model;
import main.metainfo.MyClass;
import main.metainfo.MyPackage;

import java.util.Map;

public class CodeValidate {
    public boolean validate(String path) throws ClassNotFoundException {
        Code2Model code =new Code2Model(path);

        Map<String,MyClass> classInfo=code.getClassInfo();
        Map<String, MyPackage> packageInfo=code.getPackageInfo();

        if(!new packageValidation().packageValidate(packageInfo))
            return false;

        for(String className : classInfo.keySet()){

        }


        System.out.println(classInfo);
        System.out.println(packageInfo);
        return true;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(new CodeValidate().validate("main.gen.model"));
        System.out.println(Constraint.CONSTRAINT_01);
    }
}
