package com.mokadevel.renew.forms;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Form
{
    private static final String TAG = "DEVLOG:FORM";

    private ArrayList<FormField> fields = new ArrayList<>();
    private HashMap<String, String> data;

    public void addField(String name, Object source, Validator... validators)
    {
        FormField field = new FormField(name, source);
        field.addAllValidators(validators);

        fields.add(field);
    }

    @SuppressWarnings("unchecked")
    public void collectAll()
    {
        data = new HashMap<>();

        for (FormField field : fields) {
            data.put(field.getName(), field.getValue());
        }
    }

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

    public String get(String name)
    {
        if (data == null) {
            Log.e(TAG, "get: form fields are not yet collected.");
            return null;
        }

        return data.get(name);
    }

    public ArrayList<String> getErrors()
    {
        ArrayList<String> errors = new ArrayList<>();

        for (FormField field : fields) {
            ArrayList<String> fieldErrors = new ArrayList<>();

            for (String error : field.getErrors()) {
                fieldErrors.add("Field " + field.getName() + " " + error);
            }

            errors.addAll(fieldErrors);
        }

        return errors;
    }

    /**
     * Acts as a bundle for a field, containing it's name, the source that holds it's value,
     * and validators for that value.
     */
    private static class FormField
    {
        private static final String TAG = "DEVLOG:FORM_FIELD";

        // collectors for each type of form input.
        // TODO: complete.
        private static HashMap<Class<?>, Collector> collectors = new HashMap<>();
        static {
            collectors.put(TextView.class, Collector.textView);
            collectors.put(String.class, Collector.simple);
        }

        private ArrayList<Validator> validators = new ArrayList<>();
        private ArrayList<String> errors = new ArrayList<>();

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

            if (collector != null) {
                return collector.collect(source);
            }

            Log.e(TAG, "getValue: Value can't be collected from source.");

            return null;
        }

        public boolean isValid(String value)
        {
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
