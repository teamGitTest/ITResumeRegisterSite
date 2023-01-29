package kr.co.itresumeregistersite.global.error.exception;

import kr.co.itresumeregistersite.global.error.enums.ErrorCode;
import kr.co.itresumeregistersite.global.error.exception.user.*;
import kr.co.itresumeregistersite.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j  // logging에 대한 추상 레이어를 제공하는 인터페이스의 모음

        /* logging 사용이유
        1. 스레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있음
        2. 로그 레벨에 따라 개발서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있음
        3. 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있음
        4. 특히 파일로 남길 때에는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능
        5. println을 썼을 때보다 내부 버퍼링, 멀티 스레드 등의 환경에서 훨씬 좋음
         */
@RestControllerAdvice
public class ExceptionAdvice {

    // NotFoundUserException을 지정해서 잡아낸다 (즉, 특정 Exception을 지정해서 잡아낸다)
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> notFoundUserException(NotFoundUserException e) {
        log.error("NotFoundUserException Occurred", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_USER);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedCodeException.class)
    public ResponseEntity<ErrorResponse> duplicatedCodeException(DuplicatedCodeException e) {
        log.error("DuplicatedCodeException Occurred", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.IDENTITY_DUPLICATION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistPasswordException.class)
    public ResponseEntity<ErrorResponse> notExistPasswordException(NotExistPasswordException e) {
        log.error("NotExistPasswordException Occurred", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PASSWORD_INPUT_INVALID);
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotExistIdentityException.class)
    public ResponseEntity<ErrorResponse> notExistIdentityException(NotExistIdentityException e) {
        log.error("NotExistIdentityException Occurred", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.IDENTITY_INPUT_INVALID);
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> invalidParameterException(InvalidParameterException e) {
        log.error("InvalidParameterException Occurred", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PARAMETER_INPUT_INVALID);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}