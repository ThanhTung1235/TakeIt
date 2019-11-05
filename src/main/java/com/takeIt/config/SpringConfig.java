package com.takeIt.config;

import com.takeIt.endpoint.client.AddressEndpoint;
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
        AddressEndpoint addressEndpoint = new AddressEndpoint();
        addressEndpoint.getCities();

    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(java.time.LocalDate.now());
//        LocalDate date = LocalDate.now();
//        LocalDate date1 = date.plusDays(3);
//        String a = date1.toString();
//        System.out.println(a);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTimeInMillis()));
    }
}