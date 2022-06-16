package com.bookstore.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrertDate {
	
	public String getDateAndTime()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
		
	}

}
