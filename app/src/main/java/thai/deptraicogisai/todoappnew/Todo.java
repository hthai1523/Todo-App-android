package thai.deptraicogisai.todoappnew;

import com.google.gson.annotations.SerializedName;

public class Todo {
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("id")

    private Integer id;
    @SerializedName("title")

    private String title;
    @SerializedName("completed")

    private String completed;

    public Todo(int userId, int id, String title, String completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
