package com.skrzypczyk.meetings.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

@Service
public class TokenGenerator {
    public static String generateToken() {
        return RandomString.make(50);
    }
}
