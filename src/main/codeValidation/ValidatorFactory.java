package main.codeValidation;

public class ValidatorFactory {

    public static DomainObjectValidate getValidator (String name){
        switch (name){
            case "Entity":
                return new EntityValidate();
            case "ValueObject":
                return new ValueObjectValidate();
            case "DomainEvent":
                return new DomainEventValidate();
            case "DomainService":
                return new DomainServiceValidate();
            case "Factory":
                return new FactoryValidate();
            case "Repository":
                return new RepositoryValidate();
            default:
                return null;
        }
    }
}
