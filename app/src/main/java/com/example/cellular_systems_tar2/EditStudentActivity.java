package com.example.cellular_systems_tar2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cellular_systems_tar2.model.Model;
import com.example.cellular_systems_tar2.model.Student;

public class EditStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        TextView nameTv = findViewById(R.id.editStudent_name_input);
        TextView idTv = findViewById(R.id.editStudent_id_input);
        TextView phoneTv = findViewById(R.id.editStudent_phone_input);
        TextView addressTv = findViewById(R.id.editStudent_address_input);
        CheckBox checkedCb = findViewById(R.id.editStudent_checked);

        Button saveButton = findViewById(R.id.editStudent_save_btn);
        Button deleteButton = findViewById(R.id.editStudent_delete_btn);
        Button cancelButton = findViewById(R.id.editStudent_cancel_btn);

        int studentIndex = getIntent().getIntExtra("studentIndex", 0);
        Student student = Model.instance().getAllStudents().get(studentIndex);

        setDefaultValues(student, nameTv, idTv, phoneTv, addressTv, checkedCb);

        saveButton.setOnClickListener(view -> {
            student.name = nameTv.getText().toString();
            student.id = idTv.getText().toString();
            student.phoneNumber = phoneTv.getText().toString();
            student.address = addressTv.getText().toString();
            student.isChecked = checkedCb.isChecked();

            setResult(Model.RESULTS.RELOAD.ordinal());
            finish();
        });

        deleteButton.setOnClickListener(view -> {
            Model.instance().getAllStudents().remove(studentIndex);
            setResult(Model.RESULTS.DELETE.ordinal());
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            setResult(Model.RESULTS.CANCEl.ordinal());
            finish();
        });
    }

    private void setDefaultValues(Student student, TextView nameTv, TextView idTv, TextView phoneTv,
                                  TextView addressTv, CheckBox checkedCb) {
        nameTv.setText(student.name);
        idTv.setText(student.id);
        phoneTv.setText(student.phoneNumber);
        addressTv.setText(student.address);
        checkedCb.setChecked(student.isChecked);
    }
}