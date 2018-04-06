package com.ltapps.textscanner;

/**
 * Created by Chetana on 28-03-2018.
 */
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contacts extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText Name;
    private EditText Email;

    private static final int CONTACTS_PERMISSION = 5;

    private Button AddContacts;

    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        phoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.EmailAddress);
        AddContacts = (Button) findViewById(R.id.contactBtn);

        try {
            phone = getIntent().getStringExtra("phoneNumber");
            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");

        } catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(Contacts.this, "Error", Toast.LENGTH_SHORT).show();
        }

        phoneNumber.setText(phone);
        Name.setText(name);
        Email.setText(email);

        AddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactsActivityIntent();
            }
        });
    }


    /**
     * Starts the contacts intent and requests permission to write to contacts if permission doesn't exist
     */
    public void startContactsActivityIntent(){
        String[] permissions = {"android.permission.WRITE_CONTACTS"};
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager
                .PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissions, CONTACTS_PERMISSION);
        }
        else {
            if (intent.resolveActivity(getPackageManager()) != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                startActivity(intent);
            }
        }
    }

}
