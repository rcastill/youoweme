package com.mokadevel.renew.forms;

import java.util.Locale;

/**
 * Validator's abstract class and compilation of commonly used validators.
 *
 * @author mijara
 */
public abstract class Validator
{
    // - Static validators.
    public static final NotEmptyValidator notEmpty = new NotEmptyValidator();

    // - Special validators factory methods.
    public static Validator maximumLength(int length)
    {
        return new MaximumLengthValidator(length);
    }

    public static Validator minimumLength(int length)
    {
        return new MinimumLengthValidator(length);
    }

    /**
     * Checks if the given value is valid according to certain constraint.
     *
     * This should not check if the value is null, since that just adds not
     * useful code for a very simple problem that can be checked in the form
     * itself.
     *
     * @param value     the value to validate.
     * @return          true if it's valid.
     */
    public abstract boolean isValid(String value);

    /**
     * Gets the validator error message in case of failure.
     *
     * The error message should be written in a way that we can concatenate
     * the field name, as in:
     *
     * The field "name" should not be empty.
     *
     * There, the message would be: should not be empty.
     *
     * @return  an error message.
     */
    public abstract String getErrorMessage();

    /**
     * Validates that the value has at most the given length.
     */
    private static class MaximumLengthValidator extends Validator
    {
        private int length;

        public MaximumLengthValidator(int length)
        {
            this.length = length;
        }

        @Override
        public boolean isValid(String value)
        {
            return value.length() <= length;
        }

        @Override
        public String getErrorMessage()
        {
            return "should have at most a length of " + length + ".";
        }
    }

    /**
     * Validates that the value has at least the given length.
     */
    private static class MinimumLengthValidator extends Validator
    {
        private int length;

        public MinimumLengthValidator(int length)
        {
            this.length = length;
        }

        @Override
        public boolean isValid(String value)
        {
            return value.length() >= length;
        }

        @Override
        public String getErrorMessage()
        {
            return "should have at least a length of " + length + ".";
        }
    }

    /**
     * Validates that the value given is not empty.
     */
    private static class NotEmptyValidator extends Validator
    {
        @Override
        public boolean isValid(String value)
        {
            return !value.isEmpty();
        }

        @Override
        public String getErrorMessage()
        {
            return "should not be empty.";
        }
    }
}
