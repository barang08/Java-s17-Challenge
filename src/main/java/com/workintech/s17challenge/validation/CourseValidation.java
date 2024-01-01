
package com.workintech.s17challenge.validation;

import com.workintech.s17challenge.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CourseValidation {

    public static void chechkName(String name){
        if(name==null || name.isEmpty()){
            throw new ApiException("name can not be null or empty" + name, HttpStatus.BAD_REQUEST);

        }
    }

    public static void chechkCredit(Integer credit) {
        if(credit<0 || credit>4){
            throw new ApiException("Credit should be in 0,4 interval", HttpStatus.BAD_REQUEST);
        }

    }


    public static void chechkId(Integer id) {
        if(id == null || id<0){
            throw new ApiException("id should be greater than zero"+id, HttpStatus.BAD_REQUEST);
        }
    }
}

