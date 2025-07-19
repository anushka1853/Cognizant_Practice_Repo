package com.cognizant.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
class LoanService {

    public static void main(String[] args) {
        SpringApplication.run(LoanService.class, args);
    }

    @GetMapping("/loan")
    public String getLoanDetails() {
        return "Loan service: Loan details here";
    }
}
