package main.codeValidation;

import main.metainfo.MyClass;

import java.util.Map;

public abstract class DomainObjectValidate {

    public abstract boolean validate(MyClass myclass, Map<String,MyClass> map);
}
