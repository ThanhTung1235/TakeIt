package com.takeIt.config;

import com.google.gson.Gson;
import com.takeIt.controller.AddressController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Calendar cal = Calendar.getInstance();
//        System.out.println(cal.getTime());
//        cal.add(Calendar.DATE, 3);
//        System.out.println(cal.getTime().getTime());
@Component
@EnableScheduling
public class SpringConfig {
    private static final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0 0 * * ?")
    public void testScheduling() {
        System.out.println("Current time = :" + date.format(new Date()));
        AddressController addressController = new AddressController();
        addressController.getCities();

    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTimeInMillis());

        cal.add(Calendar.DATE, 3);
        System.out.println(sdf.format(cal.getTime()));

        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        Date d = new Date(1572797062);

        if (d.compareTo(date) == 0) {
            System.out.println("is true");
        } else {
            System.out.println("is not fun fucking idiot");
        }

    }
}
