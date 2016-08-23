package provab.herdman.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import provab.herdman.R;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class FarmerDetailsLogin  extends AppCompatActivity implements View.OnClickListener  {

    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_details_login);
        loginButton=(Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    public void findViews(View view){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                /*Intent intent=new Intent(FarmerDetailsLogin.this,AnimalMainActivity.class);
                startActivity(intent);*/
                break;
        }
    }
}
