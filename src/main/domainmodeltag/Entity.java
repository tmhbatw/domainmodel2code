package main.domainmodeltag;

public class Entity extends DomainObject {
    public String identifier;

    public Entity(String identifier){
        this.identifier=identifier;
    }

    @Override
    public String toString() {
        return "identifier=\"" + identifier + "\"";
    }

    public static void main(String[] args){
        Entity e=new Entity("123");
        System.out.println(e.toString());
    }
}

