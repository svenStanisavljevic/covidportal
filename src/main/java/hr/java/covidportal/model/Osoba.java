package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Predstavlja osobe i sve podatke o njima
 */

public class Osoba implements Serializable {

    private Long id;
    private String ime;
    private String prezime;
    private Date datumRodenja;
    private Zupanija zupanija;
    private Object zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;
    private Long dob;



    /**
     * Gradi osobu
     */

    public static class Builder {

        private Long id;
        private String ime;
        private String prezime;
        private Date datumRodenja;
        private Zupanija zupanija;
        private Object zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;
        private Long dob;

        public Builder (Long id) {
            this.id = id;
        }

        public Builder withIme(String ime) {
            this.ime = ime;
            return this;
        }

        public Builder withPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public Builder withStarost(Long dob) {
            this.dob = dob;
            return this;
        }

        public Builder withZupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        public Builder withZarazen(Object zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder withKontakti(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = (List<Osoba>) kontaktiraneOsobe;
            return this;
        }

        /**
         * Radi novu instancu osobe bez potrebe da se prosljeduje null koa zadnji argument
         * Dodaje svim osobama u kontaktu s osobom virus ako ga ima
         *
         * @return - nova osoba
         */

        public Osoba build() {
            Osoba osoba = new Osoba();

            osoba.id = this.id;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.dob = this.dob;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;
            if (this.zarazenBolescu instanceof Virus && kontaktiraneOsobe != null) {
                for (int i = 0; i < kontaktiraneOsobe.size(); i++)
                    kontaktiraneOsobe.get(i).setZarazenBolescu((Virus) this.zarazenBolescu);
            }
            return osoba;
        }

    }

    private Osoba() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(Date datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public Zupanija getZupanija() { return zupanija; }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Object getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
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
        return this.ime + this.prezime;
    }



}
