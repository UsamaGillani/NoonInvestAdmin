package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UpdateInvestmentActivity extends AppCompatActivity {
   private EditText editTextUpdateInvestRate,editTextUpdateDailyIncome;
   private Button btnupdate;
   private FirebaseFirestore firestore;
   private String id;
   private ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_investment);
        id=getIntent().getExtras().getString("IdMoveToUpdate");
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_LONG).show();
        editTextUpdateInvestRate=findViewById(R.id.edt_upd_Investment_Rate);
        editTextUpdateDailyIncome=findViewById(R.id.edt_upd_Daily_income);
        imgback=findViewById(R.id.img_back_arrow);
        firestore=FirebaseFirestore.getInstance();
       btnupdate=findViewById(R.id.btn_updateInvestment);
       imgback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
       btnupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            DocumentReference documentReference= firestore.collection("InvestmentDetails").document(id);
               HashMap hasshmap=new HashMap<>();
               String update1=editTextUpdateDailyIncome.getText().toString();
               String update2=editTextUpdateInvestRate.getText().toString();
               hasshmap.put("InvestmentRate",update1);
               hasshmap.put("DailyIncome",update2);
               documentReference.update(hasshmap).addOnSuccessListener(new OnSuccessListener() {
                   @Override
                   public void onSuccess(Object o) {

                       Toast.makeText(getApplicationContext(),"DATA UPDATEDD",Toast.LENGTH_LONG).show();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(),"NOT UPDATED!",Toast.LENGTH_LONG).show();
                   }
               });
           }
       });


    }
}