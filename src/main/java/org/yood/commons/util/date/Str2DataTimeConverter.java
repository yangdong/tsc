package org.yood.commons.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;

public class Str2DataTimeConverter implements Converter<String, Timestamp> {

	private static final String PATERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String PATERN_DATE = "yyyy-MM-dd";

	@Override
	public Timestamp convert(String str) {
		try {
			return new Timestamp(new SimpleDateFormat(PATERN_DATETIME).parse(
					str).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				return new Timestamp(new SimpleDateFormat(PATERN_DATE)
						.parse(str).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}
