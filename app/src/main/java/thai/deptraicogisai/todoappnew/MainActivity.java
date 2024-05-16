package thai.deptraicogisai.todoappnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;

    private ArrayList<Todo> list;

    private BroadcastReceiver airplaneModeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
                if (isAirplaneModeOn) {
                    // Chế độ máy bay đã được bật
                    Toast.makeText(context, "Airplane mode vừa Bật", Toast.LENGTH_SHORT).show();
                } else {
                    // Chế độ máy bay đã được tắt
                    Toast.makeText(context, "Airplane mode vừa Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isNetworkAvailable()) {
            fetchTodo(this);
        } else {
            Toast.makeText(this, "Không có kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeReceiver, intentFilter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void fetchTodo(Context context){
        Call<ArrayList<Todo>> call = RetrofitClient.getApiService().getTodos();

        call.enqueue(new Callback<ArrayList<Todo>>() {
            @Override
            public void onResponse(Call<ArrayList<Todo>> call, Response<ArrayList<Todo>> response) {
              list = response.body();

                todoAdapter = new TodoAdapter(context, list, new TodoAdapter.MyClickListenner() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(MainActivity.this, "" + list.get(position).getUserId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(int position) {
                        showDeleteConfirmationDialog(position, context);
                    }
                });
                recyclerView.setAdapter(todoAdapter);
                Log.d("tag", list.toString());

            }

            @Override
            public void onFailure(Call<ArrayList<Todo>> call, Throwable t) {

            }
        });

    }

    private void showDeleteConfirmationDialog(int position,Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa todo");
        builder.setMessage("Bạn có thực sự muốn xóa todo này không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                todoAdapter.notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("Không", null);
        builder.create().show();
    }
}