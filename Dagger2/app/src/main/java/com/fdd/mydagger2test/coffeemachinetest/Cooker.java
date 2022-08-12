package com.fdd.mydagger2test.coffeemachinetest;

public class Cooker {

    private String name;

    private String coffeeKind;

    public Cooker(String name, String coffeeKind) {
        this.name = name;
        this.coffeeKind = coffeeKind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoffeeKind() {
        return coffeeKind;
    }

    public void setCoffeeKind(String coffeeKind) {
        this.coffeeKind = coffeeKind;
    }

    public String make() {
        return name + " make " + coffeeKind;
    }
}
