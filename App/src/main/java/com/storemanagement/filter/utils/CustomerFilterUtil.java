package com.storemanagement.filter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.storemanagement.filter.CustomerFilter;
import com.storemanagement.filter.TransactionFilter.AmountOperator;
import com.storemanagement.pagination.PaginationUtils;

public class CustomerFilterUtil extends PaginationUtils {

	private static final Logger logger = LoggerFactory.getLogger(CustomerFilterUtil.class);

	public static void setDefaultPropertiesToFilter(CustomerFilter customerFilter) {
		if (customerFilter.getFromDate() == null) {
			java.util.Date currentDate = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, -90);
			String dateString = formatter.format(cal.getTime());
			try {
				customerFilter.setFromDate(formatter.parse(dateString));
			} catch (Exception e) {
				logger.error("Error while preparing fromDate");
			}
		}

		if (customerFilter.getToDate() == null) {
			java.util.Date currentDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			String dateString = formatter.format(currentDate);
			try {
				customerFilter.setToDate(formatter.parse(dateString));
			} catch (ParseException e) {
				logger.error("Error while preparing toDate");
			}
		}
		if (customerFilter.isActive() == null) {
			customerFilter.setIsActive(Boolean.TRUE);
		}
		if (customerFilter.getPhoneNo() != null) {
			customerFilter.setUnformattedPhone(customerFilter.getPhoneNo());
		}
		if (customerFilter.getAmountOperator() == null) {
			customerFilter.setAmountOperator(AmountOperator.EQUALS);
		}
	}

}
