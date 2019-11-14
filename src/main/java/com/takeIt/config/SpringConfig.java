package com.takeIt.config;

import com.google.gson.Gson;
import com.takeIt.endpoint.client.AddressEndpoint;
import com.takeIt.endpoint.client.TransactionEndpoint;
import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import com.takeIt.entity.City;
import org.apache.catalina.LifecycleState;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Calendar cal = Calendar.getInstance();
//        System.out.println(cal.getTime());
//        cal.add(Calendar.DATE, 3);
//        System.out.println(cal.getTime().getTime());
@Component
@EnableScheduling
public class SpringConfig {
    //   Every day at midnight - 12am
    @Scheduled(cron = "0 0 0 * * ?")
    public void testScheduling() {
        TransactionEndpoint transactionEndpoint = new TransactionEndpoint();
        transactionEndpoint.checkExpiration();

    }

}