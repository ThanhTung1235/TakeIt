package com.takeIt.config;

import com.google.gson.Gson;
import com.takeIt.endpoint.client.AddressEndpoint;
import com.takeIt.endpoint.client.TransactionEndpoint;
import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import com.takeIt.entity.City;
import org.apache.catalina.LifecycleState;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    public static void main(String[] args) throws ParseException {
        Category category = new Category();
        category.setId(1);
        category.setName("some name");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        City city = new City();
        city.setId(1);
        city.setName("Ha Noi");
        List<City> cities = new ArrayList<>();
        cities.add(city);
        System.out.println(new Gson().toJson(ListUtils.union(categories, cities)));;


    }
}