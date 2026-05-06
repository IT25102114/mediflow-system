package com.mediflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MediFlow Online Medicine Store
 * SE1020 - Object Oriented Programming Project
 * 
 * Main application entry point using Spring Boot.
 * This application demonstrates OOP concepts including:
 * - Encapsulation (private fields with getters/setters)
 * - Inheritance (User -> Customer/Admin, Product -> PrescriptionMedicine/OTCMedicine)
 * - Polymorphism (different behavior via method overriding)
 * - Abstraction (abstract base classes)
 * 
 * Data is persisted using file read/write operations (.txt files).
 */
@SpringBootApplication
public class MediFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediFlowApplication.class, args);

    }
}
