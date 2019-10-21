package com.list.todo.ui.todoitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.list.todo.R;
import com.list.todo.RecyclerItemClickListener;
import com.list.todo.enums.TodoStatus;
import com.list.todo.model.TodoItem;
import com.list.todo.ui.base.BaseViewHolder;
import com.list.todo.util.DateUtils;
import com.list.todo.util.Utils;

import java.util.List;

import butterknife.BindView;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private final Context context;
    private List<TodoItem> todoItemList;
    private RecyclerItemClickListener<TodoItem> clickListener;

    public TodoItemAdapter(Context context, RecyclerItemClickListener<TodoItem> clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem item = todoItemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDate.setText(DateUtils.dateToString(item.getDate()));
        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(item));
        holder.itemView.setOnLongClickListener(v -> {
            clickListener.onItemLongClick(item);
            return true;
        });
        holder.mcvContainer.setStrokeColor(Utils.getTodoStatusColor(context, item.getStatus()));
    }

    @Override
    public int getItemCount() {
        return todoItemList == null ? 0 : todoItemList.size();
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.mcv_container)
        MaterialCardView mcvContainer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    void setItems(List<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
        notifyDataSetChanged();
    }

    void addItem(TodoItem todoItem) {
        todoItemList.add(todoItem);
        notifyItemInserted(todoItemList.size() - 1);
    }

    void removeItem(TodoItem todoItem) {
        notifyItemRemoved(todoItemList.indexOf(todoItem));
        todoItemList.remove(todoItem);
    }

    void updateItem(TodoItem oldItem, TodoItem newItem) {
        int index = todoItemList.indexOf(oldItem);
        todoItemList.set(index, newItem);
        notifyItemChanged(index);
    }
}