package com.allo.simpletodo.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.allo.simpletodo.utils.Utils;

/**
 * Created by ALLO on 5/6/16.
 */
public class CustomTimeEditText extends EditText {

    public interface OnTimeEditTextListener {
        void onTimeSelected(int hourOfDay, int minute);
    }

    private OnTimeEditTextListener mListener;

    public CustomTimeEditText(Context context) {
        super(context);
        init();
    }

    public CustomTimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTimeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private TimePickerDialog timePickerDialog;

    private int hourOfDay, minute;

    protected void init() {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(getContext(), v);
                showTimePicker();
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

        hourOfDay = 0;
        minute = 0;
        setText(Utils.formatTime(hourOfDay, minute));
    }

    private void showTimePicker() {
        timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        CustomTimeEditText.this.hourOfDay = hourOfDay;
                        CustomTimeEditText.this.minute = minute;

                        // Show text
                        setText(Utils.formatTime(hourOfDay, minute));

                        // Listener
                        if (mListener != null) mListener.onTimeSelected(hourOfDay, minute);
                    }
                }, hourOfDay, minute, true);
        timePickerDialog.show();
    }

    public void setTime(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMinute() {
        return minute;
    }
}
