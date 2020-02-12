package com.SAA.POC.JSON.Service;

import com.SAA.POC.JSON.VO.College;
import com.SAA.POC.JSON.VO.Student;
import com.SAA.POC.JSON.VO.Teacher;

import java.util.ArrayList;

public class CollegeService {
    private static College college;

    public static College getCollege(int iterationCount) {

        college = new College();
        ArrayList<Student> studentArrayList = new ArrayList<>();
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        for (int i = 1; i <= iterationCount; i++) {
            Student student = new Student();
            student.setId((int) Math.random());
            student.setMobile(1234567890);
            student.setAddress("Baker Street Lane " + i);
            student.setName("john Watson" + i);

            studentArrayList.add(student);

        }
        college.setStudentList(studentArrayList);
        for (int i = 1; i <= iterationCount; i++) {
            Teacher teacher = new Teacher();
            teacher.setId((int) Math.random());
            teacher.setMobile(1234567890);
            teacher.setAddress("Baker Street Lane " + i);
            teacher.setName("john Watson" + i);

            teacherArrayList.add(teacher);
        }
        college.setTeacherList(teacherArrayList);

        return college;
    }


}