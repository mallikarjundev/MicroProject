package com.stud.controller;

import com.stud.entity.Student;
import com.stud.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/student")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public Student saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @GetMapping("/{id}")
    public CompletableFuture<String> getStudentWithDepartment(@PathVariable Long id){
        return CompletableFuture.supplyAsync(()->studentService.getStudentWithDepartment(id));
    }
}
