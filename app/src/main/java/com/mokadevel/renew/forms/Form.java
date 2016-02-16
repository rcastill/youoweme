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

    // collectors for each type of form input.
    // TODO: complete.
    private static HashMap<Class<?>, Collector> collectors = new HashMap<>();
    static {
        collectors.put(TextView.class, new Collector.TextViewCollector());
    }

    private ArrayList<FormField> fields = new ArrayList<>();
    private HashMap<String, String> data;

    public void addField(String name, View view, Validator... validators)
    {
        FormField field = new FormField(name, view);
        field.addAllValidators(validators);

        fields.add(field);
    }

    @SuppressWarnings("unchecked")
    public void collectAll()
    {
        data = new HashMap<>();

        for (FormField field : fields) {
            View view = field.getView();
            Collector collector = collectors.get(view.getClass());
            String value = collector.collect(view);

            data.put(field.getName(), value);
        }
    }

    public boolean isValid()
    {
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
     * Acts as a bundle for a field, containing it's name, the view that holds it's value,
     * and validators for that value.
     */
    private class FormField
    {
        private String name;
        private View view;
        private ArrayList<Validator> validators;

        public FormField(String name, View view)
        {
            this.name = name;
            this.view = view;

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

        public View getView()
        {
            return view;
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
