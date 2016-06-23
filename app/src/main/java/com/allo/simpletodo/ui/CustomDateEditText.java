package com.allo.simpletodo.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.allo.simpletodo.utils.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ALLO on 22/6/16.
 */
public class CustomDateEditText extends EditText {

    public interface OnDateEditTextListener {
        void onDateSelected(Date date);
    }

    private OnDateEditTextListener mListener;

    public CustomDateEditText(Context context) {
        super(context);
        init();
    }

    public CustomDateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Date date;

    protected void init() {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(getContext(), v);
                showDatePicker();
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Utils.hideKeyboard(getContext(), v);
                    performClick();
                    clearFocus();
                }
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        date = calendar.getTime();

                        // Show text
                        setText(Utils.formatDate(date));

                        // Listener
                        if (mListener != null) mListener.onDateSelected(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setDate(Date date) {
        this.date = date;
        setText(Utils.formatDate(date));
    }

    public Date getDate() {
        return date;
    }
}
