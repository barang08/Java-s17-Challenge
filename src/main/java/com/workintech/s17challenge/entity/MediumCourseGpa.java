package com.workintech.s17challenge.entity;

import org.springframework.stereotype.Component;

@Component
public class MediumCourseGpa implements CourseGpa{
    @Override
    public int Gpa() {
        return 5;
    }
}
