package com.example.humayunt.templateui.MainPanel;

/**
 * Created by HumayunT on 2/28/2018.
 */
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humayunt.templateui.Adapter.DoctorListAdapter;
import com.example.humayunt.templateui.DataModel.DoctorDetail;
import com.example.humayunt.templateui.HelpGuide.About;
import com.example.humayunt.templateui.HelpGuide.Faq;
import com.example.humayunt.templateui.HelpGuide.Features;
import com.example.humayunt.templateui.HelpGuide.WatchVideo;
import com.example.humayunt.templateui.R;
import com.example.humayunt.templateui.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Doctorlisttry extends Fragment  implements View.OnClickListener{

    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<DoctorDetail> list;
    RecyclerView recycle;
    private RatingBar ratingBar;


    HashMap<String, Object> hashMap = new HashMap<String, Object>();


    Button b1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listdoctors, container, false);

        String arr[]={Manifest.permission.CALL_PHONE};
        ActivityCompat.requestPermissions(getActivity(),arr,111);

        recycle = (RecyclerView)rootView.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("doctor");
        Log.d("pakistan","pakistan");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<DoctorDetail>();
                ArrayList<DoctorDetail> listt = new ArrayList<DoctorDetail>();

                if(dataSnapshot.exists()){
                    int i =0;
		    String keys = "";
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

			 keys += dataSnapshot1.getKey()+"\n";

                        DoctorDetail value = dataSnapshot1.getValue(DoctorDetail.class);
                        hashMap.put(dataSnapshot1.getKey(), value);

                       // Toast.makeText(getActivity(), hashMap.size(), Toast.LENGTH_LONG).show();


                    list.add(value);

                }
                }

                DoctorDetail[] doctorDetails = list.toArray(new DoctorDetail[]{});
                DoctorDetail temp;


                for(int i = 0; i < doctorDetails.length; i++){
                    for(int j = 0; j < doctorDetails.length - i - 1; j++){


                            if ((Math.signum(doctorDetails[j].getRating())==0 && Math.signum(doctorDetails[j].getNumberOfRating()) ==0)||
                                    (Math.signum(doctorDetails[j + 1].getRating())==0 &&(Math.signum(doctorDetails[j + 1].getRating())==0))) {
                                temp = doctorDetails[j];
                                doctorDetails[j] = doctorDetails[j + 1];
                                doctorDetails[j + 1] = temp;
                            }

                             else if((doctorDetails[j].getRating()/doctorDetails[j].getNumberOfRating()) < (doctorDetails[j+1].getRating()/ doctorDetails[j+1].getNumberOfRating()))
                                    {  temp = doctorDetails[j];
                                    doctorDetails[j] = doctorDetails[j + 1];
                                    doctorDetails[j + 1] = temp;
                                }






                    }
                }

                DoctorListAdapter recyclerAdapter = new DoctorListAdapter(Arrays.asList(doctorDetails),getActivity().getApplicationContext());
                recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

                recycle.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        DoctorDetail doc = new DoctorDetail();
        final String number = String.valueOf(doc.getNumber());
        Toast.makeText(getActivity(),number,Toast.LENGTH_LONG).show();



        return rootView;


    }

    @Override
    public void onClick(View v) {



    }
}
