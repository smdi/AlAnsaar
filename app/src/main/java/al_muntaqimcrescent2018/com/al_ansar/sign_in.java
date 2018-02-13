package al_muntaqimcrescent2018.com.al_ansar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class sign_in extends AppCompatActivity {

    ArrayList<String> emailA ,userNameA;

    ArrayAdapter<String > arrayAdapter;

    AutoCompleteTextView emailAc,passwordAc,ac2UserName;

    Button get,signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        getSupportActionBar().hide();

         signin = (Button) findViewById(R.id.sign_in);
         signup = (Button) findViewById(R.id.sign_up);


         signin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Toast.makeText(getApplicationContext() ,"sign in",Toast.LENGTH_SHORT).show();

             }
         });

         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(),Sign_Up.class);
                 startActivity(intent);
             }
         });


        passwordAc= (AutoCompleteTextView)findViewById(R.id.password);

        emailA = new ArrayList<String >();
        userNameA = new ArrayList<String>();
        get = (Button) findViewById(R.id.login);



        displayDataInfo();


        int i =1 ;

        if(i==1)
        {
            i=1;
            arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,emailA);
            final AutoCompleteTextView actvGetEmail= (AutoCompleteTextView)findViewById(R.id.email);
            actvGetEmail.setThreshold(1);
            actvGetEmail.setAdapter(arrayAdapter);
            actvGetEmail.setTextColor(Color.BLACK);
            emailAc = actvGetEmail;
        }
//        if(i==2)
//        {
//            i=1;
//            arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,userNameA);
//            final AutoCompleteTextView actvGetFullname= (AutoCompleteTextView)findViewById(R.id.FullText);
//            actvGetFullname.setThreshold(1);//will start working from first character
//            actvGetFullname.setAdapter(arrayAdapter);//setting the adapter data into the AutoCompleteTextView
//            actvGetFullname.setTextColor(Color.BLACK);
//            ac2UserName = actvGetFullname;
//        }


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailAc.getText().toString().trim();
                String password = passwordAc.getText().toString().trim();

                boolean check  =   emailChecker(email);

                SharedPreferences.Editor editor = getSharedPreferences("Email",MODE_PRIVATE).edit();
                editor.putString("email",email);
                Toast.makeText(getApplicationContext() ,""+email,Toast.LENGTH_SHORT).show();

                editor.apply();

                // check email and password here

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);


                Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void finish() {
        super.finish();

        finish();
    }

    private void displayDataInfo() {

        SharedPreferences getShare = getSharedPreferences("Email",MODE_PRIVATE);
        String mail = getShare.getString("email","example@gmail.com");
        Toast.makeText(getApplicationContext() ,""+mail,Toast.LENGTH_SHORT).show();
        emailA.add(""+mail+"");

    }

    private boolean emailChecker(String email) {


        return  true;
    }
}
