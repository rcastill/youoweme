package com.mokadevel.renew.forms;

import android.widget.TextView;

public interface Collector<T>
{
    String collect(T view);

    class TextViewCollector implements Collector<TextView>
    {
        @Override
        public String collect(TextView view)
        {
            return view.getText().toString();
        }
    }
}
