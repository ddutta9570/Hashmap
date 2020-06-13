package deepak.packag.parseemailverification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MyTweet extends AppCompatActivity implements View.OnClickListener{

    private EditText edtTweet;
    private Button btnViewTweet;
    private ListView viewTweetListView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);

        edtTweet = findViewById(R.id.edtSendTweet);
        btnViewTweet=findViewById(R.id.btnViewTweet);
        viewTweetListView = findViewById(R.id.viewTweetListView);
        btnViewTweet.setOnClickListener(this);




    }
   public void sendTweetIsTpped(View btnSendTweet){

       ParseObject parseObject = new ParseObject("MyTweet");
       parseObject.put("tweet" , edtTweet.getText().toString());
       parseObject.put("user" , ParseUser.getCurrentUser().getUsername());

       final ProgressDialog progressDialog = new ProgressDialog(this);
       progressDialog.setMessage("Loading");
       progressDialog.show();

       parseObject.saveInBackground(new SaveCallback() {
           @Override
           public void done(ParseException e) {

               if (e==null){

                   FancyToast.makeText(MyTweet.this,ParseUser.getCurrentUser().getUsername() + "'s tweet" +
                           " ( " + edtTweet.getText().toString() + " ) " + " is saved " , Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();


               }else {

                   FancyToast.makeText(MyTweet.this,e.getMessage() , Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

               }

               progressDialog.dismiss();



           }
       });




   }

    @Override
    public void onClick(View v) {
        
    }
}