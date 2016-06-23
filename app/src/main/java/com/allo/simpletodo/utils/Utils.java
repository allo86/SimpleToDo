package com.allo.simpletodo.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ALLO on 22/6/16.
 */
public class Utils {

    /* Keyboard */

    public static void hideKeyboard(Context context, View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /* Time */

    public static String formatTime(int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();

        if (hourOfDay < 10) {
            sb.append("0").append(hourOfDay);
        } else {
            sb.append(hourOfDay);
        }
        sb.append(":");

        if (minute < 10) {
            sb.append("0").append(minute);
        } else {
            sb.append(minute);
        }

        return sb.toString();
    }

    public static String formatDate(Date date) {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        return dateFormatter.format(date);
    }

    public static String formatDateTime(Date date) {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
        return dateFormatter.format(date);
    }
}
