package com.example.banking_service.model;

import java.time.LocalDate;

public record Transaction(
        LocalDate date,
        int amount,
        int balance
) {}
