package com.stud.controller;

import com.stud.entity.Student;
import com.stud.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @CircuitBreaker(name = "studentservice", fallbackMethod = "fallbackMethod")
    public CompletableFuture<String> getStudentWithDepartment(@PathVariable Long id){
        return CompletableFuture.supplyAsync(()->studentService.getStudentWithDepartment(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CompletableFuture<String> fallbackMethod(@PathVariable Long id, RuntimeException e){
        return CompletableFuture.supplyAsync(()->"Service is down. Please try after some time.");
    }
}
