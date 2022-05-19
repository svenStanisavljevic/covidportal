package main.java.hr.java.covidportal.enums;

public enum UcestalostSimptoma {

    Produktivni("Produktivni"),
    Intenzivno("Intenzivno"),
    Visoka("Visoka"),
    Jaka("Jaka");

    private String ucestalost;

    private UcestalostSimptoma (String ucestalost) {
        this.ucestalost = ucestalost;
    }

    public String getUcestalost() {
        return ucestalost;
    }

}
