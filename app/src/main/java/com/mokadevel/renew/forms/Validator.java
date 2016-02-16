package com.mokadevel.renew.forms;

public interface Validator
{
    NotEmptyValidator notEmpty = new NotEmptyValidator();

    boolean isValid(String value);

    class NotEmptyValidator implements Validator
    {
        @Override
        public boolean isValid(String value)
        {
            return !value.isEmpty();
        }
    }
}
