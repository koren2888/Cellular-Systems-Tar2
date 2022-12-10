package com.example.cellular_systems_tar2.model;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    public enum RESULTS {
        RELOAD,
        DELETE,
        CANCEl
    }

    public static Model instance() {
        return _instance;
    }
    private Model(){
        for (int i = 0; i < 2; i++) {
            addStudent(new Student("name " + i,""+i, "" + i, "" + i,
                    "",false));
        }
    }

    List<Student> data = new LinkedList<>();
    public List<Student> getAllStudents() {
        return data;
    }

    public void addStudent(Student st) {
        data.add(st);
    }
}
