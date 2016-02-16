package com.mokadevel.renew.forms;

import android.widget.TextView;

public interface Collector<T>
{
    SimpleCollector simple = new SimpleCollector();
    TextViewCollector textView = new TextViewCollector();

    String collect(T view);

    class SimpleCollector implements Collector<String>
    {
        @Override
        public String collect(String value)
        {
            return value;
        }
    }

    class TextViewCollector implements Collector<TextView>
    {
        @Override
        public String collect(TextView view)
        {
            return view.getText().toString();
        }
    }
}
