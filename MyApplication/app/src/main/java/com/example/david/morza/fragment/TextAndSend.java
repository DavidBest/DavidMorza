package com.example.david.morza.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.morza.activity.MessageActivity;
import com.example.david.morza.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextAndSend extends Fragment{

    public static final String TAG = "TextAndSend";

    private String message="",
            number="";

    public static String messageSave = "", numberSave = "";

    private TextView editMessage,
            editNumber;

    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_text_and_send, container, false);

        editMessage = (TextView) rootView.findViewById(R.id.edit_message);
        editNumber = (TextView) rootView.findViewById(R.id.edit_number);
        scrollView = (ScrollView) rootView.findViewById(R.id.scroll);
        editMessage.setText(MessageActivity.message);
        editNumber.setText(MessageActivity.number);

        editMessage.setText(messageSave);
        editNumber.setText(numberSave);

        if (savedInstanceState != null){

            editNumber.setText(savedInstanceState.getString("number"));

//            if (savedInstanceState.containsKey("name")) {
            editMessage.setText(savedInstanceState.getString("name"));

        }

//        editMessage.setText("sss");

        Button btnNumber = (Button) rootView.findViewById(R.id.btn_number);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = editMessage.getText().toString();
                number = editNumber.getText().toString();
                Swuper swuper = (Swuper) getActivity();
                swuper.swapFromText(message);
                messageSave = message;

            }
        });

        Button btnSend = (Button) rootView.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (editMessage.getText().toString().compareTo("") == 0) {
//                    Toast.makeText(getActivity(), " were is your text !?", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    try {
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(editNumber.getText().toString(), null,  editMessage.getText().toString(), null, null);
//                        Toast.makeText(getActivity(), "SMS Sent!", Toast.LENGTH_SHORT).show();
//                        editMessage.setText("");
//                        editNumber.setText("");
//
//                    } catch (Exception e) {
//                        Toast.makeText(getActivity(), " SMS failed, please try again letter!", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
            }
        });

        return rootView;
    }

    public void saveUs(){
        messageSave = editMessage.getText().toString();
        numberSave = editNumber.getText().toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("number", editNumber.getText().toString());
        outState.putString("name", editMessage.getText().toString());
        Toast.makeText(getActivity(), "!!!!!!!", Toast.LENGTH_SHORT).show();
        super.onSaveInstanceState(outState);
    }



    public void deleteNumber(){
        editNumber.setText("");
    }

    public void setScroll(int scroll){
        scrollView.scrollBy(0, scroll);
    }

    public void setLetterForMessage(String letter){
        editMessage.setText(letter);
    }

    public String getText(){
        return  editMessage.getText().toString();
    }

    public interface Swuper{
        void swapFromText(String message);
    }


}
