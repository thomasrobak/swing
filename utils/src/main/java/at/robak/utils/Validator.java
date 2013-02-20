/*
 * Created on Sep 25, 2006
 * Gekas
 *
 */
package at.robak.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author schober
 *
 */
public class Validator {

    /**
     * 
     */
    public Validator() {
        super();
    }

    public static boolean isValidYear(String year) {
        if (year == null) {
            return false;
        }
        return year.matches("20\\d{2}");
    }

    public static boolean isValidFirma(String firma) {
        if ("".equals(firma.trim()) || firma == null) {
            return true;
        }
        return firma.matches("([1-9]|1[0-9]|2[01])");
    }

    public static boolean isValidObjekt(String objekt) {
        if ("".equals(objekt.trim()) || objekt == null) {
            return true;
        }
        return objekt.matches("\\d{1,7}");
    }

    public static boolean isValidObjektart(String objektart) {
        if ("".equals(objektart.trim()) || objektart == null) {
            return true;
        }
        return objektart.matches("d?[1-3]");
    }

    public static boolean isValidObjektgeshh(String objektgeshh) {
        if ("".equals(objektgeshh.trim()) || objektgeshh == null) {
            return true;
        }
        return objektgeshh.matches("d?[1-2]");
    }

    public static boolean isValidMieternummer(String mieternummer) {
        if ("".equals(mieternummer.trim()) || mieternummer == null) {
            return true;
        }
        return mieternummer.matches("\\d{1,4}");
    }

    public static boolean isValidMieterfolge(String mieterfolge) {
        if ("".equals(mieterfolge.trim()) || mieterfolge == null) {
            return true;
        }
        return mieterfolge.matches("\\d{1,2}");
    }

    public static boolean isValidFloat(String sFloat) {
        if ("".equals(sFloat) || sFloat == null) {
            return false;
        }
        return sFloat.matches("-?(\\d+(\\x2E\\d+)?)?");
    }

    public static boolean isValidBetrag(String betrag) {
        if ("".equals(betrag) || betrag == null) {
            return false;
        }

        // Komma und Punkte rausnehmen aus Betragsfeld
//    	String h = betrag.replaceAll("\\.|,", "");
//    	try {
//    	    
//    	    int b = Integer.parseInt(h);
//    	    
//    	} catch (Exception ex){
//    	  return false;  
//    	}

        // Abfrage: erlaubt ist mit wahlweise minus -?
        // (\\d{1,9},\\d{2}) --> eine bis neun Stellen dann 2 Kommastellen oder
        // (\\d{1,3}((\\.(\\d{3})){0,2}),\\d{2}) oder xxx.xxx.xxx,xx
        return betrag.matches("^-?((\\d{1,9})|(\\d{1,3}((\\.(\\d{3})){0,2})))(,\\d{1,2})?$");
    }

    public static boolean isValidNumeric(String sNumeric) {
        if ("".equals(sNumeric) || sNumeric == null) {
            return false;
        }
        return sNumeric.matches("-?\\d{1,11}");
    }

    public static boolean isValidUserNameAusgang(String sMitarbeiter) {
        if ("".equals(sMitarbeiter.trim()) || sMitarbeiter.trim() == null) {
            return true;
        }
        return sMitarbeiter.matches("\\w{1,10}");
    }

    public static boolean isValidDatum(String datum) throws ParseException {
        if ("".equals(datum)) {
            return true;
        }

        if (datum.length() > 10) {
            return false;
        }

        String[] h = datum.split("\\.");
        if (h.length != 3) {
            return false;
        }

        // Abfrage, ob Tag 2 Stellen, Monat 2 Stellen und Jahr 4 Stellen hat
        if (h[0].length() != 2) {
            return false;
        }
        if (h[1].length() != 2) {
            return false;
        }
        if (h[2].length() != 4) {
            return false;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.setLenient(false);
        Date dateObject = formatter.parse(datum);

        return true;
    }

    public static boolean isSmallerDate(String vonDatum, String bisDatum) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.setLenient(false);
        return formatter.parse(vonDatum).getTime() < formatter.parse(bisDatum).getTime();
    }

    public static boolean isValidBelegNummer(String belegNummer) {
        if ("".equals(belegNummer.trim()) || belegNummer == null) {
            return true;
        }
        return belegNummer.matches("\\d{1,5}");
    }
}
