package main.Valadation.validation;

import java.io.IOException;

public class Validate {

    public Boolean ValidationModel() throws IOException {
        if(!DomainServiceValidation.domainServiceCheck()) {
            System.out.println("Domain service error");
            return false;
        }
        if(!EntityValidation.entityCheck()) {
            System.out.println("Entity error");
            return false;
        }

        //这一条约束被删掉了
//        if(EntityValidation.entityCheck2()==false)
//            System.out.println("Entity error");
        if(!ValueObjectValidation.valueObjectCheck()) {
            System.out.println("Value Object error");
            return false;
        }
        if(!RepositoryValidation.repositoryCheck()) {
            System.out.println("Repository error");
            return false;
        }

        if(!AggregatedPartValidation.aggregatePartCheck()) {
            System.out.println("Aggregate part error");
            return false;
        }

        if(!DomainEventValidation.domainEventCheck()) {
            System.out.println("Domain Event error");
            return false;
        }


        if(!AggregateValidation.aggregateCheck()) {
            System.out.println(" error:An aggregate’s data access has and can only be managed by a Repository.");
            return false;
        }

        if(!AggregateValidation.aggregateCheck2()) {
            System.out.println(" error:Aggregates can only contain the aggregate root and the aggregate part.");
            return false;
        }

        if(!AggregateValidation.aggregateCheck3()) {
            System.out.println(" error: Aggregates have one and only one aggregate root. ");
            return false;
        }

        if(!AggregateValidation.aggregateCheck5()) {
            System.out.println(" error:Association between any two aggregates is not allowed through object reference. ");
            return false;
        }

        return true;
        //   System.out.println("ok");
    }
}
