package com.example.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

/**This activity is used to records the profile of user
 * But we do not use it any more so no comment in the file
 * @author Yuan Chen u7631839
 * */
public class ProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        Button btnChangeNickname = findViewById(R.id.btnChangeNickname);
        Button btnChangeGender = findViewById(R.id.btnChangeGender);
        Button btnChangeBirthday = findViewById(R.id.btnChangeBirthday);

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnChangeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(ProfileActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Enter nickname")
                        .setView(input)
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nickname = input.getText().toString();
                            }
                        })
                        .setNegativeButton("cancel", null)
                        .show();
            }
        });

        btnChangeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spinner spinner = new Spinner(ProfileActivity.this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ProfileActivity.this,
                        R.array.gender_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Select gender")
                        .setView(spinner)
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String gender = spinner.getSelectedItem().toString();
                            }
                        })
                        .setNegativeButton("cancel", null)
                        .show();
            }
        });

        btnChangeBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    }
                });
                datePickerDialog.show();
            }
        });
    }
}
