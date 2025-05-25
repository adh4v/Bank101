package com.test.stream;
 
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.test.MongoToPojo.MongoRecordMapper;
import com.test.collections.Account;
import com.test.collections.Address;
import com.test.collections.Bank;
import com.test.collections.CreditCard;
import com.test.collections.Customer;
import com.test.collections.DebitCard;
import com.test.collections.Loan;
import com.test.collections.TransactionStatus;
import com.test.collections.Transaction;
import com.test.collections.UPI;
import com.test.writeintxt.WriteInTxt;
 
public class Processing {
 
    private final MongoRecordMapper r;
    private final List<Customer> customers;
    private final List<Transaction> transactions;
    private final List<Address> address;
    private final List<Bank> bank;
    private final List<CreditCard> creditCard;
    private final List<DebitCard> debitCard;
    private final List<Loan> loan;
    private final List<TransactionStatus> status;
    private final List<UPI> upi;
    private final List<Account> account;
 
    public Processing(MongoRecordMapper r) {
        this.r = r;
 
        Map<String, List<?>> data = r.loadAllCollections();
        this.customers = (List<Customer>) data.get("Customer");
        this.transactions = (List<Transaction>) data.get("Transaction");
		this.address = (List<Address>) data.get("Address");
		this.bank = (List<Bank>) data.get("Bank");
		this.creditCard = (List<CreditCard>) data.get("CreditCard");
		this.debitCard = (List<DebitCard>) data.get("DebitCard");
		this.loan = (List<Loan>) data.get("Loan");
		this.status = (List<TransactionStatus>) data.get("Status");
		this.upi = (List<UPI>) data.get("UPI");
		this.account = (List<Account>) data.get("Account");
    }
 
    public static <T> void writeObjectsByDate(List<T> objects, String directoryPath) {
        // Group transactions by date
        Map<Object, List<T>> groupByDate = objects.stream()
                .collect(Collectors.groupingBy(tx -> ((Transaction) tx).timestamp(), TreeMap::new, Collectors.toList()));
 
        // For each grouped date, create a filename and write the transactions
        groupByDate.forEach((date, transactions) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fileName = ((LocalDateTime) date).format(formatter);
            try {
                
                WriteInTxt.writeToFile(transactions, fileName, directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
 
    // This method writes customer transactions grouped by date
    public void writeCustomerTransactions(int customerId, String directoryPath) {
        // Find the customer by ID
        Customer customer = customers.stream().filter(c -> c.customerId() == customerId).findFirst().orElse(null);
 
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return;
        }
 
        // Filter transactions for the given customer (both as Payer and Payee)
        List<Transaction> customerTransactions = transactions.stream()
                .filter(tx -> tx.Payer() == customer.customerId() || tx.Payee() == customer.customerId())
                .collect(Collectors.toList());
 
        if (customerTransactions.isEmpty()) {
            System.out.println("No transactions found for customer: " + customerId);
            return;
        }
 
        // Group transactions by date (only LocalDate)
        List<Transaction> grpCustomerTransaction = transactions.stream()
                .filter(tx -> tx.Payer() == customer.customerId() || tx.Payee() == customer.customerId())
                .collect(Collectors.toList());
 
        // For each grouped date, create a filename and write the transactions
        
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fileName = customerId + "_" + customer.customerName();
            try {
                
                WriteInTxt.writeToFile(grpCustomerTransaction, fileName, "customers");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
    public static void groupTransactionsByCustomerId(List<Transaction> transactions, List<Customer> customer) {
        Map<Integer, List<Transaction>> groupByCustomer = transactions.stream()
                .flatMap(tx -> Stream.of(tx.Payer(), tx.Payee()))
                .distinct()
                .collect(Collectors.toMap(
                        customerId -> customerId,
                        customerId -> transactions.stream()
                                .filter(tx -> tx.Payer() == customerId || tx.Payee() == customerId)
                                .collect(Collectors.toList())
                ));
 
        groupByCustomer.forEach((custId, tx) -> {
            String fileName = custId + "";
            try {
                
                WriteInTxt.writeToFile(tx, fileName, "grouped_by_customer");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}