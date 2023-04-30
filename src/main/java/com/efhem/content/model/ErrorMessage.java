package com.efhem.content.model;

import org.springframework.http.HttpStatus;



public record ErrorMessage (HttpStatus httpStatus, String message){}
