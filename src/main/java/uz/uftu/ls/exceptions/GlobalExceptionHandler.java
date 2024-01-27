package uz.uftu.ls.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.uftu.ls.domain.dto.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException userException) {
        String errorMessage = userException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FacultyException.class)
    public ResponseEntity<Object> handleFacultyException(FacultyException facultyException) {
        String errorMessage = facultyException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UniversityException.class)
    public ResponseEntity<Object> handleUniversityException(UniversityException universityException) {
        String errorMessage = universityException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FieldOfStudyException.class)
    public ResponseEntity<Object> handleFieldOfStudyException(FieldOfStudyException fieldOfStudyException) {
        String errorMessage = fieldOfStudyException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ScienceException.class)
    public ResponseEntity<Object> handleScienceException(ScienceException scienceException) {
        String errorMessage = scienceException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
