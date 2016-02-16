package com.mokadevel.renew;

import com.mokadevel.renew.forms.Form;
import com.mokadevel.renew.forms.InvalidStateException;
import com.mokadevel.renew.forms.Validator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormUnitTest
{
    @Test
    public void testCollectAll()
    {
        Form form = new Form();
        form.addField("key1", "value1");
        form.addField("key2", "value2");
        form.collectAll();

        assertEquals("value1", form.get("key1"));
        assertEquals("value2", form.get("key2"));
    }

    @Test
    public void testIsValid_true()
    {
        Form form = new Form();
        form.addField("key", "value", Validator.notEmpty);
        form.collectAll();

        assertTrue(form.isValid());
    }

    @Test
    public void testIsValid_false()
    {
        Form form = new Form();
        form.addField("key", "", Validator.notEmpty);
        form.collectAll();

        assertFalse(form.isValid());
    }

    @Test(expected = InvalidStateException.class)
    public void testIsValidExpectedFail()
    {
        Form form = new Form();
        form.addField("key", "", Validator.notEmpty);

        // here we try to check if the form is valid without collecting it first.
        form.isValid();
    }
}
