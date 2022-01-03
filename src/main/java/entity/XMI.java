package main.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import entity.tag.*;
import entity.tag.Module;

import java.io.Serializable;
import java.util.List;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JsonIgnoreProperties(ignoreUnknown = true)

public class XMI implements Serializable {

    private static final long serialVersionUID = -6976783731466786769L;

    @JacksonXmlProperty(isAttribute = true)
    private Integer version;

    @JacksonXmlProperty(isAttribute = true)
    private String xmi;

    @JacksonXmlProperty(isAttribute = true)
    private String xsi;

    @JacksonXmlProperty(isAttribute = true)
    private String ecore;

    @JacksonXmlProperty(isAttribute = true)
    private String model;

    @JacksonXmlProperty(isAttribute = true)
    private String uml;

    @JacksonXmlProperty(isAttribute = true)
    private String schemaLocation;

    @JacksonXmlProperty(localName = "Model")
    private Model umlModel;

    @JacksonXmlProperty(localName = "Aggregate")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Aggregate> aggregates;

    @JacksonXmlProperty(localName = "AggregatePart")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<AggregatePart> aggregateParts;

    @JacksonXmlProperty(localName = "AggregateRoot")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<AggregateRoot> aggregateRoots;

    @JacksonXmlProperty(localName = "BoundedContext")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BoundedContext> boundedContexts;

    @JacksonXmlProperty(localName = "DomainEvent")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DomainEvent> domainEvents;

    @JacksonXmlProperty(localName = "DomainService")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DomainService> domainServices;

    @JacksonXmlProperty(localName = "Entity")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Entity> entities;

    @JacksonXmlProperty(localName = "Factory")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Factory> factories;

    @JacksonXmlProperty(localName = "Module")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Module> modules;

    @JacksonXmlProperty(localName = "Repository")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Repository> repositories;

    @JacksonXmlProperty(localName = "ValueObject")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ValueObject> valueObjects;

    public XMI() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getXmi() {
        return xmi;
    }

    public void setXmi(String xmi) {
        this.xmi = xmi;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public String getEcore() {
        return ecore;
    }

    public void setEcore(String ecore) {
        this.ecore = ecore;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUml() {
        return uml;
    }

    public void setUml(String uml) {
        this.uml = uml;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public Model getUmlModel() {
        return umlModel;
    }

    public void setUmlModel(Model umlModel) {
        this.umlModel = umlModel;
    }

    public List<Aggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<Aggregate> aggregates) {
        if (this.aggregates == null)
            this.aggregates = aggregates;
        else {
            assert this.aggregates != null;
            this.aggregates.addAll(aggregates);
        }
    }

    public List<AggregatePart> getAggregateParts() {
        return aggregateParts;
    }

    public void setAggregateParts(List<AggregatePart> aggregateParts) {
        if (this.aggregateParts == null)
            this.aggregateParts = aggregateParts;
        else {
            assert this.aggregateParts != null;
            this.aggregateParts.addAll(aggregateParts);
        }
    }

    public List<AggregateRoot> getAggregateRoots() {
        return aggregateRoots;
    }

    public void setAggregateRoots(List<AggregateRoot> aggregateRoots) {
        if (this.aggregateRoots == null)
            this.aggregateRoots = aggregateRoots;
        else {
            assert this.aggregateRoots != null;
            this.aggregateRoots.addAll(aggregateRoots);
        }
    }

    public List<BoundedContext> getBoundedContexts() {
        return boundedContexts;
    }

    public void setBoundedContexts(List<BoundedContext> boundedContexts) {
        if (this.boundedContexts == null)
            this.boundedContexts = boundedContexts;
        else {
            assert this.boundedContexts != null;
            this.boundedContexts.addAll(boundedContexts);
        }
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void setDomainEvents(List<DomainEvent> domainEvents) {
        if (this.domainEvents == null)
            this.domainEvents = domainEvents;
        else {
            assert this.domainEvents != null;
            this.domainEvents.addAll(domainEvents);
        }
    }

    public List<DomainService> getDomainServices() {
        return domainServices;
    }

    public void setDomainServices(List<DomainService> domainServices) {
        if (this.domainServices == null)
            this.domainServices = domainServices;
        else {
            assert this.domainServices != null;
            this.domainServices.addAll(domainServices);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        if (this.entities == null)
            this.entities = entities;
        else {
            assert this.entities != null;
            this.entities.addAll(entities);
        }
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        if (this.factories == null)
            this.factories = factories;
        else {
            assert this.factories != null;
            this.factories.addAll(factories);
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        if (this.modules == null)
            this.modules = modules;
        else {
            assert this.modules != null;
            this.modules.addAll(modules);
        }
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        if (this.repositories == null)
            this.repositories = repositories;
        else {
            assert this.repositories != null;
            this.repositories.addAll(repositories);
        }
    }

    public List<ValueObject> getValueObjects() {
        return valueObjects;
    }

    public void setValueObjects(List<ValueObject> valueObjects) {
        if (this.valueObjects == null)
            this.valueObjects = valueObjects;
        else {
            assert this.valueObjects != null;
            this.valueObjects.addAll(valueObjects);
        }
    }

    @Override
    public String toString() {
        return "XMI{" +
                "version=" + version +
                ", xmi='" + xmi + '\'' +
                ", xsi='" + xsi + '\'' +
                ", ecore='" + ecore + '\'' +
                ", model='" + model + '\'' +
                ", uml='" + uml + '\'' +
                ", schemaLocation='" + schemaLocation + '\'' +
                ", umlModel=" + umlModel +
                ", aggregates=" + aggregates +
                ", aggregateParts=" + aggregateParts +
                ", aggregateRoots=" + aggregateRoots +
                ", boundedContexts=" + boundedContexts +
                ", domainEvents=" + domainEvents +
                ", domainServices=" + domainServices +
                ", entities=" + entities +
                ", factories=" + factories +
                ", modules=" + modules +
                ", repositories=" + repositories +
                ", valueObjects=" + valueObjects +
                '}';
    }
}
