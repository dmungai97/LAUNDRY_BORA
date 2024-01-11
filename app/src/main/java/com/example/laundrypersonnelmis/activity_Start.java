package com.example.laundrypersonnelmis;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_Start extends AppCompatActivity {
    Button btnregister ,clientbtnlogin,servicebtnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnregister=(Button) findViewById(R.id.registerActivityBtn);
        clientbtnlogin=(Button)findViewById(R.id.loginClientActivityBtn) ;
        servicebtnlogin=(Button)findViewById(R.id.loginServiceActivityBtn) ;

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();

            }


        });
        clientbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openclientlogin();
            }
        });
        servicebtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openservicelogin();
            }
        });


    }
    public void openclientlogin() {
        Intent intent = new Intent(this, clientslogin.class);
        startActivity(intent);

    }
    public void openservicelogin(){
        Intent service =new Intent(this, servicemenlogin.class);
        startActivity(service);

    }
    public void openNewActivity(){
        Intent register = new Intent(this, MainActivity.class);
        startActivity(register);

    }
}


