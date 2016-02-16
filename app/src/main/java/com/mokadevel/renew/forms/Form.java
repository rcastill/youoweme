package com.mokadevel.renew.forms;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Form
{
    private static final String TAG = "DEVLOG:FORM";

    private ArrayList<FormField> fields = new ArrayList<>();
    private HashMap<String, String> data;

    /**
     * Adds a new field to the form. The type of the source object can be any, but if no
     * collector is found, then this will throw {@link InvalidSourceTypeException}.
     *
     * @param name          the name for the field.
     * @param source        the source of the value.
     * @param validators    additional validators to check that value.
     */
    public void addField(String name, Object source, Validator... validators)
    {
        FormField field = new FormField(name, source);
        field.addAllValidators(validators);

        fields.add(field);
    }

    /**
     * Collects every value across all fields, without validating them.
     */
    @SuppressWarnings("unchecked")
    public void collectAll()
    {
        data = new HashMap<>();

        for (FormField field : fields) {
            data.put(field.getName(), field.getValue());
        }
    }

    /**
     * Validates the entire form. As a cascading effect, this also collect every error in
     * every field in the form.
     *
     * All previous errors are flushed, so this effectively restart the state machine to
     * the point of collection.
     *
     * Note: this does not collect any value, so for a process of trying and error, you
     * should always call the {@link #collectAll()} method before this every time.
     *
     * @return  true if this form is valid.
     */
    public boolean isValid()
    {
        if (data == null) {
            throw new InvalidStateException("collectAll should be called first.");
        }

        boolean success = true;

        for (FormField field : fields) {
            String value = data.get(field.getName());

            if (value == null || !field.isValid(value)) {
                success = false;
            }
        }

        return success;
    }

    /**
     * Gets the value of a field after collecting them. The validation process
     * is not needed at this stage.
     *
     * If no collecting is found, this will throw an
     * {@link InvalidStateException}.
     *
     * @param name      the name of the field.
     * @return          the value of the field.
     */
    public String get(String name)
    {
        if (data == null) {
            throw new InvalidStateException("collectAll should be called first.");
        }

        return data.get(name);
    }

    /**
     * Retrieves all error messages collected after calling the {@link #isValid}
     * method.
     *
     * Error messages are formatted in a very strict way, that should change
     * but this method will always return the same type of value.
     *
     * If no validation process has been done, then this will throw a
     * {@link InvalidStateException}.
     *
     * @return  the list of the error messages across all fields and validators.
     */
    public ArrayList<String> getErrors()
    {
        ArrayList<String> errors = new ArrayList<>();

        for (FormField field : fields) {
            ArrayList<String> fieldErrors = new ArrayList<>();
            ArrayList<String> rawErrors = field.getErrors();

            if (rawErrors == null) {
                throw new InvalidStateException("Form has not been validated.");
            }

            for (String error : rawErrors) {
                fieldErrors.add("Field " + field.getName() + " " + error);
            }

            errors.addAll(fieldErrors);
        }

        return errors;
    }

    /**
     * Acts as a bundle for a field, containing it's name, the source that holds
     * it's value, and validators for that value.
     */
    private static class FormField
    {
        private static final String TAG = "DEVLOG:FORM_FIELD";

        // collectors for each type of form input.
        private static HashMap<Class<?>, Collector> collectors = new HashMap<>();

        static {
            collectors.put(TextView.class, Collector.textView);
            collectors.put(String.class, Collector.simple);
        }

        private ArrayList<Validator> validators = new ArrayList<>();
        private ArrayList<String> errors;

        private String name;
        private Object source;

        public FormField(String name, Object source)
        {
            this.name = name;
            this.source = source;
        }

        public void addAllValidators(Validator[] validators)
        {
            Collections.addAll(this.validators, validators);
        }

        public String getName()
        {
            return name;
        }

        public Object getSource()
        {
            return source;
        }

        @SuppressWarnings("unchecked")
        public String getValue()
        {
            Collector collector = collectors.get(source.getClass());

            if (collector == null) {
                throw new InvalidSourceTypeException("No collector for the type "
                        + source.getClass().getCanonicalName());
            }

            return collector.collect(source);
        }

        public boolean isValid(String value)
        {
            errors = new ArrayList<>();

            boolean success = true;

            for (Validator validator : validators) {
                if (!validator.isValid(value)) {
                    errors.add(validator.getErrorMessage());
                    success = false;
                }
            }

            return success;
        }

        public ArrayList<String> getErrors()
        {
            return errors;
        }
    }
}
