package com.silver.app.kalanama.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.silver.app.kalanama.R;

import java.util.Arrays;

/**
 * Created by admin on 08/19/2015.
 */

public class NumberPickerDialog extends DialogFragment {
    private static final String KEY_DIALOG_TITLE = "dialogTitle";
    private static final String KEY_INIT_NUM = "1";
    private static final String KEY_NUMS = "KEY_NUMS";

    private Context context;
    private ButtonClickListener mButtonClickListener;
    private OnDialogResultSetListener mOnDialogResultSetListener;
    private Bundle mArgument;
    private NumberPicker npNum;
    String[] nums;

    public NumberPickerDialog() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mButtonClickListener = new ButtonClickListener();
    }

    public static NumberPickerDialog newInstance(CharSequence dialogTitle,
                                                 String initNum, String[] data) {
        // Create a new instance of DateTimePicker
        NumberPickerDialog mNumberPickerDialog = new NumberPickerDialog();
        // Setup the constructor parameters as arguments
        Bundle mBundle = new Bundle();
        mBundle.putCharSequence(KEY_DIALOG_TITLE, dialogTitle);
        mBundle.putString(KEY_INIT_NUM, initNum);
        mBundle.putStringArray(KEY_NUMS, data);
        mNumberPickerDialog.setArguments(mBundle);
        // Return instance with arguments
        return mNumberPickerDialog;
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
        // mDialog.setView(createDateTimeView(mDialog.getLayoutInflater(),
        // mArgument.getString(KEY_INIT_NUM)));

        View mView = mDialog.getLayoutInflater().inflate(
                R.layout.dialog_number_picker, null);
        nums = mArgument.getStringArray(KEY_NUMS);
        String init_num = mArgument.getString(KEY_INIT_NUM);

        npNum = (NumberPicker) mView.findViewById(R.id.npNums);

        npNum.setDisplayedValues(nums);
        npNum.setMinValue(0);
        npNum.setMaxValue(nums.length - 1);
        npNum.setValue(Arrays.asList(nums).indexOf(init_num));

        mDialog.setView(mView);
        return mDialog;
    }

    public void setOnDialogResultSetListener(
            OnDialogResultSetListener onDialogResultSetListener) {
        mOnDialogResultSetListener = onDialogResultSetListener;
    }

    private class ButtonClickListener implements
            DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int result) {
            if (DialogInterface.BUTTON_POSITIVE == result) {
                mOnDialogResultSetListener.ResultSet(nums[npNum.getValue()]);
            }
        }
    }
}

