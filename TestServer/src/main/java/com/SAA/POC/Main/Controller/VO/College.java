package com.SAA.POC.Main.Controller.VO;

import java.util.ArrayList;

public class College {

    public College(){}
    private ArrayList<Student> studentList;
    private ArrayList<Teacher> teacherList;

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }
}
