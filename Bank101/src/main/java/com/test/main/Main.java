package com.test.main;


import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Map;

import com.test.MongoToPojo.MongoRecordMapper;
import com.test.collections.Account;
import com.test.collections.Address;
import com.test.collections.Bank;
import com.test.collections.CreditCard;
import com.test.collections.Customer;
import com.test.collections.DebitCard;
import com.test.collections.Loan;
import com.test.collections.Transaction;
import com.test.collections.TransactionStatus;
import com.test.collections.UPI;
import com.test.mongodbconnection.MongoDBConnection;
import com.test.stream.Processing;

public class Main {
    public static void main(String[] args) {
        // Register collection names with record classes
        Map<String, Class<?>> collectionRegistry = Map.of(
            "Transaction", Transaction.class,
            "Customer", Customer.class,
            "Address",Address.class,
            "Bank",Bank.class,
            "CreditCard",CreditCard.class,
            "DebitCard",DebitCard.class,
            "Loan",Loan.class,
            "TransactionStatus",TransactionStatus.class,
            "UPI",UPI.class,
            "Account",Account.class
        );
        
        // Connect to MongoDB
        var database = MongoDBConnection.getDatabase();

        MongoRecordMapper r = new MongoRecordMapper(database, collectionRegistry);
        
        // Load all collections into a Map
        Map<String, List<?>> loadedData = r .loadAllCollections();
        
        // Fetching the data
        List<Transaction> transactions = (List<Transaction>) loadedData.get("Transaction");
        List<Customer> customers = (List<Customer>) loadedData.get("Customer");
        
        // Create the Processing object
        Processing processing = new Processing(r);
        
        String directoryPath = "transactions";
        
        // Process the Transaction data
        String className = Transaction.class.getSimpleName();
        processing.writeObjectsByDate(transactions, className);
        
        // Write customer transactions (assuming customerId 1001 as an example)
        processing.writeCustomerTransactions(1001, "Customer");

        // Now process transactions grouped by customerId
        processing.groupTransactionsByCustomerId(transactions,customers);
    }
}

