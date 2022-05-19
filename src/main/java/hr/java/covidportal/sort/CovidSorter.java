package main.java.hr.java.covidportal.sort;

import main.java.hr.java.covidportal.model.Zupanija;

import java.util.Comparator;

public class CovidSorter implements Comparator<Zupanija> {

    @Override
    public int compare(Zupanija o1, Zupanija o2) {
        return (o1.getStopa().compareTo(o2.getStopa()));
    }

}
