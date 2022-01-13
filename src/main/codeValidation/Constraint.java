package main.codeValidation;

public enum Constraint {

    //Entity
    CONSTRAINT_01(1,"实体有且只有一个唯一标识"),
    CONSTRAINT_02(2,"实体的标识应该被设计成一个或几个它的属性的组合"),

    //ValueObject
    CONSTRAINT_03(3,"一个值对象不含有唯一标识"),
    CONSTRAINT_04(4,"值对象是不可变的"),

    //DomainEvent
    CONSTRAINT_05(5,"领域事件有且只有一个唯一标识"),
    CONSTRAINT_06(6,"领域事件的标识应该被设计成一个或几个它的属性的组合"),
    CONSTRAINT_07(7,"领域事件应该包含一个时间戳"),
    CONSTRAINT_08(8,"领域事件应该包含领域事件的生产者和接受者"),
    CONSTRAINT_09(9,"领域事件是不可变的"),

    //DomainService
    CONSTRAINT_10(10,"领域服务是无状态的"),
    CONSTRAINT_11(11,"领域服务不能同时被设计为其他领域模式"),

    //Repository
    CONSTRAINT_12(12,"资源库需要指定它所获取的对象"),
    CONSTRAINT_13(13,"资源库不包含属性"),
    CONSTRAINT_14(14,"资源库不能被同时设计为其他领域模式"),

    //Factory
    CONSTRAINT_15(15,"工厂祖耀指定它所创建的对象"),
    CONSTRAINT_16(16,"工厂不能被同时设计为其他领域模式"),

    //AggregateRoot
    CONSTRAINT_17(17,"聚合根需要被设计为实体"),

    //AggregatePart
    CONSTRAINT_18(18,"聚合部分只能被设计为实体或值对象"),
    CONSTRAINT_19(19,"聚合部分不能被其他外部的对象所引用"),
    CONSTRAINT_20(20,"聚合部分的引用不应该被外部对象获得"),

    //Aggregate
    CONSTRAINT_21(21,"一个聚合应该有且只有一个聚合根"),
    CONSTRAINT_22(22,"除了聚合根，聚合中的其他部分应该是一个聚合部分"),
    CONSTRAINT_23(23,"聚合的创建应该由工厂实现"),
    CONSTRAINT_24(24,"聚合的获取应该由资源库实现"),
    CONSTRAINT_25(25,"聚合中的对象不应该成为外部限界上下文的属性");



    public int getCode(){
        return code;
    }

    public String getRule(){
        return rule;
    }

    private int code;

    private String rule;

    Constraint(int code,String rule){
        this.code=code;
        this.rule = rule;
    }

    public String toString(){
        return "违反了第"+this.code+"条约束："+this.rule;
    }

}
