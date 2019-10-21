package com.list.todo.util;

import android.content.Context;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.core.util.PatternsCompat;

import com.list.todo.R;
import com.list.todo.enums.TodoStatus;

public class Utils {

    public static int getTodoStatusColor(Context context, String todoStatus) {
        int resourceId;
        switch (Enum.valueOf(TodoStatus.class, todoStatus)) {
            case NOT:
                resourceId = R.color.status_not;
                break;
            case EXPIRED:
                resourceId = R.color.status_expired;
                break;
            case COMPLETED:
                resourceId = R.color.status_completed;
                break;
            default:
                throw new IllegalStateException("not found TodoStatus = " + todoStatus);
        }
        return ContextCompat.getColor(context, resourceId);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return PatternsCompat.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
