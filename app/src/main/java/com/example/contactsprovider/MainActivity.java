package com.example.contactsprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public static final int CONTACT_LOADER_ID = 78;

    ArrayList<MyModel> myModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewContacts = findViewById(R.id.Names);
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), contactLoader);
        showContacts();
        getContactList();
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    String PhoneNum="";
                    while (pCur.moveToNext()) {
                        PhoneNum = PhoneNum +pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))+"\n";
                    }
                    myModels.add(new MyModel(name, PhoneNum, photo));
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        CustomAdapter customAdapter = new CustomAdapter(myModels, this);
        listViewContacts.setAdapter(customAdapter);
    }

    private void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> contactLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.HAS_PHONE_NUMBER,     
                            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};
                    CursorLoader cursorLoader = new CursorLoader(MainActivity.this, ContactsContract.Contacts.CONTENT_URI, projectionFields,
                            null, null, null
                    );
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                }
            };
}