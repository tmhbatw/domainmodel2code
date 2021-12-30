package main.domainmodeltag;

public class Repository extends DomainObject{
    public String accessingDomainObject;

    public Repository(String accessingDomainObject){
        this.accessingDomainObject=accessingDomainObject;
    }

    public String toString(){
        return "accessingDomainObject=\""+accessingDomainObject+"\"";
    }

    public static void main(String[] args){
        Repository re=new Repository("123");
        System.out.println(re);
    }
}
