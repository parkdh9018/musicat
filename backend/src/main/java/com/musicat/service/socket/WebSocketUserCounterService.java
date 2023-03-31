package com.musicat.service.socket;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class WebSocketUserCounterService {

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
