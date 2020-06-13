package deepak.packag.parseemailverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TwitterUser extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList <String> tUser;
    private ArrayAdapter arrayAdapter;
    private String  followedUsers = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_user);

        FancyToast.makeText(this,"Welcome" + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

        listView = findViewById(R.id.listView);
        tUser = new ArrayList<>();
      arrayAdapter =new ArrayAdapter(TwitterUser.this,android.R.layout.simple_list_item_checked,tUser);
      listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
      listView.setOnItemClickListener(this);





        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                    if (objects.size()>0 && e == null ){

                        for (ParseUser twitterUsers : objects)  {

                            tUser.add(twitterUsers.getUsername());

                        }

                        listView.setAdapter(arrayAdapter);

                        for (String twitterUser  : tUser) {

                            if (ParseUser.getCurrentUser().getList("fanOf") != null){

                            if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)){


                                followedUsers = followedUsers + twitterUser  + "\n";

                                listView.setItemChecked(tUser.indexOf(twitterUser),true);
                                FancyToast.makeText(TwitterUser.this,ParseUser.getCurrentUser().getUsername() + " is following " + followedUsers,Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                            }


                        }
                        listView.setVisibility(View.VISIBLE);
                    }
                    }


                }

        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout_Item:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        Intent intent = new Intent(TwitterUser.this,MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                });
                break;

                case R.id.sendTweetItem:

                    Intent intent = new Intent(TwitterUser.this,MyTweet.class) ;
                    startActivity(intent);

                    break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()){

            FancyToast.makeText(TwitterUser.this , tUser.get(position) + " is now followed ",Toast.LENGTH_LONG,FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().add("fanOf" , tUser.get(position));
        }else {
            FancyToast.makeText(TwitterUser.this , tUser.get(position) + " is now unfollowed ",Toast.LENGTH_LONG,FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(tUser.get(position));
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf" , currentUserFanOfList);


        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e==null){

                    FancyToast.makeText(TwitterUser.this , "Saved" , Toast.LENGTH_LONG ,FancyToast.SUCCESS,true).show();

                }
            }
        });

    }


}
