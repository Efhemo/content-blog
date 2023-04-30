package com.efhem.demo.model;

import org.springframework.http.HttpStatus;



public record ErrorMessage (HttpStatus httpStatus, String message){}
