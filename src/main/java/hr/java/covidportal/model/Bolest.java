package main.java.hr.java.covidportal.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sadrzi podatke o bolesti koju osoba moze imati
 */

public class Bolest extends ImenovaniEntitet {

    protected List<Simptom> simptomi;

    /**
     * Stvara novu bolest
     *
     * @param naziv - ime bolesti
     * @param simptomi - niz simptoma koje bolest ima
     */

    public Bolest(Long id, String naziv, List<Simptom> simptomi) {
        super(naziv, id);
        for (Simptom simptom : this.simptomi = simptomi) {
            
        }
    }



    public String getNaziv() {
        return naziv;
    }

    public List<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setNaziv(String naziv) {
        super.naziv = naziv;
    }

    public void setSimptomi(List<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    public String getSimptomiString(List<Simptom> simptomi) {
        String result = simptomi.stream()
                .map(n -> n.getNaziv())
                .collect(Collectors.joining(", ", "{", "}"));
        return result;
    }




    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return naziv;
    }
}
