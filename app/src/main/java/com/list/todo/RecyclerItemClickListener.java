package com.list.todo;

public interface RecyclerItemClickListener<T> {
    void onItemClick(T item);
    void onItemLongClick(T item);
}
