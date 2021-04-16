package com.example.therapybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private static String TAG = "HomeActivity";

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

}