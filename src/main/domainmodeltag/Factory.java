package main.domainmodeltag;

public class Factory extends DomainObject{

    public String creatingDomainObject;

    public Factory(String creatingDomainObject){
        this.creatingDomainObject=creatingDomainObject;
    }

    public String toString(){
        return "creatingDomainObject=\""+creatingDomainObject+"\"";
    }

    public static void main(String[] args){
        Factory f=new Factory("123");
        System.out.println(f);
    }
}
