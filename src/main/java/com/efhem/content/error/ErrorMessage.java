package com.efhem.content.error;

import org.springframework.http.HttpStatus;



public record ErrorMessage (HttpStatus httpStatus, String message){}
