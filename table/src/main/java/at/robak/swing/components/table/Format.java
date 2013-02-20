package at.robak.swing.components.table;

import java.awt.Color;
import java.awt.Font;

public class Format {

    public static Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 11);

    public static Color COLOR_DEFAULT = new Color(0, 0, 0);
    public static Color COLOR_TBL_ROW1 = new Color(254, 254, 254);
    public static Color COLOR_TBL_ROW2 = new Color(202,225,255);
    public static Color COLOR_TBL_SEL = new Color(72,118,255);
    public static Color COLOR_TBL_SEARCHROW2 = new Color(220, 252, 255);
    public static Color COLOR_TBL_SEARCHROW1 = new Color(230, 250, 254);
    public static Color COLOR_TBL_SEARCHMATCH = new Color(180, 210, 250);
    public static Color COLOR_BORDER_SEARCHMATCH = Color.MAGENTA;
    public static Color COLOR_TBL_HEADER = new Color(58,95,205);
    public static Color COLOR_TBL_HEADERFONT = Color.WHITE;

    public static String ICON_PRINT_TA = "printer.jpg";
    public static String ICON_PRINT_BJ = "printer.jpg";

    public static String formatBetrag(String betrag) {

        String result = "";
        String vorzeichen = "";
        int z = 0;
        betrag = betrag.trim();

        // V 1.53 Minuszeichen wurde als Tausenderstelle erkannt
        if (betrag.startsWith("-")) {
            vorzeichen = "-";
            betrag = betrag.replace("-", "");
        }

        // wenn man nur Cent Beträge hat dann mit 0 davor
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

    public static void main(String[] args) {
        System.out.println(formatBetrag("5"));
        System.out.println(formatBetrag("45"));
        System.out.println(formatBetrag("-123"));
        System.out.println(formatBetrag("1234"));
        System.out.println(formatBetrag("-12345"));
        System.out.println(formatBetrag("123456"));
        System.out.println(formatBetrag("1234567"));
        System.out.println(formatBetrag("12345678"));
        System.out.println(formatBetrag("123456789"));
        System.out.println(formatBetrag("1234567890"));
        System.out.println(formatBetrag("12345678901"));
    }
}
