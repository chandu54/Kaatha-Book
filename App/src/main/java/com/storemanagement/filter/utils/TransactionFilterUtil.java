package com.storemanagement.filter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.storemanagement.filter.TransactionFilter;
import com.storemanagement.filter.TransactionFilter.AmountOperator;
import com.storemanagement.pagination.PaginationUtils;

public class TransactionFilterUtil extends PaginationUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionFilterUtil.class);
	
	public static void setDefaultPropertiesToFilter(TransactionFilter transactionFilter) {
		if (transactionFilter.getFromDate() == null) {
			java.util.Date currentDate = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, -90);
			String dateString = formatter.format(cal.getTime());
			try {
				transactionFilter.setFromDate(formatter.parse(dateString));
			} catch (Exception e) {
				logger.error("Error while preparing fromDate");
			}
		}

		if (transactionFilter.getToDate() == null) {
			java.util.Date currentDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			String dateString = formatter.format(currentDate);
			try {
				transactionFilter.setToDate(formatter.parse(dateString));
			} catch (ParseException e) {
				logger.error("Error while preparing toDate");
			}
		}
		if(transactionFilter.getAmountOperator() == null) {
			transactionFilter.setAmountOperator(AmountOperator.EQUALS);
		}
	}
}
