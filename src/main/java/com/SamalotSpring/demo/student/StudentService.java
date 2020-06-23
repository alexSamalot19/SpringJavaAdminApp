package com.SamalotSpring.demo.student;

import com.SamalotSpring.demo.EmailValidator;
import com.SamalotSpring.demo.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService,
                          EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    List<Student> getAllStudents() {
        return studentDataAccessService.selectAllStudents();
    }

    void addNewStudent(Student student) {
        addNewStudent(null, student);
    }


    void addNewStudent(UUID studentId,Student student) {
        UUID newStudentID = Optional.ofNullable(studentId).orElse(UUID.randomUUID());
        //Todo: Validate email
        if(!emailValidator.test(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + "is not valid");
        }

        //Todo: verify that email is not taken
        studentDataAccessService.insertStudent(newStudentID, student);

    }



}
