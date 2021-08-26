package tw.com.watsonfan.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class BusStation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station);
        Bundle bundle = getIntent().getExtras();
        String Rid = bundle.getString("RID");
        int goBack = bundle.getInt("goBack");
        Toast.makeText(getApplicationContext(),Rid,Toast.LENGTH_SHORT).show();


    }
}