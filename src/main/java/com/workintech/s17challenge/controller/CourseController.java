package com.workintech.s17challenge.controller;

import com.workintech.s17challenge.entity.ApiResponse;
import com.workintech.s17challenge.entity.Course;
import com.workintech.s17challenge.entity.CourseGpa;
import com.workintech.s17challenge.exception.ApiException;
import com.workintech.s17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    List<Course> courses;

    private CourseGpa lowCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa highCourseGpa;

    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") CourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init(){
        this.courses = new ArrayList<>();
    }

    @GetMapping("/")
    public List<Course> findAll(){
        return courses;
    }

    @GetMapping("/{name}")
    public Course findByName(@PathVariable String name){
        CourseValidation.chechkName(name);
        return courses.stream()
                .filter(course-> course.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(()->new ApiException("course not found with given name"+ name, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@RequestBody Course course){
        CourseValidation.chechkName(course.getName());
        CourseValidation.chechkCredit(course.getCredit());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(course,totalGpa);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }
    private Integer getTotalGpa(Course course){
        if(course.getCredit()<=2){
            return course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.Gpa();
        }
        else if(course.getCredit() == 3){
            return course.getGrade().getCoefficient()* course.getCredit()* mediumCourseGpa.Gpa();
        }
        else{
            return course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.Gpa();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody Course course){
        CourseValidation.chechkId(id);
    CourseValidation.chechkCredit(course.getCredit());
    CourseValidation.chechkName(course.getName());
    Course existingCourse = courses.stream()
            .filter(c->c.getId().equals(id))
            .findFirst()
            .orElseThrow(()->new ApiException("course not found"+id, HttpStatus.BAD_REQUEST));
    int indexOfExistingCourse = courses.indexOf(existingCourse);
    course.setId(id);
    courses.set(indexOfExistingCourse,course);
    Integer totalGpa = getTotalGpa(course);
    ApiResponse apiResponse = new ApiResponse(courses.get(indexOfExistingCourse),totalGpa);
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        Course existingCourse = courses.stream()
                .filter(c->c.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new ApiException("course not found"+id, HttpStatus.BAD_REQUEST));
        courses.remove(existingCourse);
    }
}
