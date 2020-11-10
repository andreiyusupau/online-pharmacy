package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.CreditCardDto;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.service.creditcard.CreditCardService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/creditcards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public Collection<CreditCard> getAll() {
        return creditCardService.getAll();
    }

    @PostMapping
    public long add(@RequestBody @Valid CreditCardDto creditCardDto) {
        return creditCardService.add(creditCardDto);
    }

    @GetMapping("/{id}")
    public CreditCard get(@PathVariable Long id) {
        return creditCardService.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        creditCardService.remove(id);
    }

}