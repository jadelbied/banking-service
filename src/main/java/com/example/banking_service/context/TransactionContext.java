package com.example.banking_service.context;

import java.time.LocalDate;

public class TransactionContext {

    private static final ThreadLocal<LocalDate> currentDate = new ThreadLocal<>();

    public static void setCurrentDate(LocalDate date) {
        currentDate.set(date);
    }

    public static LocalDate getCurrentDate() {
        return currentDate.get();
    }

    public static void clear() {
        currentDate.remove();
    }
}