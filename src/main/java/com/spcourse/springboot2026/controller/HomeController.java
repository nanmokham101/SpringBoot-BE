package com.spcourse.springboot2026.controller;

import com.spcourse.springboot2026.dto.NumberRequestDTO;
import com.spcourse.springboot2026.service.ArithmeticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
@Slf4j
public class HomeController {
    @Autowired
    ArithmeticService arithmeticService; // DI
    @PostMapping("/add")
    public NumberRequestDTO addNumber(@RequestBody NumberRequestDTO numberRequestDTO){
        log.info("num1 : "+ numberRequestDTO.getNum1());
        log.info("num2 : "+ numberRequestDTO.getNum2());
        double result = arithmeticService.add(numberRequestDTO.getNum1(), numberRequestDTO.getNum2());

        NumberRequestDTO numberRequestDTOResult = new NumberRequestDTO();
        numberRequestDTOResult.setNum1(numberRequestDTO.getNum1());
        numberRequestDTOResult.setNum2(numberRequestDTO.getNum2());
        numberRequestDTOResult.setResult(result);

        return numberRequestDTOResult;
    }
//    @PostMapping("/subtract")
//    @PostMapping("/multi")
//    @PostMapping("/divided")
    //code for me now. during 5 min
}
