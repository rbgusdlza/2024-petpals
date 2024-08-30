package rbgusdlza.petpals.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import rbgusdlza.petpals.web.error.PetPalsException;

@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(PetPalsException.class)
    public ModelAndView handlePetPalsException(PetPalsException exception) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorCode", exception.getErrorCode());
        modelAndView.addObject("errorMessage", exception.getErrorCode().getDescription());
        return modelAndView;
    }
}
