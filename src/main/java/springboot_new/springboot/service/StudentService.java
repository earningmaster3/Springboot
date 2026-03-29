package springboot_new.springboot.service;

import springboot_new.springboot.dto.AddStudentReqDto;
import springboot_new.springboot.dto.StudentDto;
import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudent();
    StudentDto getStudentByID(long id);
    StudentDto createNewStudent(AddStudentReqDto addStudentReqDto);

    void deleteStudent(Long id);

    StudentDto updateStudent(Long id, AddStudentReqDto addStudentReqDto);
}
