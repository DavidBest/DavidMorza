package com.example.david.morza.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.morza.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Numbers extends Fragment {

    public static boolean a = true;

    public static final String TAG = "Numbers";
    private SimpleCursorAdapter adapter;


    private ListView listView;

    private TextView btnNumbers;

    private String number="";
    static String contact = "";
    Cursor cursor;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        listView = (ListView)  rootView.findViewById(R.id.listContacts);

        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        cursor.moveToFirst();

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        int[] to = {R.id.textForContacts1, R.id.textForContacts2};

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.contacts_info, cursor, from, to, 0);
        view = createHeader(cursor);
        listView.addHeaderView(view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeyBoard.OnSelectedButtonListener getSetTextObject = (KeyBoard.OnSelectedButtonListener) getActivity();
                getSetTextObject.vibration(200);
                number = ((TextView) view.findViewById(R.id.textForContacts2)).getText().toString();
                Swuper swuper = (Swuper) getActivity();
                swuper.swapFromNumbers(number);
            }
        });



        btnNumbers = (TextView) rootView.findViewById(R.id.edit_message);

        btnNumbers.setText(contact);
        setLetterForMessage(contact);


        return rootView;
    }




    public void setLetterForMessage(String letter){

        listView.removeHeaderView(view);

        if (letter.compareTo("") == 0){
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        } else {
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like '%" + letter + "%'",
                    null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        }
        cursor.moveToFirst();

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        int[] to = {R.id.textForContacts1, R.id.textForContacts2};

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.contacts_info, cursor, from, to, 0);
        if (cursor.moveToFirst()){
            view = createHeader(cursor);
            listView.addHeaderView(view);
        }

        listView.setAdapter(adapter);
        btnNumbers.setText(letter);
    }

    View createHeader(Cursor cursor) {
        cursor.moveToFirst();
        View v = getActivity().getLayoutInflater().inflate(R.layout.firs_position_info, null);
        int a = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        ((TextView)v.findViewById(R.id.textForContacts1)).setText(cursor.getString(a));
        a = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        ((TextView)v.findViewById(R.id.textForContacts2)).setText(cursor.getString(a));
        return v;
    }

    public String getText(){
        return btnNumbers.getText().toString();
    }

    public void setText(String number){
        Toast.makeText(getActivity(), "aa", Toast.LENGTH_SHORT).show();
//        btnNumbers.setText(number);
//
//        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
//                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER},
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like '%" + number + "%'",
//                null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
//        int[] to = {R.id.textForContacts1, R.id.textForContacts2};
//
//        adapter = new SimpleCursorAdapter(getActivity(), R.layout.contacts_info, cursor, from, to, 0);
//        if (cursor.moveToFirst()){
//            view = createHeader(cursor);
//            listView.addHeaderView(view);
//        }
//
//        listView.setAdapter(adapter);
    }

    public void saveUs(){
        contact = btnNumbers.getText().toString();
    }

    public interface Swuper{
        void swapFromNumbers(String number);
    }
}
