package com.example.vanh1200.contactex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnClickListener {
    private static final int REQUEST_READ_CONTACT = 1;
    private static final int REQUEST_CALL_PHONE = 2;
    private static final String PREFIX_URI_CALL = "tel:";
    private ContactAdapter mContactAdapter;
    private RecyclerView mRecyclerContacts;
    private List<Contact> mContacts;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACT);
    }

    private void initView() {
        mRecyclerContacts = findViewById(R.id.recycler_contact);
    }

    private void checkPermission(String permission, int requestPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission}, requestPermission);
            } else {
                doFunction(requestPermission);
            }
        }
    }

    private void doFunction(int requestPermission) {
        switch (requestPermission) {
            case REQUEST_READ_CONTACT:
                initRecyclerContact();
                break;
            case REQUEST_CALL_PHONE:
                if (mPhoneNumber != null)
                    makeCall(mPhoneNumber);
            default:
                break;
        }
    }

    private void initRecyclerContact() {
        mContacts = new ArrayList<>();
        readContacts();
        mContactAdapter = new ContactAdapter(mContacts);
        mContactAdapter.setListener(this);
        mRecyclerContacts.setAdapter(mContactAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        mRecyclerContacts.addItemDecoration(decoration);
    }

    private void readContacts() {
        Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(contactUri,
                null, null, null, null);
        if (cursor == null)
            return;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setPhone(cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            mContacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void makeCall(String phone) {
        Uri uri = Uri.parse(mergeString(PREFIX_URI_CALL, phone));
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        startActivity(intent);
    }

    public String mergeString(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
        }
        return builder.toString();
    }

    @Override
    public void onClickCall(int position) {
        mPhoneNumber = mContacts.get(position).getPhone();
        checkPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE);
    }

    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onClickFavorite(int position) {
        Contact contact = mContacts.get(position);
        contact.setFavorite(!contact.getFavorite());
        mContactAdapter.notifyItemChanged(position);
    }
}
