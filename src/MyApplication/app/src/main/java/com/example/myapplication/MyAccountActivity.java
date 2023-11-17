package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/**Show the info of the login user
 * @author Xuejie Dong u7602840
 * */
public class MyAccountActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        Button btnPersonalInfo = findViewById(R.id.btnPersonalInfo);
        Button btnAccountSecurity = findViewById(R.id.btnAccountSecurity);
        Button btnNotification = findViewById(R.id.btnNotification);
        Button btnGeneralSettings = findViewById(R.id.btnGeneralSettings);

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnAccountSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "Account and Security", Toast.LENGTH_SHORT).show();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "Message notification", Toast.LENGTH_SHORT).show();
            }
        });

        btnGeneralSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "General settings", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
