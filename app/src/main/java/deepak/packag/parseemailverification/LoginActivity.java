package deepak.packag.parseemailverification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLogin,btnSignUpLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);

        btnLogin.setOnClickListener(this);
        btnSignUpLoginActivity.setOnClickListener(this);


   }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnLogin:

                if (edtLoginEmail.getText().toString().equals("")||
                        edtLoginPassword.getText().toString().equals("")){

                    FancyToast.makeText(this,"Email Password is required", FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show();
                }else {

                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user !=null && e == null){




                                transitiontoTwitterUsers ();


                            }
                        }
                    });
                }

                return;

                case R.id.btnSignUpLoginActivity:
                    return;
        }

    }

    private void transitiontoTwitterUsers (){

        Intent intent = new Intent(LoginActivity.this,TwitterUser.class);
        startActivity(intent);


    }
}
















