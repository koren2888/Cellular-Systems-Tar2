package com.example.cellular_systems_tar2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cellular_systems_tar2.model.Model;
import com.example.cellular_systems_tar2.model.Student;

public class NewStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        TextView nameTv = findViewById(R.id.newStudent_name_input);
        TextView idTv = findViewById(R.id.newStudent_id_input);
        TextView phoneTv = findViewById(R.id.newStudent_phone_input);
        TextView addressTv = findViewById(R.id.newStudent_address_input);
        CheckBox checkedCb = findViewById(R.id.newStudent_checked);

        Button saveButton = findViewById(R.id.newStudent_save_btn);
        Button cancelButton = findViewById(R.id.newStudent_cancel_btn);

        saveButton.setOnClickListener(view -> {
            Student newStudent = new Student(nameTv.getText().toString(), idTv.getText().toString(),
                    phoneTv.getText().toString(), addressTv.getText().toString(), "",
                    checkedCb.isChecked());
            Model.instance().addStudent(newStudent);

            setResult(Model.RESULTS.RELOAD.ordinal());
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            setResult(Model.RESULTS.CANCEl.ordinal());
            finish();
        });
    }
}