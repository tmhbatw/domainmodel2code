package main.domainmodeltag;

public class DomainEvent extends DomainObject {
    public String identifier;
    public String timestamp;
    public String publisher;
    public String subscriber;

    public DomainEvent(String identifier,String timestamp,String publisher,String subscriber){
        this.identifier=identifier;
        this.timestamp=timestamp;
        this.publisher=publisher;
        this.subscriber=subscriber;
    }

    public String toString(){
        return "identifier=\""+identifier+"\" "+"timestamp=\""+timestamp+"\" "+"publisher=\""+publisher+"\" "+
                "subscriber=\""+subscriber+"\"";
    }

    public static void main(String[] args){
        DomainEvent de=new DomainEvent("1","2","3","4");
        System.out.println(de);
    }


}
