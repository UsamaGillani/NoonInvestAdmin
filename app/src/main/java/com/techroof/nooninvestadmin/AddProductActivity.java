package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    EditText productNameEt;
    ImageView imageView;
    TextView textView;
    Button btnaddProduct;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    //private Firestore db;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";
    String Database_Path = "All_Image_Uploads_Database";

    private String productName, categoryName, productImg;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        imageView = findViewById(R.id.upload_image);
        textView = findViewById(R.id.textview_Category);
        btnaddProduct = findViewById(R.id.button_Product_Add);
        productNameEt = findViewById(R.id.Edt_Product_Name);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Uploading Image");
        progressDialog.setCanceledOnTouchOutside(false);

        firestore = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        categoryName = bundle.getString("category");

        textView.setText(categoryName);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getimagefromgallery();
            }
        });

        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //UploadImage();

                productName = productNameEt.getText().toString();

                if (TextUtils.isEmpty(productName)) {
                    productNameEt.setError("Enter Product Name");
                }
                if (TextUtils.isEmpty(productImg)) {
                    Toast.makeText(getApplicationContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    addData(productName, categoryName, productImg);
                    Toast.makeText(getApplicationContext(),
                            "url" + productImg,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void addData(String productName, String categoryName, String productImg) {

        String pId = firestore.collection("products").document().getId();

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("name", productName);
        productMap.put("category", categoryName);
        productMap.put("image", productImg);
        productMap.put("id", pId);

        firestore.collection("products")
                .document(pId)
                .set(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_SHORT).show();
                            //Intent to home or previous activity
                            Intent previousActivity=new Intent(getApplicationContext(),ProductCategoryActivity.class);
                            startActivity(previousActivity);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();


            }
        });
    }


    public void getimagefromgallery() {

        try {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_IMAGE);
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            UploadImageFileToFirebaseStorage();


        }

    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImageFileToFirebaseStorage() {


        if (imageUri != null) {

            progressDialog.show();

            StorageReference storageReference2nd =
                    storageReference
                            .child(Storage_Path + System.currentTimeMillis() + "." +
                                    GetFileExtension(imageUri));


            storageReference2nd.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            productImg = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                            progressDialog.dismiss();

                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    productImg = uri.toString();
                                    Toast.makeText(getApplicationContext(), "URI" + productImg, Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            progressDialog.dismiss();

                            Toast.makeText(AddProductActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            //progressDialog.setTitle("Image is Uploading...");

                        }
                    });


        } else {

            Toast.makeText(AddProductActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }


    }


}
