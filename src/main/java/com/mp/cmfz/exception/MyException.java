package com.mp.cmfz.exception;

import com.mp.cmfz.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {

    public ExceptionEnum exceptionEnum;
}
