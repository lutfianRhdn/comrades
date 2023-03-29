package id.comrade.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormatter {
    private static final DecimalFormat sNumberFormat = new DecimalFormat("'Rp. '#,000");

    static {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("id", "ID"));
        sNumberFormat.setDecimalFormatSymbols(dfs);
    }

    public static String format(int balance) {
        return sNumberFormat.format(balance);
    }
}
