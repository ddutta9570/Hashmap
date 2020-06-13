package deepak.packag.parseemailverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEmail,edtUserName, edtPassword;
    private Button btnSignUp,btnLoginSignUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign up");

        edtEmail = findViewById(R.id.edtEmail);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginSignUpActivity = findViewById(R.id.btnLoginSignUpActivity);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        btnLoginSignUpActivity.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:

                if (edtEmail.getText().toString().equals("")||
                        edtPassword.getText().toString().equals("")||
                        edtUserName.getText().toString().equals("")){


                    FancyToast.makeText(this,"Email Password username is required", FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show();

                }else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUserName.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog =new ProgressDialog(this);
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e== null){

                                FancyToast.makeText(MainActivity.this, appUser.getUsername() + "is signed up", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,true).show();

                                Intent intent = new Intent(MainActivity.this,TwitterUser.class);
                                startActivity(intent);


                            }else {

                                FancyToast.makeText(MainActivity.this,e.getMessage(),
                                        FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                            }
                            progressDialog.dismiss();

                        }
                    });
                }

                break;

                case R.id.btnLoginSignUpActivity:
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
        }

    }


}