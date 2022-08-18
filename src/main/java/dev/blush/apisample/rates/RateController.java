package dev.blush.apisample.rates;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rates")
public class RateController {
    private final RatesService ratesService;

    public RateController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @GetMapping
    public ResponseEntity<Rates> findAll() {
        Rates rates = new Rates(ratesService.findAll());
        return ResponseEntity.ok().body(rates);
    }

    @PutMapping(consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<Rates> createList(@Valid @RequestBody Rates rates) {
        Rates created = ratesService.create(rates);
        return ResponseEntity.ok().body(created);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }
}
