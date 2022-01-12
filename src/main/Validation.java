package main;

import main.Valadation.validation.*;
import main.codeValidation.CodeValidate;

public class Validation {
    public static void main(String[] args) throws Exception {
        System.out.println(Validation.ValidationModel());
    }

    public static Boolean ValidateCode(String path) throws Exception {
        return new CodeValidate().validate(path);
    }

    public static Boolean ValidationModel() throws Exception {
        return new Validate().ValidationModel();
    }




}
