package main.domainmodeltag;

public class AggregatePart extends DomainObject{

    public String aggregateRootType;

    public AggregatePart(String aggregateRootType){
        this.aggregateRootType=aggregateRootType;
    }

    public String toString(){
        return "(aggregateRootType = "+aggregateRootType+".class)";
    }

    public static void main(String[] args){
        AggregatePart a=new AggregatePart("123");
        System.out.println(a);
    }
}
