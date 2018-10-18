package net.eazypg.eazypgmanager.ModelAppliance;

public class ModelItem
{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ModelItem(String name, int length) {
        this.name = name;
    }
}

