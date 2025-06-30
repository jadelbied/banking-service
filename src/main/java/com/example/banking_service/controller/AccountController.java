package com.example.banking_service.controller;

import com.example.banking_service.context.TransactionContext;
import com.example.banking_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    public record TransactionRequest(int amount, LocalDate date) {}

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionRequest request) {
        try {
            TransactionContext.setCurrentDate(request.date());
            accountService.deposit(request.amount());
            return ResponseEntity.ok("Dépôt effectué avec succès.");
        } finally {
            TransactionContext.clear();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody TransactionRequest request) {
        try {
            TransactionContext.setCurrentDate(request.date());
            accountService.withdraw(request.amount());
            return ResponseEntity.ok("Retrait effectué avec succès.");
        } finally {
            TransactionContext.clear();
        }
    }

    @GetMapping("/statement")
    public ResponseEntity<String> getStatement() {
        accountService.printStatement();
        return ResponseEntity.ok("Le relevé a été imprimé dans la console du serveur.");
    }
}