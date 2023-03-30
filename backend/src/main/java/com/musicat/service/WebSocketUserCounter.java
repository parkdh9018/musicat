package com.musicat.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class WebSocketUserCounter {

    private AtomicInteger userCount = new AtomicInteger(0);

    public void increment() {
        userCount.incrementAndGet();
    }

    public void decrement() {
        userCount.decrementAndGet();
    }

    public int getCount() {
        return userCount.get();
    }


}
