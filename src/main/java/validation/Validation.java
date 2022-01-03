package main.java.validation;

import entity.tag.DomainEvent;

import java.io.IOException;

public class Validation {
    public static void main(String[] args) throws IOException {
        boolean flag=true;
       if(DomainServiceValidation.domainServiceCheck()==false)
           System.out.println("领域服务错误");
        if(EntityValidation.entityCheck()==false)
            System.out.println("实体错误");
       if(ValueObjectValidation.valueObjectCheck()==false)
           System.out.println("值对象错误");
       if(RepositoryValidation.repositoryCheck()==false)
           System.out.println("资源库错误");
        if(AggregatedPartValidation.aggregatePartCheck()==false)
            System.out.println("聚合部分错误");
        if(DomainEventValidation.domainEventCheck() ==false)
            System.out.println("领域事件错误");


        if(AggregateValidation.aggregateCheck() ==false)
            System.out.println(" error:An aggregate’s data access has and can only be managed by a Repository.");
        if(AggregateValidation.aggregateCheck2() ==false)
            System.out.println(" error:Aggregates can only contain the aggregate root and the aggregate part.");
        if(AggregateValidation.aggregateCheck3() ==false)
            System.out.println(" error: Aggregates have one and only one aggregate root. ");
        if(AggregateValidation.aggregateCheck4() ==false)
            System.out.println(" error:Association between any two aggregates is not allowed through object reference. ");




     //   System.out.println("ok");
    }
}
