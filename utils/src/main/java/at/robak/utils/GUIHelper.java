/*
 * Created on Apr 11, 2005
 *
 */
package at.robak.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;

import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUIHelper {

    private static final GUIHelper INSTANCE = new GUIHelper();
    public static String[] wochentage = new String[]{
        "", "Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"
    };

    public GUIHelper() {
    }

    public static int parseOrZero(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void setComboBoxesToDate(JComboBox jComboBox0, JComboBox jComboBox1, JComboBox jComboBox2) {
        setComboBoxesToDate(jComboBox0, jComboBox1, jComboBox2, new GregorianCalendar());
    }

    public static void setComboBoxesToDate(JComboBox jComboBox0, JComboBox jComboBox1, JComboBox jComboBox2, Calendar calendar) {
        jComboBox0.setSelectedIndex(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        jComboBox1.setSelectedIndex(calendar.get(Calendar.MONTH));
        jComboBox2.setSelectedIndex(calendar.get(Calendar.YEAR) - 2000);
    }

    public static void SetComboBoxToName(JComboBox jComboBox0, String name) {
        for (int i = 0; i < jComboBox0.getItemCount(); ++i) {
            if (((String) jComboBox0.getItemAt(i)).equals(name)) {
                jComboBox0.setSelectedIndex(i);
                return;
            }
        }
        jComboBox0.setSelectedIndex(0);
    }

    public static void setTime(JTextField textfield, long time) {
        if (time > 0) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(time);
            String date = "" + cal.get(Calendar.DATE);
            String month = "" + (cal.get(Calendar.MONTH) + 1);
            String year = "" + cal.get(Calendar.YEAR);
            String hour = "" + (cal.get(Calendar.HOUR) + (cal.get(Calendar.AM_PM) == Calendar.PM ? 12 : 0));
            String min = "" + cal.get(Calendar.MINUTE);
            textfield.setText(
                    (date.length() < 2 ? "0" : "") + date + "-"
                    + (month.length() < 2 ? "0" : "") + month + "-"
                    + year + " "
                    + (hour.length() < 2 ? "0" : "") + hour + ":"
                    + (min.length() < 2 ? "0" : "") + min);

        } else {
            textfield.setText("");
        }
    }

    public static void cloneText(JTextField src, JTextField[] dst) {
        for (int i = 0; i < dst.length; ++i) {
            dst[i].setText(src.getText());
        }
    }

    public static String formatText(String str, int cols) {
        String ret = "";

        for (int end = cols; end < str.length(); end += cols) {
            ret += str.substring(end - cols, end) + "\n";
        }

        return ret;
    }

    public static String formatDecimal(double in) {
        try {
            BigDecimal dec = new BigDecimal(in);
            return dec.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (NumberFormatException ex) {
            /* cannot be handled */
        }
        return "" + in;
    }

    public static String formatDecimal(String in) {
        try {
            BigDecimal dec = new BigDecimal(in);
            return dec.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (NumberFormatException ex) {
            /* cannot be handled */
        }
        return in;
    }

    public static String prettyFloatPDF(Object o) {
        if (o instanceof String) {
            return prettyFloatPDF((String) o);
        }
        return o.toString();
    }

    public static String prettyFloatPDF(String v) {
        return prettyFloat(formatDecimal(v));
    }

    public static String prettyFloat(String v) {
        if (v == null) {
            return v;
        }

        String sign = "";
        if (v.startsWith("-")) {
            sign = "-";
            v = v.substring(1);
        }

        String[] h = v.split("\\.");
        if (h.length != 2) {
            return v;
        }

        byte[] mb = ReverseArray.reverse(h[0].getBytes());
        String ret = "";
        for (int i = 0; i < mb.length; ++i) {
            if (i % 3 == 0 && i > 0) {
                ret += '.';
            }
            ret += (char) mb[i];
        }
        return sign + (new String(ReverseArray.reverse(ret.getBytes()))) + "," + h[1];
    }

    public static Object prettyFloat(Object v) {
        if (v instanceof BigDecimal) {
            return prettyFloat(((BigDecimal) v).toString());
        }
        if (!(v instanceof String)) {
            return v;
        }
        return prettyFloat((String) v);
    }

    public static void translateComma(JTextField field) {
        field.setText(field.getText().replace(',', '.'));
    }

    public static String formatCent(String s) {
        if (s.indexOf(".") > 0) {
            return s;
        }
        String sign = "";
        if (s.startsWith("-")) {
            s = s.substring(1);
            sign = "-";
        }
        while (s.length() < 3) {
            s = "0" + s;
        }
        int lenght = s.length();
        String vorKomma = s.substring(0, lenght - 2);
        String nachKomma = s.substring(lenght - 2);
        return sign + vorKomma + "." + nachKomma;
    }

    // von 3,0 --> 3
    public static int formatToIntegerWithoutComma(String s) {
        int lenght = s.length();
        if (lenght < 3) {
            return 0;
        }

        //    if (s.indexOf(".") > 0) {
        //    return s;
        //}


        String vorKomma = s.substring(0, lenght - 2);
        String nachKomma = s.substring(lenght - 2);
        return Integer.parseInt(vorKomma);
    }

    public static String formatStringDateToTimestampString(String date, String format) {
        if ("".equals(date)) {
            return "";
        }
        String formatedDate = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);
        formatedDate += format;
        return formatedDate;
    }

    public static String prettyCent(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }

        return prettyFloat(formatCent(s));
    }

    public static String prettyCentStorno(String s) {
        String prettyCent = prettyFloat(formatCent(s));

        // V 1.01 - 18.04.2007: Zähler von 15 auf 14 gestellt
        while (prettyCent.length() < 14) {
            prettyCent = '*' + prettyCent;
        }

        return prettyCent;
    }

    // Kommastellen von z.B. 12,1234 auf 12,12 �ndern
    public static String cutCommaPositionsFromXToY(String betrag, int vonStellen, int aufStellen) {
        String[] splitPrice = betrag.split("\\.");
        if (splitPrice.length > 1) {
            if (splitPrice[1].length() == vonStellen) {
                betrag = betrag.substring(0, betrag.length() - aufStellen);
            }
        }
        return betrag;
    }

    public static String prettyEdvNummer(String fa, String objekt, String objektart, String objektgeshh, String mieternummer, String mieterfolge) {
        String result = "";
        result += (fa == null ? "" : pad(fa, "0", 2)) + " " + (objekt == null ? "" : pad(objekt, "0", 7)) + " " + (objektart == null ? "" : pad(objektart, "0", 1)) + " " + (objektgeshh == null ? "" : pad(objektgeshh, "0", 1)) + " " + (mieternummer == null ? "" : pad(mieternummer, "0", 4)) + " " + (mieterfolge == null ? "" : pad(mieterfolge, "0", 2));
        return result;
    }

    public static String prettyObjektNummer(String fa, String objekt, String objektart, String objektgeshh) {
        String result = "";
        result += (fa == null ? "" : pad(fa, "0", 2)) + " " + (objekt == null ? "" : pad(objekt, "0", 7)) + " " + (objektart == null ? "" : pad(objektart, "0", 1)) + " " + (objektgeshh == null ? "" : pad(objektgeshh, "0", 1));
        return result;
    }

    public static String pad(String toPad, String padWith, int len) {
        String result = new String(toPad);

        while (result.length() < len) {
            result = padWith + result;
        }

        return result;

    }

    public static String[] uglyEdvNummer(String prettyEdvNummer) {
        String[] result = new String[6];
        String[] parts = prettyEdvNummer.split(" ");
        for (int i = 0; i < parts.length; i++) {
            result[i] = "" + Integer.parseInt(parts[i]);
        }

        return result;
    }

    public static String prettyAdress(String plz, String ort, String strasse, String hausnummer, String stiege, String stock, String tuer) {
        String result = "";
        result += plz.trim() + " " + ort.trim() + ", " + strasse.trim();

        if (!"".equals(hausnummer)) {
            result += "/" + hausnummer;
        }

        if (!"".equals(stiege)) {
            result += "/" + stiege;
        }

        if (!"".equals(stock)) {
            result += "/" + stock;
        }

        if (!"".equals(tuer)) {
            result += "/" + tuer;
        }


        return result;
    }

    public static boolean isDoubleClick(MouseEvent e) {
        /*
         ** check for double click
         */

        System.out.println("Click at (" + e.getX() + ":" + e.getY() + ")");
        if (e.getClickCount() == 2) {
            System.out.println("  and it's a double click!");
            return true;
        } else {
            System.out.println("  and it's a simple click!");
            return false;
        }
    }

    public static String getWochenTag(String datum) {
        Calendar cal = new GregorianCalendar();

        String[] h = datum.split("\\.");
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(h[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(h[1]) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt("20" + h[2]));

        return wochentage[cal.get(Calendar.DAY_OF_WEEK)];
    }

    public static String formatSessionDate(String date) {
        if (date == null) {
            return "";
        }
        String[] h = date.split("\\.");
        if (h.length < 3) {
            return date;
        }
        return pad(h[0], "0", 2) + "." + pad(h[1], "0", 2) + "." + h[2];
    }
    // Diese Methode formatiert in ein 8-stelliges Datum TT.MM.JJJJ

    public static String formatDate(String date) {
        if (date == null || "01.01.0001".equals(date) || "0".equals(date)) {
            return "";
        }

        // Wenn Datum nur 7 Stellen hat, dann 0 davor
        // z.B. aus 4011995 --> 04011995
        if (date.length() == 7) {
            date = "0" + date;
        }


        if (date.matches("^\\d{8}|\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}$") == false) {
            return date;
        }

        String[] h = date.split("\\.");
        if (h.length < 3) {
            if ((date.length() == 8 || date.length() == 6) && h.length == 1) {
                h = new String[3];
                h[0] = date.substring(0, 2);
                h[1] = date.substring(2, 4);
                h[2] = date.substring(4, date.length());
            } else {
                return date;
            }
        }

        if (h[2].length() == 2) {
            h[2] = "20" + h[2];
        }

        return pad(h[0], "0", 2) + "." + pad(h[1], "0", 2) + "." + h[2];
    }

    // Diese Methode dreht und formatiert Datum von 19670528 --> 28.05.1967
    public static String turnAndFormatDate(String date) {
        if (date == null || "0".equals(date)) {
            return "";
        }

        String[] h;
        h = new String[3];
        h[0] = date.substring(0, 4);
        h[1] = date.substring(4, 6);
        h[2] = date.substring(6, 8);

        return h[2] + "." + h[1] + "." + h[0];
    }

    // Datum von 28.05.1967 to 19670528
    public static String formatToDateYYYYMMDD(String date) {
        String[] h = date.split("\\.");
        return h[2] + h[1] + padBeforeString(h[0], "0", 2);
    }

    // Datum von 19670528 to 28051967
    public static String formatToDateDDMMYYYY(String date) {
        if (date == null || "0".equals(date)) {
            return "";
        }

        String[] h;
        h = new String[3];
        h[0] = date.substring(0, 4);
        h[1] = date.substring(4, 6);
        h[2] = date.substring(6, 8);

        return h[2] + h[1] + h[0];
    }

    // Diese Methode formatiert in eine 6-stellige Zeit HH:MM:SS
    public static String formatTime(String time) {
        if (time == null) {
            return "";
        }

        time = GUIHelper.padBeforeString(time, "0", 6);

        if (time.matches("^\\d{6}|\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}$") == false) {
            return time;
        }

        String[] h = time.split("\\:");
        if (h.length < 3) {
            if ((time.length() == 6) && h.length == 1) {
                h = new String[3];
                h[0] = time.substring(0, 2);
                h[1] = time.substring(2, 4);
                h[2] = time.substring(4, 6);
            } else {
                return time;
            }
        }

        // Wenn 3 x 00 steht, wird blank zur�ckgegeben
        if ("00".equals(h[0]) && "00".equals(h[0]) && "00".equals(h[0])) {
            return "";
        }

        return pad(h[0], "0", 2) + ":" + pad(h[1], "0", 2) + ":" + h[2];
    }

    public static String formatToDateWithPoints(String s) {

        while (s.length() < 8) {
            s = "0" + s;
        }
        int lenght = s.length();
        String tag = s.substring(0, 2);
        String monat = s.substring(2, 4);
        String jahr = s.substring(4, 8);

        return tag + "." + monat + "." + jahr;
    }

    public static String getSysDateWithPoints() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    public static String getSysDateWithoutPoints() {
        return new SimpleDateFormat("ddMMyyyy").format(new Date());
    }

    public static String getSysTimeWithPoints() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getSysTimeWithoutPoints() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    public static GregorianCalendar parseDatum(String datum) {

        String[] h = datum.split("\\.");
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Integer.parseInt(h[2]), Integer.parseInt(h[1]) - 1, Integer.parseInt(h[0]));

        return cal;
    }

    public static Boolean isMailAddressValid(String mail) {
        return mail.matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$");
    }

    public static String formatBetrag(String betrag) {

        if (betrag == null) {
            return "";
        }
        String result = "";
        String vorzeichen = "";
        int z = 0;
        betrag = betrag.trim();

        // V 1.53 Minuszeichen wurde als Tausenderstelle erkannt
        if (betrag.startsWith("-")) {
            vorzeichen = "-";
            betrag = betrag.replace("-", "");
        }

        // wenn man nur Cent Betr�ge hat dann mit 0 davor
        while (betrag.length() < 3) {
            betrag = "0" + betrag;
        }

        for (int i = betrag.length() - 1; i >= 0; i--, z++) {

            result = betrag.charAt(i) + result;
            switch (z) {
                case 1:
                    result = "," + result;
                    break;
                case 4:
                case 7:
                    if (i > 0) {
                        result = "." + result;
                    }

            }
        }
        return vorzeichen + result;
    }
    // Zeichen auff�llen vor dem String

    public static String padBeforeString(String toPad, String padWith, int len) {
        String result = new String(toPad);

        while (result.length() < len) {
            result = padWith + result;
        }
        return result;
    }
    // Zeichen auff�llen nach dem String

    public static String padBehindString(String toPad, String padWith, int len) {
        String result = new String(toPad);

        while (result.length() < len) {
            result = result + padWith;
        }
        return result;
    }
    // Gesch�ftsjahre ab 1988 bis zum heurigen Jahr ermitteln
    //public static String[] getBusinessYears() {
    //  String[] retField = new String[0];
    //  int toYear = Integer.parseInt(Session.getSysJahr());
    //  for(int i=1988;i<=toYear;){
    //      retField = new String[i];
    //      i += 1;
    //  }
    //   return retField;
    //     }
    // Gesch�ftsjahre ab 1988 bis zum heurigen Jahr ermitteln
    public static enum ORDER {
        ASC, DESC
    }

    public static Vector<Integer> getBusinessYears(Integer fromYear, Integer toYear, ORDER order) {
        int distance = Math.abs(toYear - fromYear + 1);
        Vector<Integer> li = new Vector(distance);
        int from = order == ORDER.ASC ? Math.min(toYear, fromYear) : Math.max(fromYear, toYear);
        for (int i = 0; i < distance; i++) {
            li.add(from + i * (order == ORDER.ASC ? 1 : -1));
        }

        return li;
    }

    public static String prettyTwoDecimalPlaces(String value) {
        if (value == null) {
            return "";
        }
        return value.substring(0, value.length() - 2);
    }

    public static boolean isValueStraight(double d) {
        Double tD = new Double(d);
        String str = tD.toString();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                System.out.println(str.substring(i + 1, str.length()));
                int ix = Integer.valueOf(str.substring(i + 1, str.length()));
                if (ix > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean getDialogAnswer(String question) {
        Integer result = JOptionPane.showConfirmDialog(null,
                question, " ",
                JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            return true;
        }
        return false;
    }

    public static String addDaysToDate(String date, int daysToAdd) throws ParseException {
        Date now = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        Date future = calendar.getTime();
        return new SimpleDateFormat("dd.MM.yyyy").format(future);
    }
}
