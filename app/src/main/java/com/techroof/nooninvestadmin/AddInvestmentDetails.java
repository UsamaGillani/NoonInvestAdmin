package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.type.DateTime;

import java.util.HashMap;
import java.util.Map;

public class AddInvestmentDetails extends AppCompatActivity {

    EditText editTextInvestmentRate,editTextDailyIncome;
    AppCompatSpinner spinner,spinnerClass;
    private FirebaseFirestore firestore;
    public FirebaseStorage storage;
    String category,InvestmentRate,DailyIncome, categoryClass;
    Button btnAddInvestment;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_investment_details);
        editTextInvestmentRate=findViewById(R.id.edt_Investment_Rate);
        editTextDailyIncome=findViewById(R.id.edt_Daily_income);
        spinner=findViewById(R.id.sp_category_investment);
        spinnerClass=findViewById(R.id.sp_category_class);
        btnAddInvestment=findViewById(R.id.btn_addInvestment);

        String[] arraySpinner = new String[]{
                "Clothing", "Tech","HomeAppliances","AutoMotives"

        };

        String[] arraySpinnerclass = new String[]{
                "Class A", "Class B","Class C"

        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        ArrayAdapter<String> adapterspinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerclass);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapterspinner);



        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Data");
        progressDialog.setCanceledOnTouchOutside(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "" + category, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryClass=spinnerClass.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "" + categoryClass, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
btnAddInvestment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        InvestmentRate = editTextInvestmentRate.getText().toString();
        DailyIncome=editTextDailyIncome.getText().toString();
if(!editTextInvestmentRate.getText().toString().isEmpty() && !editTextDailyIncome.getText().toString().isEmpty()) {

    addInvestment(categoryClass, category, InvestmentRate, DailyIncome);
}else{

    Toast.makeText(getApplicationContext(), "PLEASE ENTER DATA", Toast.LENGTH_SHORT).show();

}
    }
});


    }

    private void addInvestment(String Class,String category, String investmentRate, String dailyIncome) {


        String Invest_Id = firestore.collection("AddInvestment").document().getId();

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("class",Class);
        productMap.put("category", category);
        productMap.put("InvestmentRate", investmentRate);
        productMap.put("DailyIncome", dailyIncome);
        productMap.put("id",Invest_Id);


        firestore.collection("InvestmentDetails")
                .document(Invest_Id)
                .set(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                            //Intent to home or previous activity
                            //Intent previousActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                            //startActivity(previousActivity);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("y", "onFailure: ");

            }
        });

    }
}