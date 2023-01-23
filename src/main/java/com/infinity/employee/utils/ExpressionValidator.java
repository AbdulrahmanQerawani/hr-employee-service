package com.infinity.employee.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {

    public static final String EMAIL_REGEX= "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String NAME_REGEX="^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

    public  static Boolean isValid(String value, String regExp){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();

    }
}
