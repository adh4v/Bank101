package com.test.collections;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.text.DateFormatter;

import com.test.annotation.FieldLength;



public record Customer(
	@FieldLength(length=20)int customerId,
	@FieldLength(length=20)String customerName,
	@FieldLength(length=20)String customerUsername,
	@FieldLength(length=20)LocalDate  customerDOB,
	@FieldLength(length=20)String customerEmail,
	@FieldLength(length=20) String customerContactNumber,
	@FieldLength(length=20)String customerAlternateNumber,
	@FieldLength(length=20)String customerMailingAddress,
	@FieldLength(length=20)String customerPermanentAddress,
	@FieldLength(length=20)String customerNationality,
	@FieldLength(length=20)String customerNominee,
	@FieldLength(length=20)String customerMMID,
	@FieldLength(length=20)String customerAadhar,
	@FieldLength(length=20)String customerPANNumber,
	@FieldLength(length=20)String customerAccountId
) 	
{
	public Customer(
			int customerId,
		    String customerName,
		    String customerUsername,
		    String cust_dob,
		    String customerEmail,
		    String customerContactNumber,
		    String customerAlternateNumber,
		    String customerMailingAddress,
		    String customerPermanentAddress,
		    String customerNationality,
		    String customerNominee,
		    String customerMMID,
		    String customerAadhar,
		    String customerPANNumber,
		    String customerAccountId)
		{
		    this(customerId,customerName, customerUsername,
		    		LocalDate .parse(cust_dob, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
		         customerEmail, customerContactNumber, customerAlternateNumber,
		         customerMailingAddress, customerPermanentAddress, customerNationality,
		         customerNominee, customerMMID, customerAadhar, customerPANNumber, customerAccountId);
		}
	
	
}
