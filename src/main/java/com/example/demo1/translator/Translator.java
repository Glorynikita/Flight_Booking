package com.example.demo1.translator;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String toLocale(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

}
