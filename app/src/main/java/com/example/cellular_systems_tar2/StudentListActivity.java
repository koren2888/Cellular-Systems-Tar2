package com.example.cellular_systems_tar2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cellular_systems_tar2.model.Model;
import com.example.cellular_systems_tar2.model.Student;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    List<Student> studentsList;
    Boolean needReload = true;
    ActivityResultLauncher<Intent> reloadActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        reloadActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        needReload = (result.getResultCode() == Model.RESULTS.RELOAD.ordinal());
                    }
                });

        Button btn = findViewById(R.id.studentList_new_student);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewStudentActivity.class);
            reloadActivityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needReload) {
            studentsList = Model.instance().getAllStudents();
            RecyclerView list = findViewById(R.id.studentlist_list);
            list.setHasFixedSize(true);

            list.setLayoutManager(new LinearLayoutManager(this));
            StudentRecyclerAdapter adapter = new StudentRecyclerAdapter();
            list.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    Intent intent = new Intent(StudentListActivity.this,
                            StudentDetailsActivity.class);
                    intent.putExtra("studentIndex", pos);
                    reloadActivityResultLauncher.launch(intent);
                }
            });

            needReload = false;
        }
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView idTv;
        CheckBox cb;
        public StudentViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.studentlistrow_name_tv);
            idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
            cb = itemView.findViewById(R.id.studentlistrow_cb);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int)cb.getTag();
                    Student st = studentsList.get(pos);
                    st.isChecked = cb.isChecked();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Student st, int pos) {
            nameTv.setText(st.name);
            idTv.setText(st.id);
            cb.setChecked(st.isChecked);
            cb.setTag(pos);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }
    class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentViewHolder>{
        OnItemClickListener listener;
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.student_list_row,parent,false);
            return new StudentViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            Student st = studentsList.get(position);
            holder.bind(st,position);
        }

        @Override
        public int getItemCount() {
            return studentsList.size();
        }
    }
}