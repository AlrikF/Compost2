package com.example.dataauth2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Account_activity extends AppCompatActivity {

    private Button Firebasebtn;
    private Button button_grey;
    private DatabaseReference mdatabase;
    private EditText Namefield;
    private EditText Emailfield;
    private ListView User_view;
    private ArrayList<String > User_arraylist=new ArrayList<>();
    private ArrayList<String> mKeys=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity);

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users");//Connecting to  firebase



        Firebasebtn =(Button) findViewById(R.id.firebase_button);
        Namefield=(EditText)findViewById(R.id.Namefield);
        Emailfield=(EditText)findViewById(R.id.Email_field);
        User_view=(ListView)findViewById(R.id.user_list);

        button_grey=(Button)findViewById(R.id.buttongrey);



//        // For handling nested Firebase
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                User_Info user_info = dataSnapshot.getValue(User_Info.class);
//
//                Log.d("TAG","Name: "+user_info.getName() );
//                Log.d("TAG","Email :"+ user_info.getEmail() );
//                Iterator it = user_info.getSite_name().entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry pair = (Map.Entry)it.next();
//                    Log.d("TAG","grade: "+pair.getKey() +  " = "  + pair.getValue());
//                    it.remove(); // avoids a ConcurrentModificationException
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };



//        mdatabase.addListenerForSingleValueEvent(valueEventListener);





        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,User_arraylist);
        User_view.setAdapter(arrayAdapter); // setting adapter for listview

        mdatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

                String value=dataSnapshot.getValue(String.class);
                //Log.d("","Value:"+value);
                System.out.println(value);
                User_arraylist.add(value);

                String key =dataSnapshot.getKey();
                //Log.d("","KEY"+key);
                System.out.println(key);
                mKeys.add(key);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String Value=dataSnapshot.getValue(String.class);
                String key =dataSnapshot.getKey();

                int index=mKeys.indexOf(key);
                User_arraylist.set(index,Value);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Firebasebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String name=Namefield.getText().toString().trim();
                String email=Emailfield.getText().toString().trim();

                HashMap<String,String> dataMap= new HashMap<String, String>();
                dataMap.put("Name",name);
                dataMap.put("Email",email);


                mdatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if(task.isSuccessful())
                        {

                            Toast.makeText(Account_activity.this,"Stored",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Account_activity.this,"Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

        button_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_activity.this,greydata.class));
            }
        });



        ///// end of onclick listener

     /*
     /////// For displaying in textview

     mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String name=dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


      */




    }
}
