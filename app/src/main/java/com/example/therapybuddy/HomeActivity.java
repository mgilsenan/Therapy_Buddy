package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private static String TAG = "HomeActivity";

    // local user
    public static User user ;

    // UI
    CardView moodLogCardView, worksheetsCardView, statisticsCardView, contactCardView;

    // firebase
    FirebaseAuth fAuth;

    protected void setUp(){
        //Hooks
        moodLogCardView = findViewById(R.id.moodLogCardView);
        worksheetsCardView = findViewById(R.id.worksheetsCardView);
        statisticsCardView = findViewById(R.id.statisticsCardView);
        contactCardView = findViewById(R.id.contactCardView);

        user = new User();
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final Intent[] intent = {null};
        switch(item.getItemId()){
            case R.id.logout:
                Toast.makeText(this, "Logged out from account", Toast.LENGTH_LONG).show();
                final String phone = LoginActivity.getUser().getPhone();
                FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(phone).exists() ) {
                            FirebaseDatabase.getInstance().getReference("user").child(phone).child("loginBefore").setValue("FALSE");

                            LoginActivity.getUser().setPhone("");
                            LoginActivity.getUser().setName("");
                            LoginActivity.getUser().setEmail("");
                            LoginActivity.clearUser();

                            SharedPreferences pref = getSharedPreferences(LoginActivity.myPreference, Context.MODE_PRIVATE);

                            intent[0] = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent[0]);
                            finish();
                        }else{
                            Toast.makeText(HomeActivity.
                                            this, "Error has occured",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void btn_moodleLog(View view) {
        startActivity(new Intent(getApplicationContext(),MoodLogActivity.class));
    }
    public void worksheetsButtonAction(View view){
        startActivity(new Intent(getApplicationContext(),WorkSheetsActivity.class));
    }

    public void statsButtonAction(View view){
        startActivity(new Intent(getApplicationContext(),StatisticsActivity.class));

    }
    // Action for clicking the worksheets cardview
    protected void worksheetsCardViewAction(){
        worksheetsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HomeActivity.this, WorkSheetsActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this);
                    startActivity(intent);
                }
            }
        });
    }
}