package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

//added category and status variable alongwith getters and setters.
public class ToDoItem {
    private String description;
    private String category;
    private String dueDate;
    private String status;

    public ToDoItem(String description, String category, String dueDate, String status) {
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;
        this.status = status;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
