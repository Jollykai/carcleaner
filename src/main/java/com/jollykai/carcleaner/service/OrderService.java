package com.jollykai.carcleaner.service;

import com.jollykai.carcleaner.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@Service
public
class OrderService {

    static OrderRepository orderRepository;

    public OrderService(@Autowired OrderRepository orderRepository) {
        OrderService.orderRepository = orderRepository;
    }

    public static Date getDate(Optional<Long> orderTime) {
        if (orderTime.isEmpty()) {
            return getTimeFreeForOrder(roundTime(new Date()));
        }
        return roundTime(new Date(orderTime.get()));
    }

    private static Date getTimeFreeForOrder(Date date) {
        while (orderRepository.existsByDate(date)) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 1);
            date = calendar.getTime();
        }
        return date;
    }

    public static boolean isPastDate(Date date) {
        return date.before(new Date());
    }

    public static Date roundTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        if (calendar.get(Calendar.MINUTE) > 0)
            calendar.add(Calendar.HOUR, 1);

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String getWaitingTime(Date orderDate) {
        Date date = new Date(formatTime(orderDate).getTime() - new Date().getTime());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static Date formatTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getTime();
    }
}
