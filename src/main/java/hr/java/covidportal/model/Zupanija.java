package main.java.hr.java.covidportal.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Predstavlja zupaniju iz koje osoba moze biti
 */

public class Zupanija extends ImenovaniEntitet {

    private Integer brojStanovnika;
    private Integer brojZarazenih;

    /**
     * Kreira novu instancu zupanije
     *
     * @param naziv - ime zupanije
     * @param brojStanovnika - broj stanovnika te zupanije
     */

    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenih) {
        super(naziv, id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    public String getNaziv() {
        return super.naziv;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }


    public Long getStopa() {
        Long prvi = Long.parseLong(this.brojZarazenih.toString());
        Long drugi = Long.parseLong(this.brojStanovnika.toString());
        return prvi * 100 / drugi;
    }

    public void setNaziv(String naziv) {
        super.naziv = naziv;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Zupanija(String naziv, Long id) {
        super(naziv, id);
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
