package com.example.lmstaskservice.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskStatus {
    PENDING("PENDING"),

    ASSIGNED("ASSIGNED"),

    DONE("DONE");

    private String status;

}
