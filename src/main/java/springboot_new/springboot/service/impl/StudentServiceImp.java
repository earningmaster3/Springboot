package springboot_new.springboot.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot_new.springboot.dto.AddStudentReqDto;
import springboot_new.springboot.dto.StudentDto;
import springboot_new.springboot.entity.Student;
import springboot_new.springboot.exception.BadRequestException;
import springboot_new.springboot.exception.ResourceNotFoundException;
import springboot_new.springboot.repository.StudentRepo;
import springboot_new.springboot.service.StudentService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {

    private final StudentRepo  studentRepo;
@Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StudentDto> getAllStudent() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
    }

    @Override
    public StudentDto getStudentByID(long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @Override
    public StudentDto createNewStudent(AddStudentReqDto addStudentReqDto) {
        if(studentRepo.existsByEmail(addStudentReqDto.getEmail())){
            throw new BadRequestException("Email already exists: " + addStudentReqDto.getEmail());
        }
        Student student = new Student();
        student.setName(addStudentReqDto.getName());
        student.setEmail(addStudentReqDto.getEmail());

        Student savedStudent = studentRepo.save(student);
        return new StudentDto(savedStudent.getId(), savedStudent.getName(), savedStudent.getEmail());
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepo.deleteById(id);

    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentReqDto addStudentReqDto) {

        //check id is here or not
       Student existing = studentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Student not found with" + id));
       //update the fields
       existing.setName(addStudentReqDto.getName());
       existing.setEmail(addStudentReqDto.getEmail());
       //update the student
        Student updated = studentRepo.save(existing);
        //Entity->response dto
        StudentDto response = new StudentDto();
        response.setId(updated.getId());
        response.setEmail(updated.getEmail());
        return response;

    }


}
