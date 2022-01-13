package main;

import main.Valadation.validation.*;
import main.codeValidation.CodeValidate;

public class CodeValidation {
    public static void main(String[] args) throws Exception {
        System.out.println(ValidateCode("main.gen.model"));
    }

    public static Boolean ValidateCode(String path) throws Exception {
        return new CodeValidate().validate(path);
    }



}
