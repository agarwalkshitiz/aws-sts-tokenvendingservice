package com.paytm.tokenvendingservice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import com.paytm.tokenvendingservice.exception.AppException;

public class Utility {
  private static final Logger logger = LoggerFactory.getLogger(Utility.class);

  final static String dateFormat = "dd/MM/yyyy HH:mm:ssZ";

  public static String getStringDateFromTimestamp(Timestamp ts) {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Calcutta")));
    return sdf.format(new Date(ts.getTime()));
  }

  public static Timestamp getTimestampFromString(String str) {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    try {
      return new Timestamp(sdf.parse(str).getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      throw new AppException("Invalid date format:" + str, HttpStatus.BAD_REQUEST);
    }
  }

  public static Calendar getCalender(HttpServletRequest servletReq, String param) {
    String timeString = servletReq.getParameter(param);
    try {
      if (timeString.contains("%")) {
        timeString = URLDecoder.decode(timeString, "UTF-8");
      }
    } catch (UnsupportedEncodingException e1) {
      logger.error("getCalender UnsupportedEncodingException:" + e1.getMessage() + " TimeString:"
          + timeString);
      e1.printStackTrace();
    }
    if (timeString == null || timeString.trim().length() <= 0) {
      throw new AppException("Invalid " + param + " : " + timeString, HttpStatus.BAD_REQUEST);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ssZ", Locale.ENGLISH);
    try {
      sdf.parse(timeString);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new AppException("Invalid " + param + " : " + timeString, HttpStatus.BAD_REQUEST);
    }
    return sdf.getCalendar();
  }

  public static void logException(Logger logger, Level level, Throwable ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    logger.error(sw.toString());
  }
}
