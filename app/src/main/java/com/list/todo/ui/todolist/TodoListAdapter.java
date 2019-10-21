package com.list.todo.ui.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.list.todo.R;
import com.list.todo.RecyclerItemClickListener;
import com.list.todo.model.Todo;
import com.list.todo.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private final Context context;
    private final List<Todo> todoList;
    private RecyclerItemClickListener<Todo> clickListener;

    TodoListAdapter(Context context, List<Todo> todoList, RecyclerItemClickListener<Todo> clickListener) {
        this.context = context;
        this.todoList = todoList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.tvName.setText(todo.getName());
        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(todo));
        holder.itemView.setOnLongClickListener(v -> {
            clickListener.onItemLongClick(todo);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return todoList == null ? 0 : todoList.size();
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(@NonNull View itemView) { super(itemView); }
    }

    void addItem(Todo todo) {
        todoList.add(todo);
        notifyItemInserted(todoList.size() - 1);
    }

    void removeItem(Todo todo) {
        int removeIndex = todoList.indexOf(todo);
        todoList.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }
}