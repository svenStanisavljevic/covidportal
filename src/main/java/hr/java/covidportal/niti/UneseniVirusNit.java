package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Virus;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.dohvatiBrojVirusa;
import static main.java.sample.BazaPodataka.uneseniVirus;

public class UneseniVirusNit implements Callable {
    @Override
    public String call() throws Exception {

        String zadnjiVirus = null;

        try {
            zadnjiVirus = uneseniVirus();
        } catch (InterruptedException | SQLException | IOException e) {
            e.printStackTrace();
        }

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tempLabel1 = (zadnjiVirus + " " + time.format(formatter));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tempLabel1;

    }
}
