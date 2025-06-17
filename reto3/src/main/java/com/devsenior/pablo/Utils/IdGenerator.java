package com.devsenior.pablo.Utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final ConcurrentHashMap<String, AtomicInteger> idCounters = new ConcurrentHashMap<>();
    
    static {
        // Initialize counters for each model type
        idCounters.put("USER", new AtomicInteger(0));
        idCounters.put("BOOK", new AtomicInteger(0));
        idCounters.put("LOAN", new AtomicInteger(0));
    }
    
    public static int generateUserId() {
        return idCounters.get("USER").incrementAndGet();
    }
    
    public static int generateBookId() {
        return idCounters.get("BOOK").incrementAndGet();
    }
    
    public static int generateLoanId() {
        return idCounters.get("LOAN").incrementAndGet();
    }
    
    // Method to reset counters (useful for testing)
    public static void resetCounters() {
        idCounters.get("USER").set(0);
        idCounters.get("BOOK").set(0);
        idCounters.get("LOAN").set(0);
    }
}
