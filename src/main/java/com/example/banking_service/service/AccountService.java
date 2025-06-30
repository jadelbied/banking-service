package com.example.banking_service.service;

public interface AccountService {
    void deposit(int amount);
    void withdraw(int amount);
    void printStatement();
}
