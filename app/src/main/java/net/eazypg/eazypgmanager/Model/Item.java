package net.eazypg.eazypgmanager.Model;

public class Item
{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Item(String name, int length) {
        this.name = name;
    }
}

