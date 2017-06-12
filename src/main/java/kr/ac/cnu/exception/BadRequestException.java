package kr.ac.cnu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rokim on 2017. 6. 12..
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Request")
public class BadRequestException extends RuntimeException {
}
