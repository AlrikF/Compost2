package com.example.dataauth2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class greydata extends AppCompatActivity {


    private static final String TAG = "AddToDatabase";
    private Button mAddToDB;
    private Button mAddnos;
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    com.example.dataauth2.DynamicViewGrey dnvg;
    Context context;
    private RelativeLayout mRelLayout;
    private GridLayout mGridlayout;

    private CheckBox cb_type1;
    private TextView tv_type1;
    private EditText et_type1;
    Integer total_wind=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greydata);


        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        //mRelLayout=(RelativeLayout)findViewById(R.id.mRelLayout);
        mGridlayout=(GridLayout)findViewById(R.id.gridLayout);

        // First : Wind Compost
        cb_type1 = (CheckBox)findViewById(R.id.cb_wind_compost);
        tv_type1 = (TextView)findViewById(R.id.tv_windcompost);
        et_type1=(EditText)findViewById(R.id.et_windcompost);

        tv_type1.setVisibility(View.INVISIBLE);
        et_type1.setVisibility(View.INVISIBLE);

        mAddToDB=(Button)findViewById(R.id.btn_add_site_details);
        mAddnos=(Button)findViewById(R.id.btn_add_wind_comp);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();          // under which user to insert
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(greydata.this,"Successfully signed in with: " + user.getEmail(),Toast.LENGTH_LONG);
                } else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(greydata.this,"Successfully signed out.",Toast.LENGTH_LONG);
                }
                //
            }
        };

        // Read from the database                             //////////////////////////////////////
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                //Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mAddToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                String newWind_no = et_type1.getText().toString();
                //Integer Wind_no_of=Integer.parseInt(mNo_of_Wind.getText().toString());
                if(!newWind_no.equals(""))
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    myRef.child(userID).child("Wind_Compost").child("Number").setValue(newWind_no);        // to change names
                    Toast.makeText(greydata.this,"Adding " + newWind_no + " to database...",Toast.LENGTH_LONG);
                    //reset the text
                    et_type1.setText("");
                }
            }
        });




        cb_type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked())
                {
                    tv_type1.setVisibility(View.VISIBLE);
                    et_type1.setVisibility(View.VISIBLE);
                }
                if(!buttonView.isChecked())
                {
                    tv_type1.setVisibility(View.INVISIBLE);
                    et_type1.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_type1.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on key press
                    Toast.makeText(greydata.this, et_type1.getText(), Toast.LENGTH_SHORT).show();
                    System.out.println(et_type1.getText().toString().trim());
                    return true;
                }
                return false;
            }





        });

        mAddnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cb_type1.isChecked() && et_type1.getText().toString().trim()!="")
                {


                    System.out.println("Total Wind is :"+total_wind);
                    if(total_wind!=0)
                    {
                        System.out.println("First clear prev Entries ");

                        FirebaseUser user = mAuth.getCurrentUser();
                        String userID = user.getUid();
                        myRef.child(userID).child("Wind_Compost").setValue(null);
//                        finish();
//                        overridePendingTransition(0,0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0,0);

                        //((ViewGroup)mGridlayout).removeViewAt(2*1+1);
                        //((ViewGroup)mGridlayout).removeViewAt(2*1);

                        //mGridlayout.removeView();

                        for (int i=0 ;i<total_wind;i++) {          /// For deleting the extra elements

                            TextView tv=findViewById(2*i);
                            EditText et=findViewById(2*i+1);
                            mGridlayout.removeView(tv);
                            mGridlayout.removeView( et );



                            // mGridlayout.removeViewAt(2*i);
                            // mGridlayout.removeViewAt(2*i+1);
                        }

                    }
                    total_wind=Integer.parseInt(et_type1.getText().toString().trim());
                    for (int i=0 ;i<total_wind;i++) {
                        dnvg = new com.example.dataauth2.DynamicViewGrey();
                        // mGridlayout.addView(dnvg.descriptionTextView(getApplicationContext(), "Itemqweq :"), 1);
                        mGridlayout.addView(dnvg.descriptionTextView(getApplicationContext(), "Capacity",2*(i)), 0);
                        //mGridlayout.addView(dnvg.descriptionTextView(getApplicationContext(), "Item jkj:"), 1);

                        mGridlayout.addView(dnvg.receivedNumberEditText(getApplicationContext(),2*i+1), 1);
                        //  mGridlayout.addView(dnvg.dummy(getApplicationContext(),"It"),2);
                    }
                    // startActivity(greydata.this,greydata.this);
                }
            }
        });

        mAddToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_owner=findViewById(R.id.et_owner);
                String owner=et_owner.getText().toString().trim();
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                myRef.child(userID).child("Owner").setValue(owner);


                for (int i=0 ;i<total_wind;i++) {
                    EditText et=findViewById(2*i+1);
                    String wind_cap=et.getText().toString().trim();
                    //    FirebaseUser user = mAuth.getCurrentUser();
                    //    String userID = user.getUid();
                    myRef.child(userID).child("Wind_Compost").child("WindCap"+i) .setValue(wind_cap);        // to change names
                    System.out.println(et.getText().toString().trim());
                    // Toast.makeText(greydata.this,"Adding " + wind_cap + " to database...",Toast.LENGTH_LONG);

                }

            }
        });



    }






}
