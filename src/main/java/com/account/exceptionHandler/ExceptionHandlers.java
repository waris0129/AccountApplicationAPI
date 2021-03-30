package com.account.exceptionHandler;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlers {


    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ResponseWrapper> companyNotFoundException(CompanyNotFoundException companyNotFoundException){
        String message = companyNotFoundException.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.builder().code(HttpStatus.NOT_FOUND.value()).success(false).message(message).build());
    }



    @ExceptionHandler(AccountingApplicationException.class)
    public ResponseEntity<ResponseWrapper> accountingApplicationException(AccountingApplicationException accountingApplicationException){
        String message = accountingApplicationException.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.builder().code(HttpStatus.BAD_REQUEST.value()).success(false).message(message).build());
    }


    @ExceptionHandler(UserNotFoundInSystem.class)
    public ResponseEntity<ResponseWrapper> accountingApplicationException(UserNotFoundInSystem userNotFoundInSystem){
        String message = userNotFoundInSystem.getMessage();
        if(message.equals(""))
            message = "User not found in system.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.builder().code(HttpStatus.BAD_REQUEST.value()).success(false).message(message).build());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseWrapper> invalidTokenException(InvalidTokenException invalidTokenException){
        String message = invalidTokenException.getMessage();

        return new ResponseEntity<>(ResponseWrapper.builder().success(false).code(HttpStatus.UNAUTHORIZED.value()).message(message).build(),HttpStatus.UNAUTHORIZED);
    }


}
