package thai.deptraicogisai.todoappnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private ArrayList<Todo> todoList;
    private Context context;

    private MyClickListenner myClickListenner;


    public TodoAdapter(Context context,ArrayList<Todo> todoList, MyClickListenner myClickListenner) {
        this.todoList = todoList;
        this.context = context;
        this.myClickListenner = myClickListenner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_activity, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.MyViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.id.setText("" + todo.getId());
        holder.title.setText(todo.getTitle());
        holder.completed.setText(todo.getCompleted());
    }

    @Override
    public int getItemCount() {
        if (todoList != null && todoList.size() > 0)
            return todoList.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, title, completed;

        public MyViewHolder(View view) {
            super(view);

            id = view.findViewById(R.id.idUser);
            title = view.findViewById(R.id.title);
            completed = view.findViewById(R.id.completed);

            itemView.setOnClickListener(v -> {
                //code here
                myClickListenner.onItemClick(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                myClickListenner.onItemLongClick(getAdapterPosition());
                return true;
            });
        }
    }

    public interface MyClickListenner{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
