package com.spcourse.springboot2026.service.impl;

import com.spcourse.springboot2026.service.ArithmeticService;
import org.springframework.stereotype.Service;

@Service
public class ArithmeticServiceImpl implements ArithmeticService {

    @Override
    public double add(double num1, double num2) {
        return num1 + num2;
    }

    @Override
    public double subtract(double num1, double num2) {
        return num1 - num2;
    }

    @Override
    public double multi(double num1, double num2) {
        return num1 * num2;
    }

    @Override
    public double divided(double num1, double num2) {
        return num1 / num2;
    }
}
