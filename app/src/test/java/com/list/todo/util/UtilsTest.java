package com.list.todo.util;

import android.content.Context;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.test.platform.app.InstrumentationRegistry;

import com.list.todo.R;
import com.list.todo.enums.TodoStatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PrepareForTest(TextUtils.class)
public class UtilsTest {

    private Context context;
    private String other = "other";

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Integer.class);
    }

    @Test(expected=Exception.class)
    public void getTodoStatusColor() {
        //RuntimeEnvironment.systemContext
        assertEquals(Utils.getTodoStatusColor(context, TodoStatus.COMPLETED.name()), ContextCompat.getColor(context, R.color.status_completed));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.COMPLETED.name()), ContextCompat.getColor(context, R.color.status_not));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.COMPLETED.name()), ContextCompat.getColor(context, R.color.status_expired));
        assertEquals(Utils.getTodoStatusColor(context, TodoStatus.NOT.name()), ContextCompat.getColor(context, R.color.status_not));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.NOT.name()), ContextCompat.getColor(context, R.color.status_completed));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.NOT.name()), ContextCompat.getColor(context, R.color.status_expired));
        assertEquals(Utils.getTodoStatusColor(context, TodoStatus.EXPIRED.name()), ContextCompat.getColor(context, R.color.status_expired));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.EXPIRED.name()), ContextCompat.getColor(context, R.color.status_completed));
        assertNotEquals(Utils.getTodoStatusColor(context, TodoStatus.EXPIRED.name()), ContextCompat.getColor(context, R.color.status_not));

        try {
            assertEquals(Utils.getTodoStatusColor(context, other), ContextCompat.getColor(context, R.color.status_expired));
            fail("not found TodoStatus = " + other);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }

        try {
            assertEquals(Utils.getTodoStatusColor(context, other), ContextCompat.getColor(context, R.color.status_not));
            fail("not found TodoStatus = " + other);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }

        try {
            assertEquals(Utils.getTodoStatusColor(context, other), ContextCompat.getColor(context, R.color.status_completed));
            fail("not found TodoStatus = " + other);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }


    }

    @Test
    public void isValidEmail() {
        assertTrue(Utils.isValidEmail("saf@fsakj.com"));
        assertFalse(Utils.isValidEmail("dsa@dsa@.com"));
        assertFalse(Utils.isValidEmail("adsaf@fasf..com"));
        assertFalse(Utils.isValidEmail("fmasnf.com"));
        assertFalse(Utils.isValidEmail("kdhe@kjfsa"));
        assertFalse(Utils.isValidEmail("@fsa.com"));
        assertFalse(Utils.isValidEmail(".com"));
        assertFalse(Utils.isValidEmail(".kjde.com"));
        assertFalse(Utils.isValidEmail("test.com@test"));

    }


}