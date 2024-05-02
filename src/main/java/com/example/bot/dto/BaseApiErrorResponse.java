package com.example.bot.dto;

import org.springframework.http.ResponseEntity;

public record BaseApiErrorResponse (
   String errorMessage
){}
