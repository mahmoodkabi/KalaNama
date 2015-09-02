package com.silver.app.kalanama.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.silver.app.kalanama.R;

import java.util.Arrays;

/**
 * Created by admin on 08/19/2015.
 */
public class PersianDatePickerDialog extends DialogFragment {
    public static final String TAG_FRAG_DATE_TIME = "fragDateTime";
    private static final String KEY_DIALOG_TITLE = "dialogTitle";
    private static final String KEY_INIT_DATE = "initDate";

    private Context context;
    private ButtonClickListener mButtonClickListener;
    private OnDialogResultSetListener mOnDialogResultSetListener;
    private Bundle mArgument;
    private NumberPicker npYear;
    private NumberPicker npMonth;
    private NumberPicker npDay;
    String[] years;
    String[] monthes;
    int lastDay;

    // DialogFragment constructor must be empty
    public PersianDatePickerDialog() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mButtonClickListener = new ButtonClickListener();
    }

    /**
     *
     * @param dialogTitle
     *            Title of the DateTimePicker DialogFragment
     * @param initDate
     *            Initial Date and Time set to the Date and Time Picker
     * @return Instance of the DateTimePicker DialogFragment
     */
    public static PersianDatePickerDialog newInstance(CharSequence dialogTitle,
                                                      String initDate) {
        // Create a new instance of DateTimePicker
        PersianDatePickerDialog mDateTimePicker = new PersianDatePickerDialog();
        // Setup the constructor parameters as arguments
        Bundle mBundle = new Bundle();
        mBundle.putCharSequence(KEY_DIALOG_TITLE, dialogTitle);
        mBundle.putString(KEY_INIT_DATE, initDate);
        mDateTimePicker.setArguments(mBundle);
        // Return instance with arguments
        return mDateTimePicker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Retrieve Argument passed to the constructor
        mArgument = getArguments();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(mArgument.getCharSequence(KEY_DIALOG_TITLE));
        mBuilder.setNegativeButton("لغو", mButtonClickListener);
        mBuilder.setPositiveButton("تایید", mButtonClickListener);

        AlertDialog mDialog = mBuilder.create();
        mDialog.setView(createDateTimeView(mDialog.getLayoutInflater(),
                mArgument.getString(KEY_INIT_DATE)));

        return mDialog;
    }

    private View createDateTimeView(LayoutInflater layoutInflater,
                                    String initDate) {
        // Inflate the XML Layout using the inflater from the created Dialog
        View mView = layoutInflater.inflate(R.layout.dialog_date_picker, null);
        String[] ymd = initDate.split("/");

        npYear = (NumberPicker) mView.findViewById(R.id.npYear);
        npMonth = (NumberPicker) mView.findViewById(R.id.npMonth);
        npDay = (NumberPicker) mView.findViewById(R.id.npDay);
        years = new String[] { "1393", "1394" };
        monthes = new String[] { "فروردین", "اردیبهشت", "خرداد", "تیر",
                "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند" };

        npYear.setDisplayedValues(years);
        npYear.setMinValue(0);
        npYear.setMaxValue(1);
        npYear.setValue(Arrays.asList(years).indexOf(ymd[0]));

        npMonth.setDisplayedValues(monthes);
        npMonth.setMinValue(0);
        npMonth.setMaxValue(11);

        lastDay = Integer.parseInt(ymd[2]);
        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
                MonthChanged(newVal);

            }
        });
        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
                lastDay = newVal + 1;
                if (newVal == npDay.getMinValue()
                        && oldVal == npDay.getMaxValue()) {
                    npMonth.setValue(npMonth.getValue() + 1);
                    MonthChanged(npMonth.getValue());
                }
                if (oldVal == npDay.getMinValue()
                        && newVal == npDay.getMaxValue()) {
                    npMonth.setValue(npMonth.getValue() - 1);
                    MonthChanged(npMonth.getValue());
                }
            }
        });
        npMonth.setValue(Integer.parseInt(ymd[1]) - 1);
        MonthChanged(npMonth.getValue());
        return mView;
    }

    private void MonthChanged(int val) {
        int maxDays = 31;
        if (val > 5)
            maxDays = 30;
        String[] days = new String[maxDays];
        for (int d = 0; d < days.length; d++)
            days[d] = String.valueOf(d + 1);

        npDay.setMaxValue(0);
        npDay.setDisplayedValues(days);
        npDay.setMinValue(0);
        npDay.setMaxValue(days.length - 1);

        if (lastDay <= days.length)
            npDay.setValue(lastDay - 1);
        else
            npDay.setValue(days.length - 1);
    }

    /**
     * Sets the OnDateTimeSetListener interface
     *
     * @param onDateTimeSetListener
     *            Interface that is used to send the Date and Time to the
     *            calling object
     */
    public void setOnDateTimeSetListener(
            OnDialogResultSetListener onDialogResultSetListener) {
        mOnDialogResultSetListener = onDialogResultSetListener;
    }

    private class ButtonClickListener implements
            DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int result) {
            // Determine if the user selected Ok
            if (DialogInterface.BUTTON_POSITIVE == result) {
                String date;
                String m = String.valueOf(npMonth.getValue() + 1);
                if (npMonth.getValue() + 1 < 10)
                    m = "0" + m;
                String d = String.valueOf(npDay.getValue() + 1);
                if (npDay.getValue() + 1 < 10)
                    d = "0" + d;
                date = years[npYear.getValue()] + "/" + m + "/" + d;
                mOnDialogResultSetListener.ResultSet(date);
            }
        }
    }

    /**
     * Interface for sending the Date and Time to the calling object
     */

}
