package com.example.start.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(600,"The program has an uncategorized exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(601, "User has existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(602, "User is not existed!", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(603, "Password must be longer than {min} characters!", HttpStatus.BAD_REQUEST),
    INVALID_AGE(604, "Age must be higher than {min}!", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(605, "Password is wrong!", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(606, "You do not have this permission!", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(607, "Please re-login!", HttpStatus.UNAUTHORIZED),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXIST(1002, "Can't find role USER", HttpStatus.NOT_FOUND),
    INVALID_DOB(1002, "Your age must be between {min} and {max}", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_ERROR(1003, "Upload files failed, please try again.", HttpStatus.BAD_REQUEST),
    // Course
    COURSE_NOT_EXIST(2000, "Course is not exist", HttpStatus.NOT_FOUND),
    // Assignment
    ASSIGNMENT_NOT_EXIST(2100, "Assignment is not exist", HttpStatus.NOT_FOUND),
    // Submission
    SUBMISSION_NOT_EXIST(2200, "Submission is not exist", HttpStatus.NOT_FOUND),
    // Lesson
    LESSON_NOT_EXIST(2200, "Lesson is not exist", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode StatusCode) {
        this.code = code;
        this.message = message;
        this.StatusCode = StatusCode;
    }

    int code;
    String message;
    HttpStatusCode StatusCode;
}
