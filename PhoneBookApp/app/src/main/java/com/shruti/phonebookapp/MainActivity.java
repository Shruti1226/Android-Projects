package com.shruti.phonebookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shruti.phonebookapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ArrayList<User> users;
    private RecyclerView recyclerView;
    private MyAdapter usersAdapter;
    private ActivityMainBinding activityMainBinding;

//    firebase
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        activityMainBinding= DataBindingUtil.setContentView(
                this, R.layout.activity_main
        );

//        recyclerview with databinding
          recyclerView = activityMainBinding.recyclerView;
          usersAdapter=new MyAdapter(this, users);
          recyclerView.setAdapter(usersAdapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(this));

//          Fetch the data from firebase into RecyclerView
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    User user=dataSnapshot.getValue(User.class);
                    users.add(user);
                }
                //          notify an adapter associated with a recyclerView
//        that the underlying dataset has changed
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }
}