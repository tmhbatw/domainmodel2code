package main;

import main.Valadation.validation.Validate;

public class ModelValidation {

    public static Boolean ValidationModel() throws Exception {
        return new Validate().ValidationModel();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(ValidationModel());
    }
}
