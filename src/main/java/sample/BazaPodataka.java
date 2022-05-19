package main.java.sample;
import main.java.hr.java.covidportal.model.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;
import java.util.Properties;

import static main.java.sample.Main.logger;

public class BazaPodataka {

    private static final String configFile="src\\main\\resources\\config.properties";

    public static Boolean aktivnaVezaSaBazom = false;

    /*--------------------------------------------------------------------------------------
        Probajte doubleclickati simptome iz liste :D
    ----------------------------------------------------------------------------------------- */


    public static synchronized Connection poveziNaBazu() throws SQLException, IOException, InterruptedException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(configFile));
        String dbUrl = svojstva.getProperty("dataBaseURL");
        String dbUser = svojstva.getProperty("username");
        String dbPass = svojstva.getProperty("password");
        Connection veza = DriverManager.
                getConnection(dbUrl, dbUser, dbPass);
        //logger.info("Spajanje na bazu podataka");

        return veza;

    }

    public static void odspajanjeOdBaze(Connection veza) throws SQLException {
        veza.close();
        //logger.info("Odspajanje od baze podataka");
    }

    public static Integer dohvatiBrojVirusa() throws InterruptedException, SQLException, IOException {
        Integer brojVirusa = null;
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS broj FROM BOLEST WHERE VIRUS = 1;");
        while (resultSet.next()) {
            brojVirusa = resultSet.getInt("BROJ");
        }
        return brojVirusa;
    }

    public static String uneseniVirus() throws SQLException, IOException, InterruptedException {
        String nazivVirusa = null;
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOLEST WHERE VIRUS = 1 ORDER BY ID DESC LIMIT 1;");
        while (resultSet.next()) {
            nazivVirusa = resultSet.getString("NAZIV");
        }
        return nazivVirusa;
    }

    public static List<Zupanija> dohvatiSveZupanije() throws SQLException, IOException, InterruptedException {
        List<Zupanija> listaZupanija = new ArrayList<>();
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ZUPANIJA");
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            Integer brojStanovnika = resultSet.getInt("broj_stanovnika");
            Integer brojZarazenih = resultSet.getInt("broj_zarazenih_stanovnika");
            Zupanija novaZupanija = new Zupanija(id, naziv, brojStanovnika, brojZarazenih);
            listaZupanija.add(novaZupanija);
        }
        odspajanjeOdBaze(veza);
        return listaZupanija;
    }

    public static void spremiNovuZupaniju(Zupanija zupanija) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) VALUES(?, ?, ?)");
        statement.setString(1, zupanija.getNaziv());
        statement.setString(2, zupanija.getBrojStanovnika().toString());
        statement.setString(3, zupanija.getBrojZarazenih().toString());
        statement.executeUpdate();
        odspajanjeOdBaze(veza);
    }

    public static Zupanija dohvatiZupaniju(Long idSearch) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID = ?");
        statement.setLong(1, idSearch);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            Integer brojStanovnika = resultSet.getInt("broj_stanovnika");
            Integer brojZarazenih = resultSet.getInt("broj_zarazenih_stanovnika");
            Zupanija novaZupanija = new Zupanija(id, naziv, brojStanovnika, brojZarazenih);
            odspajanjeOdBaze(veza);
            return novaZupanija;
        }
        return null;
    }

    public static List<Simptom> dohvatiSveSimptome() throws SQLException, IOException, InterruptedException {
        List<Simptom> listaSimptoma = new ArrayList<>();
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM SIMPTOM");
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            String vrijednost = resultSet.getString("vrijednost");
            Simptom noviSimptom = new Simptom(id, naziv, vrijednost);
            listaSimptoma.add(noviSimptom);
        }
        odspajanjeOdBaze(veza);
        return listaSimptoma;
    }

    public static void spremiNoviSimptom(Simptom simptom) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?, ?);");
        statement.setString(1, simptom.getNaziv());
        statement.setString(2, simptom.getVrijednost());
        statement.executeUpdate();
        odspajanjeOdBaze(veza);
    }

    public static Simptom dohvatiSimptom(Long idSearch) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("SELECT * FROM SIMPTOM WHERE ID = ?");
        statement.setLong(1, idSearch);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            String vrijednost = resultSet.getString("vrijednost");
            Simptom noviSimptom = new Simptom(id, naziv, vrijednost);
            odspajanjeOdBaze(veza);
            return noviSimptom;
        }
        return null;
    }

    public static List<Bolest> dohvatiSveBolesti() throws SQLException, IOException, InterruptedException {
        List<Bolest> listaBolesti = new ArrayList<>();
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOLEST WHERE VIRUS = 'false'");
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            List<Simptom> nizSimptoma = new ArrayList<>();
            List<Simptom> nizSvihSimptoma = dohvatiSveSimptome();
            ResultSet resultSet2 = veza.createStatement().executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while (resultSet2.next()) {
                if (id.equals(resultSet2.getLong("bolest_id"))) {
                    for (Simptom s : nizSvihSimptoma) {
                        if (s.getId().equals(resultSet2.getLong("simptom_id"))) {
                            nizSimptoma.add(s);
                        }
                    }
                }
            }
            Bolest novaBolest = new Bolest(id, naziv, nizSimptoma);
            listaBolesti.add(novaBolest);
        }
        odspajanjeOdBaze(veza);
        return listaBolesti;
    }

    public static Bolest dohvatiBolest(Long idSearch) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?");
        statement.setLong(1, idSearch);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            List<Simptom> nizSimptoma = new ArrayList<>();
            List<Simptom> nizSvihSimptoma = dohvatiSveSimptome();
            ResultSet resultSet2 = veza.createStatement().executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while (resultSet2.next()) {
                if (id.equals(resultSet2.getLong("bolest_id"))) {
                    for (Simptom s : nizSvihSimptoma) {
                        if (s.getId().equals(resultSet2.getLong("simptom_id"))) {
                            nizSimptoma.add(s);
                        }
                    }
                }
            }
            Bolest novaBolest = new Bolest(id, naziv, nizSimptoma);
            odspajanjeOdBaze(veza);
            return novaBolest;
        }
        return null;
    }

    public static List<Virus> dohvatiSveViruse() throws SQLException, IOException, InterruptedException {
        List<Virus> listaVirusa = new ArrayList<>();
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOLEST WHERE VIRUS = 'true'");
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            List<Simptom> nizSimptoma = new ArrayList<>();
            List<Simptom> nizSvihSimptoma = dohvatiSveSimptome();
            ResultSet resultSet2 = veza.createStatement().executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while (resultSet2.next()) {
                if (id.equals(resultSet2.getLong("bolest_id"))) {
                    for (Simptom s : nizSvihSimptoma) {
                        if (s.getId().equals(resultSet2.getLong("simptom_id"))) {
                            nizSimptoma.add(s);
                        }
                    }
                }
            }
            Virus noviVirus = new Virus(id, naziv, nizSimptoma);
            listaVirusa.add(noviVirus);
        }
        odspajanjeOdBaze(veza);
        return listaVirusa;
    }

    public static void spremiNovuBolest(Bolest bolest, Boolean statusVirusa) throws SQLException, IOException, InterruptedException {
        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, bolest.getNaziv());
        statement.setBoolean(2, statusVirusa);
        statement.executeUpdate();
        List<Bolest> bolesti = dohvatiSveBolesti();
        List<Virus> virusi = dohvatiSveViruse();
        try (ResultSet resultSet = statement.getGeneratedKeys())
        {
            while (resultSet.next())
            {
                Long id = resultSet.getLong( 1 );
                for (Bolest b : bolesti) {
                    if (b.getNaziv().equals(bolest.getNaziv())) {
                        bolest.setId(b.getId());
                    }
                }
                for (Virus v : virusi) {
                    if (v.getNaziv().equals(bolest.getNaziv())) {
                        bolest.setId(v.getId());
                    }
                }
            }
        }

        for (Simptom s : bolest.getSimptomi()) {
            PreparedStatement statement2 = veza.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (?, ?);");
            statement2.setLong(1, bolest.getId());
            statement2.setLong(2, s.getId());
            statement2.executeUpdate();
        }
        odspajanjeOdBaze(veza);
    }

    public static List<Osoba> dohvatiSveOsobe() throws SQLException, IOException, InterruptedException {

        List<Osoba> osobe = new ArrayList<>();
        Connection veza = poveziNaBazu();
        Statement statement = veza.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM OSOBA");
        String prezime = null;
        Date datumRodenja = null;
        String ime = null;
        Long id = null;

        while (resultSet.next()) {
            id = resultSet.getLong("id");
            ime = resultSet.getString("ime");
            prezime = resultSet.getString("prezime");
            datumRodenja = resultSet.getDate("datum_rodjenja");
            Long dob;
            Instant instant = Instant.ofEpochMilli(datumRodenja.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(
                    instant, ZoneId.systemDefault()).toLocalDate();
            dob = ChronoUnit.YEARS.between(localDate, LocalDate.now());
            Long zupanijaId = resultSet.getLong("zupanija_id");
            Long bolestId = resultSet.getLong("bolest_id");

            Zupanija zupanija = null;
            Bolest bolest = null;
            List<Osoba> kontakti = new ArrayList<>();
            List<Bolest> bolesti = dohvatiSveBolesti();
            bolesti.addAll(dohvatiSveViruse());

            for (Zupanija z : dohvatiSveZupanije()) {
                if (z.getId().equals(zupanijaId)) {
                    zupanija = z;
                }
            }
            for (Bolest b : bolesti) {
                if (b.getId().equals(bolestId)) {
                    bolest = b;
                }
            }

            ResultSet resultSet2 = veza.createStatement().executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE");
            while (resultSet2.next()) {
                if (id.equals(resultSet2.getLong("osoba_id"))) {
                    for (Osoba o : osobe) {
                        if (o.getId().equals(resultSet2.getLong("kontaktirana_osoba_id"))) {
                            kontakti.add(o);
                        }
                    }
                }
            }

            osobe.add(new Osoba.Builder(id).withIme(ime).withPrezime(prezime).withStarost(dob)
                    .withZupanija(zupanija).withZarazen(bolest).withKontakti(kontakti).build());
        }
        return osobe;
    }

    public static void spremiNovuOsobu(Osoba osoba, Long zupanijaId, Long bolestId, LocalDate datum, List<Osoba> kontakti) throws SQLException, IOException, InterruptedException {

        Connection veza = poveziNaBazu();
        PreparedStatement statement = veza.prepareStatement("INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, osoba.getIme());
        statement.setString(2, osoba.getPrezime());
        Date datumRodenja = Date.valueOf(datum);
        statement.setDate(3, datumRodenja);
        statement.setLong(4, zupanijaId);
        statement.setLong(5, bolestId);
        statement.executeUpdate();
        List <Osoba> osobe = dohvatiSveOsobe();

        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                for (Osoba o : osobe) {
                    if (o.getIme().equals(osoba.getIme()) && o.getPrezime().equals(osoba.getPrezime())) {
                        osoba.setId(o.getId());
                    }
                }
            }
        }
        for (Osoba o : kontakti) {
            PreparedStatement statement2 = veza.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE(OSOBA_ID, KONTAKTIRANA_OSOBA_ID) VALUES (?, ?)");
            statement2.setLong(1, osoba.getId());
            statement2.setLong(2, o.getId());
            statement2.executeUpdate();
        }
        odspajanjeOdBaze(veza);
    }

}
