package com.test.zipjettest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity implements DatePickerDialog.OnDateSetListener {
    private MyAutoCompleteTextView start, end;
    private Button search;
    private TextView date;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuggestionAdapter adapter = new SuggestionAdapter(this);

        // start location
        start = (MyAutoCompleteTextView) findViewById(R.id.start);
        start.setAdapter(adapter);
        start.setThreshold(0);
        start.setOnItemClickListener(startListener);
        start.setLoadingIndicator((android.widget.ProgressBar) findViewById(R.id.loading1));

        // end location
        end = (MyAutoCompleteTextView) findViewById(R.id.end);
        end.setAdapter(adapter);
        end.setThreshold(0);
        end.setOnItemClickListener(endListener);
        end.setLoadingIndicator((android.widget.ProgressBar) findViewById(R.id.loading2));

        // Display Date in Text View
        date = (TextView) findViewById(R.id.date);

        // retain date during config changes
        if(savedInstanceState != null){
            year = savedInstanceState.getInt("year");
            month = savedInstanceState.getInt("month");
            day = savedInstanceState.getInt("day");
        }
        else{
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        setDate(year, month, day);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Select date through date picker
                DialogFragment datePicker = DatePickDialog.newInstance(year, month, day);
                datePicker.show(getFragmentManager(), "date_picker");
            }
        });

        // Search Button
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Search is not yet implemented",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Start location listener *
     */
    AdapterView.OnItemClickListener startListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!start.getText().toString().equals("") && !end.getText().toString().equals("")) {
                search.setEnabled(true);
            } else {
                search.setEnabled(false);
            }
        }
    };

    /**
     * End location listener *
     */
    AdapterView.OnItemClickListener endListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!start.getText().toString().equals("") && !end.getText().toString().equals("")) {
                search.setEnabled(true);
            } else {
                search.setEnabled(false);
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // Saved last selected date
        outState.putInt("year", year);
        outState.putInt("month", month);
        outState.putInt("day", day);
    }

    @Override
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
        year = selectedYear;
        month = selectedMonth;
        day = selectedDay;
        setDate(year, month, day );
    }

    public void setDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("-")
                .append(month + 1).append("-").append(year));
    }
}
