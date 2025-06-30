package com.example.banking_service.service;

import com.example.banking_service.context.TransactionContext;
import com.example.banking_service.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private int balance = 0;
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public synchronized void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Le montant doit être positif.");
        balance += amount;
        LocalDate transactionDate = TransactionContext.getCurrentDate();
        transactions.add(new Transaction(transactionDate, amount, balance));
    }

    @Override
    public synchronized void withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Le montant doit être positif.");
        if (balance < amount) throw new IllegalStateException("Solde insuffisant.");
        balance -= amount;
        LocalDate transactionDate = TransactionContext.getCurrentDate();
        transactions.add(new Transaction(transactionDate, -amount, balance));
    }

    @Override
    public void printStatement() {
        List<Transaction> statementTransactions = new ArrayList<>(this.transactions);
        Collections.reverse(statementTransactions);

        StringBuilder statement = new StringBuilder("Date       || Amount || Balance\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Transaction tx : statementTransactions) {
            statement.append(String.format("%-10s || %-6d || %d\n",
                    tx.date().format(formatter),
                    tx.amount(),
                    tx.balance()));
        }
        System.out.println(statement.toString());
    }
}