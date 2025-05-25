package com.test.collections;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.test.annotation.*;

public record Transaction
		(
		@FieldLength(length = 20) LocalDateTime timestamp,
		@FieldLength(length = 10) Double TransactionWithdrawalAmount,
		@FieldLength(length = 10) Double TransactionDepositAmount,
		@FieldLength(length = 10) int Payer,
		@FieldLength(length = 10) int Payee,
		@FieldLength(length = 6) String TransactionType,
		@FieldLength(length = 20) String TransactionReferenceNumber,
		@FieldLength(length = 10) Double TransactionClosingAmount,
		@FieldLength(length = 10) int TransactionStatusId,
		@FieldLength(length=10) String TransactionId
		) 
{
	public Transaction(

			String timestamp,
			Double TransactionWithdrawalAmount,
			Double TransactionDepositAmount,
			int Payer,
			int Payee,
			String TransactionType,
			String TransactionReferenceNumber,
			Double TransactionClosingAmount,
			int TransactionStatusId,
			String TransactionId
			)
	{
		this(LocalDateTime.parse(timestamp,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),TransactionWithdrawalAmount,TransactionDepositAmount,Payer,Payee,TransactionType,TransactionReferenceNumber,TransactionClosingAmount,TransactionStatusId,TransactionId);
	}
	
	
}
