package com.workintech.s17challenge.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class LowCourseGpa implements CourseGpa{
    @Override
    public int Gpa() {
        return 3;
    }
}
