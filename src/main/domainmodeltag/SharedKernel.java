package main.domainmodeltag;

public class SharedKernel extends DomainObject{

    private String oneContext;

    private String theOtherContext;

    public SharedKernel(String oneContext,String anotherContext){
        this.oneContext=oneContext;
        this.theOtherContext=anotherContext;
    }

    public String toString(){
        return "oneContext = "+"\""+this.oneContext+"\" "+
                "theOtherContext = \""+this.theOtherContext+"\"";
    }

}
