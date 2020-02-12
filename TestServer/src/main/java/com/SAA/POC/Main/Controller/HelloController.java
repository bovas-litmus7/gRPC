package com.SAA.POC.Main.Controller;

import com.SAA.POC.Main.Controller.VO.College;
import com.SAA.POC.Main.Controller.VO.Student;
import com.SAA.POC.Main.Controller.VO.Teacher;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "Cool stuffs are happening at port 6000!! check that out!!";
    }


    @RequestMapping(value = "/echoCollege", method = RequestMethod.POST)
    @ResponseBody
    public College echo(@RequestBody College college) {
        //System.out.println("Server hit @:"+Instant.now().atZone(ZoneId.systemDefault()));

        System.out.println("Rest call hit");
        College request = college;
        ArrayList<Student> studentList = request.getStudentList();
        ArrayList<Teacher> teacherList = request.getTeacherList();
        College response = new College();
        response.setStudentList(studentList);
        response.setTeacherList(teacherList);
       // System.out.println("Server reponded @:"+Instant.now().atZone(ZoneId.systemDefault()));
        return college;


    }


}
