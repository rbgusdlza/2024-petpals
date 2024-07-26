package rbgusdlza.petpals.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import rbgusdlza.petpals.web.error.PetPalsException;

@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(PetPalsException.class)
    public ModelAndView handlePetPalsException(PetPalsException exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        model.addAttribute("errorCode", exception.getErrorCode());
        model.addAttribute("errorMessage", exception.getErrorCode().getDescription());
        return modelAndView;
    }
}
