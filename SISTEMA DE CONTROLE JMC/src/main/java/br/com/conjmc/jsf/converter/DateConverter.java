package br.com.conjmc.jsf.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;

@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

  private static Logger log = Logger.getLogger(DateConverter.class);
  private static final String MM_DD_YY = "MM/dd/yy";
  private static final String MM_DD_YYYY = "MM/dd/yyyy";
  private SimpleDateFormat yyyyConvertor; 
  private SimpleDateFormat yyConvertor;

  public DateConverter() {
    yyyyConvertor = new SimpleDateFormat(MM_DD_YYYY);
    yyConvertor = new SimpleDateFormat(MM_DD_YY);
    yyConvertor.setLenient(false);
    yyyyConvertor.setLenient(false); 
  }

  @Override
  public Object getAsObject(FacesContext fc, UIComponent component, String value) throws ConverterException {

   if( value==null || value.isEmpty()) {
      return new Date();
    }

    Date result = null;

    try {
      if (value.length() > MM_DD_YY.length()) { 
        result = (Date)yyyyConvertor.parseObject(value);
      } else {
        result = (Date)yyConvertor.parseObject(value); 
      }
    }
    catch (ParseException e) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid date         format.","Failed to convert " + value + "");
  throw new ConverterException(message);
    }

    return result;
  }

  @Override
  public String getAsString(FacesContext fc, UIComponent component, Object date) throws     ConverterException {
    try {
      return yyyyConvertor.format((Date)date);
    }
    catch (NullPointerException e) {
      return null;
    }
  }
}
