package main.java.hr.java.covidportal.niti;

import main.java.sample.PocetniEkranController;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.dohvatiBrojVirusa;



public class BrojVirusaNit implements Callable<String> {

    public static Integer brojVirusaGlobal = 0;

    @Override
    public String call() throws Exception {

        try {
            brojVirusaGlobal = dohvatiBrojVirusa();
        } catch (InterruptedException | SQLException | IOException e) {
            e.printStackTrace();
        }

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tempLabel1 = (brojVirusaGlobal.toString() + " " + time.format(formatter));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tempLabel1;
    }
}
