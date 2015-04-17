package com.test.zipjettest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by kavya, 17-04-2015.
 */
public class DatePickDialog extends DialogFragment {
    public static final String YEAR = "Year";
    public static final String MONTH = "Month";
    public static final String DAY = "Day";

    private DatePickerDialog.OnDateSetListener mListener;

    private int year;
    private int month;
    private int day;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mListener = (DatePickerDialog.OnDateSetListener) activity;
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }

    public static DatePickDialog newInstance(int year, int month, int day) {
        DatePickDialog newDialog = new DatePickDialog();

        // Supply initial date to show in dialog.
        Bundle args = new Bundle();

        args.putInt(DAY, day);
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);

        newDialog.setArguments(args);

        return newDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getInt(DAY);
        month = getArguments().getInt(MONTH);
        year = getArguments().getInt(YEAR);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final DatePickerDialog picker = new DatePickerDialog(getActivity(),
                getConstructorListener(), year, month, day);

        // set minimum date to current date
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        if (hasJellyBeanAndAbove()) {
            picker.setButton(DialogInterface.BUTTON_POSITIVE,
                    getActivity().getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatePicker dp = picker.getDatePicker();
                            mListener.onDateSet(dp,
                                    dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        }
                    });
            picker.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getActivity().getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        }
        return picker;
    }

    private static boolean hasJellyBeanAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    private DatePickerDialog.OnDateSetListener getConstructorListener() {
        return hasJellyBeanAndAbove() ? null : mListener;
    }
}
