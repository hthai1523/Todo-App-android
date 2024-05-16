package thai.deptraicogisai.todoappnew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
            if(isAirplaneModeOn) {
                Toast.makeText(context, "Airplane mode vừa bật", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Airplane mode vừa tắt", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
