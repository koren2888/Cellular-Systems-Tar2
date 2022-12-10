package com.example.cellular_systems_tar2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cellular_systems_tar2.model.Model;
import com.example.cellular_systems_tar2.model.Student;

public class StudentDetailsActivity extends AppCompatActivity {

    int studentIndex;
    Boolean needReload = true;
    Boolean didReloaded = false;
    ActivityResultLauncher<Intent> reloadActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        reloadActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        needReload = (result.getResultCode() == Model.RESULTS.RELOAD.ordinal());
                        didReloaded = didReloaded || needReload;
                        if (result.getResultCode() == Model.RESULTS.DELETE.ordinal()) {
                            setResult(Model.RESULTS.RELOAD.ordinal());
                            finish();
                        }
                    }
                });

        studentIndex = getIntent().getIntExtra("studentIndex", 0);

        Button editBtn = findViewById(R.id.studentDetails_edit_btn);
        editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditStudentActivity.class);
            intent.putExtra("studentIndex", studentIndex);
            reloadActivityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needReload) {
            setResult((didReloaded)? Model.RESULTS.RELOAD.ordinal(): Model.RESULTS.CANCEl.ordinal());

            Student student = Model.instance().getAllStudents().get(studentIndex);

            setStudentDetails(student);

            needReload = false;
        }
    }

    private void setStudentDetails(Student student) {
        TextView nameTv = findViewById(R.id.studentDetails_name_value);
        TextView idTv = findViewById(R.id.studentDetails_id_value);
        TextView phoneTv = findViewById(R.id.studentDetails_phone_value);
        TextView addressTv = findViewById(R.id.studentDetails_address_value);
        CheckBox checkedCb = findViewById(R.id.studentDetails_checked);

        nameTv.setText(student.name);
        idTv.setText(student.id);
        phoneTv.setText(student.phoneNumber);
        addressTv.setText(student.address);
        checkedCb.setChecked(student.isChecked);
        checkedCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student.isChecked = checkedCb.isChecked();
                setResult(Model.RESULTS.RELOAD.ordinal());
            }
        });
    }
}