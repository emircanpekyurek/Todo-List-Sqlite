package com.list.todo.ui.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.list.todo.R;
import com.list.todo.ui.base.BaseDialog;
import com.list.todo.util.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTodoItemDialog extends BaseDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_add_todo_item;
    }

    @BindView(R.id.et_todo_name)
    EditText etTodoName;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private AddTodoItemListener listener;

    public static void showDialog(Context context, AddTodoItemListener listener) {
        new AddTodoItemDialog(context, listener).show();
    }

    private AddTodoItemDialog(@NonNull Context context,AddTodoItemListener listener) {
        super(context);
        this.listener = listener;
    }

    @OnClick(R.id.ll_select_date_container)
    void selectDate() {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, day) -> {
            String dateString = DateUtils.getDateString(day, month, year);
            tvDate.setText(dateString);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_add)
    void addTodo() {
        String name = etTodoName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), getContext().getString(R.string.fill_in_all_blanks), Toast.LENGTH_SHORT).show();
            return;
        }
        String dateString = tvDate.getText().toString().trim();
        try {
            Date date = DateUtils.stringToDate(dateString);
            listener.addTodoItem(name, date);
            dismiss();
        } catch (ParseException e) {
            Toast.makeText(getContext(), getContext().getString(R.string.select_an_appropriate_date), Toast.LENGTH_SHORT).show();
        }
    }

    public interface AddTodoItemListener {
        void addTodoItem(String name, Date date);
    }
}
