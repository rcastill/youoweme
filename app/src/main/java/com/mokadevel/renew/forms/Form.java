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

        for (FormField field : fields) {
            String value = data.get(field.getName());

            if (!field.isValid(value)) {
                return false;
            }
        }

        return true;
    }

    public String get(String name)
    {
        if (data == null) {
            Log.e(TAG, "get: form fields are not yet collected.");
            return null;
        }

        return data.get(name);
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

        private String name;
        private Object source;
        private ArrayList<Validator> validators;

        public FormField(String name, Object source)
        {
            this.name = name;
            this.source = source;

            validators = new ArrayList<>();
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
            // TODO: support error messages.
            for (Validator validator : validators) {
                if (!validator.isValid(value)) {
                    return false;
                }
            }

            return true;
        }
    }
}
