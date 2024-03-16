package com.example.laundrypersonnelmis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Collection_and_Delivery extends AppCompatActivity{

    private Button collectionDateTimeButton;
    private Button deliveryDateTimeButton;
    private Button buttonOpenServices;

    private String userPhone;
    private String collectionDateTime;
    private String deliveryDateTime;
    private String frequency;

    private String specialInstructionsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_and_delivery);

        // Get the user's phone number from the intent
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("phone");

        collectionDateTimeButton = findViewById(R.id.collectionDateTimeButton);
        deliveryDateTimeButton = findViewById(R.id.deliveryDateTimeButton);

        buttonOpenServices = findViewById(R.id.nextButton);

        buttonOpenServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextServices();
            }
        });
    }

    public void showCollectionDateTimePickerDialog(View v) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Collection_and_Delivery.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        if (selectedDate.before(calendar)) {
                            Toast.makeText(Collection_and_Delivery.this, "Please select a future date", Toast.LENGTH_SHORT).show();
                        } else {
                            final int selectedYear = year;
                            final int selectedMonth = monthOfYear;
                            final int selectedDay = dayOfMonth;

                            // Launch Time Picker Dialog after selecting Date
                            TimePickerDialog timePickerDialog = new TimePickerDialog(Collection_and_Delivery.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            collectionDateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + " " + hourOfDay + ":" + minute;
                                            Toast.makeText(Collection_and_Delivery.this, "Selected Collection Date and Time: " + collectionDateTime, Toast.LENGTH_LONG).show();
                                        }
                                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                            timePickerDialog.show();
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); // Set minimum date to current date
        datePickerDialog.show();
    }

    public void showDeliveryDateTimePickerDialog(View v) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Collection_and_Delivery.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        if (selectedDate.before(calendar)) {
                            Toast.makeText(Collection_and_Delivery.this, "Please select a future date", Toast.LENGTH_SHORT).show();
                        } else {
                            final int selectedYear = year;
                            final int selectedMonth = monthOfYear;
                            final int selectedDay = dayOfMonth;

                            // Launch Time Picker Dialog after selecting Date
                            TimePickerDialog timePickerDialog = new TimePickerDialog(Collection_and_Delivery.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            deliveryDateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + " " + hourOfDay + ":" + minute;
                                            Toast.makeText(Collection_and_Delivery.this, "Selected Delivery Date and Time: " + deliveryDateTime, Toast.LENGTH_LONG).show();
                                        }
                                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                            timePickerDialog.show();
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); // Set minimum date to current date
        datePickerDialog.show();
    }

    public void openNextServices() {
        Intent intent = new Intent(Collection_and_Delivery.this, services_activity.class);
        saveDateTimeToDatabase();
        intent.putExtra("phone", userPhone);
        intent.putExtra("collectionDateTime", collectionDateTime);
        intent.putExtra("deliveryDateTime", deliveryDateTime);
        intent.putExtra("frequency",frequency);
        intent.putExtra("specialInstructionsText",specialInstructionsText);
        startActivity(intent);
    }

    private void saveDateTimeToDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userPhone).child("Orders").child("Collection_and_Delivery");
        databaseReference.child("collectionDateTime").setValue(collectionDateTime);
        databaseReference.child("deliveryDateTime").setValue(deliveryDateTime);

        RadioGroup frequencyGroup = findViewById(R.id.frequencyGroup);
        int selectedRadioButtonId = frequencyGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            frequency = selectedRadioButton.getText().toString();
            databaseReference.child("frequency").setValue(frequency);
        } else {
            // Handle case where no radio button is selected
        }

        EditText specialInstructions = findViewById(R.id.specialInstructions);
        specialInstructionsText = specialInstructions.getText().toString().trim();
        if (!specialInstructionsText.isEmpty()) {
            databaseReference.child("specialInstructions").setValue(specialInstructionsText);
        }
    }
}


