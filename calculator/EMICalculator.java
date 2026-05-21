package calculator;

import java.util.Scanner;

/**
 * EMI Calculator - Calculates Equated Monthly Installment
 * EMI = [P * r * (1 + r)^n] / [(1 + r)^n - 1]
 * Where:
 * P = Principal Loan Amount
 * r = Monthly Interest Rate (Annual Rate / 12 / 100)
 * n = Number of Monthly Installments (Years * 12)
 */
public class EMICalculator {

    /**
     * Calculates EMI based on principal, annual rate, and tenure
     * @param principal The loan amount
     * @param annualRate The annual interest rate
     * @param years The loan tenure in years
     * @return The monthly EMI amount
     */
    public static double calculateEMI(double principal, double annualRate, int years) {
        // Convert annual rate to monthly rate
        double monthlyRate = annualRate / 12 / 100;
        
        // Calculate number of months
        int numberOfMonths = years * 12;
        
        // Handle edge case where monthly rate is 0
        if (monthlyRate == 0) {
            return principal / numberOfMonths;
        }
        
        // EMI Formula: [P * r * (1 + r)^n] / [(1 + r)^n - 1]
        double numerator = principal * monthlyRate * Math.pow(1 + monthlyRate, numberOfMonths);
        double denominator = Math.pow(1 + monthlyRate, numberOfMonths) - 1;
        
        return numerator / denominator;
    }
    
    /**
     * Calculates total interest paid over the loan tenure
     * @param emi The monthly EMI amount
     * @param years The loan tenure in years
     * @return The total interest paid
     */
    public static double calculateTotalInterest(double emi, int years) {
        return (emi * years * 12) - (emi * years * 12 / ((1 + (0.05 / 12)) * years * 12)); // Simplified
        // Better approach: totalInterest = (emi * numberOfMonths) - principal
    }
    
    /**
     * Generates an amortization schedule
     * @param principal The loan amount
     * @param monthlyRate The monthly interest rate
     * @param numberOfMonths The number of months
     * @param emi The monthly EMI amount
     */
    public static void printAmortizationSchedule(double principal, double monthlyRate, 
                                                  int numberOfMonths, double emi) {
        double balance = principal;
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println(String.format("%-8s %-15s %-15s %-15s %-15s", 
                          "Month", "EMI", "Principal", "Interest", "Balance"));
        System.out.println("=".repeat(80));
        
        for (int month = 1; month <= numberOfMonths; month++) {
            double interestPayment = balance * monthlyRate;
            double principalPayment = emi - interestPayment;
            balance -= principalPayment;
            
            // Avoid negative balance due to rounding
            if (balance < 0) balance = 0;
            
            System.out.println(String.format("%-8d ₹%-14.2f ₹%-14.2f ₹%-14.2f ₹%-14.2f", 
                              month, emi, principalPayment, interestPayment, balance));
        }
        System.out.println("=".repeat(80));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("\n" + "*".repeat(50));
            System.out.println("Welcome to EMI Calculator");
            System.out.println("*".repeat(50));
            
            // Get user inputs
            System.out.print("\nEnter Principal Loan Amount (in ₹): ");
            double principal = scanner.nextDouble();
            
            if (principal <= 0) {
                System.out.println("Error: Principal amount must be greater than 0");
                return;
            }
            
            System.out.print("Enter Annual Interest Rate (in %): ");
            double annualRate = scanner.nextDouble();
            
            if (annualRate < 0) {
                System.out.println("Error: Interest rate cannot be negative");
                return;
            }
            
            System.out.print("Enter Loan Tenure (in years): ");
            int years = scanner.nextInt();
            
            if (years <= 0) {
                System.out.println("Error: Tenure must be greater than 0");
                return;
            }
            
            // Calculate EMI
            double emi = calculateEMI(principal, annualRate, years);
            double monthlyRate = annualRate / 12 / 100;
            int numberOfMonths = years * 12;
            double totalAmount = emi * numberOfMonths;
            double totalInterest = totalAmount - principal;
            
            // Display results
            System.out.println("\n" + "-".repeat(50));
            System.out.println("EMI CALCULATION RESULTS");
            System.out.println("-".repeat(50));
            System.out.println(String.format("Principal Amount: ₹%.2f", principal));
            System.out.println(String.format("Annual Interest Rate: %.2f%%", annualRate));
            System.out.println(String.format("Loan Tenure: %d years (%d months)", years, numberOfMonths));
            System.out.println(String.format("Monthly EMI: ₹%.2f", emi));
            System.out.println(String.format("Total Amount to Pay: ₹%.2f", totalAmount));
            System.out.println(String.format("Total Interest Paid: ₹%.2f", totalInterest));
            System.out.println("-".repeat(50));
            
            // Ask if user wants to see amortization schedule
            System.out.print("\nDo you want to see the amortization schedule? (yes/no): ");
            String choice = scanner.next();
            
            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")) {
                printAmortizationSchedule(principal, monthlyRate, numberOfMonths, emi);
            }
            
        } catch (Exception e) {
            System.out.println("Error: Invalid input! Please enter valid numbers.");
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
