package com.example.david.morza.activity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.david.morza.fragment.KeyBoard;
import com.example.david.morza.fragment.Numbers;
import com.example.david.morza.R;
import com.example.david.morza.fragment.TextAndSend;


public class MessageActivity extends ActionBarActivity implements KeyBoard.OnSelectedButtonListener, Numbers.Swuper, TextAndSend.Swuper {

    private TextAndSend textAndSendFragment;
    private Numbers numbers;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    public static String message = "",
            number = "";

    private String numberRest = "", messageRest = "", contactRest = "";

    private boolean check = true;

    public Vibrator vibrator;

    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (savedInstanceState != null) {
            check = savedInstanceState.getBoolean("check");
        }
        startFragment();
//        vibrator.vibrate(1000);
//        vibration(1000);

    }

//    @Override
//    protected void onRestoreInstanceState(@Nullable Bundle  savedInstanceState) {
//
//
//
//        super.onRestoreInstanceState(savedInstanceState);
//    }



    @Override
    protected void onResume() {
        if (check) {
            manager.beginTransaction()
                    .add(R.id.container, textAndSendFragment, TextAndSend.TAG)
                    .commit();
        } else {
            manager.beginTransaction()
                    .add(R.id.container, numbers, Numbers.TAG)
                    .commit();
//            Numbers numbers1 = (Numbers) manager.findFragmentByTag(Numbers.TAG);
//            numbers1.setText(contactRest);
        }
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (check) {
            TextAndSend textAndSend = (TextAndSend) manager.findFragmentByTag(TextAndSend.TAG);
            textAndSend.saveUs();
            manager.beginTransaction().remove(textAndSendFragment).commit();
        } else {
//            outState.putString("numbr", numbers.getText());
            Numbers numbers = (Numbers) manager.findFragmentByTag(Numbers.TAG);
            numbers.saveUs();
            manager.beginTransaction().remove(numbers).commit();
        }
        outState.putBoolean("check", check);

        super.onSaveInstanceState(outState);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.Instruction:
                intent = new Intent(getApplication(),InstructionActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDeleteNumber() {
        TextAndSend textAndSend = (TextAndSend) manager.findFragmentByTag(TextAndSend.TAG);
        textAndSend.deleteNumber();
    }

    @Override
    public void onSetText(String letter) {
        if(check){
            TextAndSend textAndSend = (TextAndSend) manager.findFragmentByTag(TextAndSend.TAG);
            textAndSend.setLetterForMessage(letter);
        } else {
            Numbers numbers = (Numbers) manager.findFragmentByTag(Numbers.TAG);
            numbers.setLetterForMessage(letter);
        }
    }


    @Override
    public String getText() {
        String a;

        if(check){
            TextAndSend textAndSend = (TextAndSend) manager.findFragmentByTag(TextAndSend.TAG);
            a = textAndSend.getText();
        } else {
            Numbers numbers = (Numbers) manager.findFragmentByTag(Numbers.TAG);
            a = numbers.getText();
        }

        return a;
    }

    @Override
    public void swapFromNumbers(String number) {
//        MessageActivity.message = message;
//        MessageActivity.number = number;
        TextAndSend.numberSave = number;
        manager.beginTransaction()
                .replace(R.id.container, textAndSendFragment, TextAndSend.TAG)
//                .addToBackStack(TextAndSend.TAG)
                .commit();
        check = true;
    }

    @Override
    public void swapFromText(String message) {
//        MessageActivity.message = message;
//        MessageActivity.number = number;



        manager.beginTransaction()
                .replace(R.id.container, numbers, Numbers.TAG)
//                .addToBackStack(Numbers.TAG)
                .commit();
        check = false;
    }

    @Override
    public void scrollGo(int scroll) {
        if(manager.findFragmentByTag(TextAndSend.TAG) != null) {
            TextAndSend textAndSend = (TextAndSend) manager.findFragmentByTag(TextAndSend.TAG);
            textAndSend.setScroll(scroll);
        }
    }

    @Override
    public boolean mabecheck() {
        return check;
    }

    @Override
    public void vibration(int vibrateTime) {
//        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.cancel();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(vibrateTime);
//        vibrator.

    }

    public void startFragment(){
        manager = getSupportFragmentManager();

        textAndSendFragment = new TextAndSend();
        numbers = new Numbers();

//        if (check) {
//            manager.beginTransaction()
//                    .add(R.id.container, textAndSendFragment, TextAndSend.TAG)
//                    .commit();
//        } else {
//            manager.beginTransaction()
//                    .add(R.id.container, numbers, Numbers.TAG)
//                    .commit();
//        }
//        manager.beginTransaction()
//                    .add(R.id.container, textAndSendFragment, TextAndSend.TAG)
//                    .commit();
//        Toast.makeText(getApplicationContext(), a,Toast.LENGTH_SHORT).show();
    }


//    if (check){
//        manager.beginTransaction().remove(textAndSendFragment).commit();
//    } else {
//        manager.beginTransaction().remove(numbers).commit();
//    }


}
