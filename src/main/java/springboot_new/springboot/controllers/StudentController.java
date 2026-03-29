package springboot_new.springboot.controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot_new.springboot.dto.AddStudentReqDto;
import springboot_new.springboot.dto.StudentDto;
import springboot_new.springboot.service.StudentService;

import java.util.List;

@RestController
public class StudentController {
 private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String getHomePage(){
        return " you are in home page";
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentDto>>getStudent(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent());
    }

    @GetMapping("/student/{id}")
    public StudentDto getStudentById(@PathVariable Long id){
        return studentService.getStudentByID(id);
    }

    @PostMapping("/student")
    public ResponseEntity<StudentDto> createNewStudent(@Valid @RequestBody AddStudentReqDto addStudentReqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(addStudentReqDto));
    }

    @DeleteMapping("student/{id}")
    public ResponseEntity<StudentDto> DeleteStudent(@PathVariable Long id ){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("student/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody AddStudentReqDto addStudentReqDto){
        return ResponseEntity.ok(studentService.updateStudent(id, addStudentReqDto));
    }

}
