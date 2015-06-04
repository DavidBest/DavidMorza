package com.example.david.morza.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.morza.DBHelper;
import com.example.david.morza.R;
import com.example.david.morza.RequestCode;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeyBoard extends Fragment{

    private String askPass= "", pointTire="" , textMessage="", textLetter="";
    private boolean afterEnter = true;

    private int scrollDown = 1000;

    public SQLiteDatabase database;
    private Cursor cursor;

    Vibrator vibrator;

    private static final String DB_NAME = "yourdb.sqlite3";
    private static final String TABLE_NAME_1 = "letterForMessage";
    private static final String MORSE_ID = "_id";
    private static final String MORSE_LETTER = "letter";
    private static final String MORSE_POINT_TIRE = "number";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_key_board, container, false);

        DBHelper dbHelper = new DBHelper(getActivity(), DB_NAME);
        database = dbHelper.openDataBase();

        cursor = database.query(TABLE_NAME_1,
                new String[]
                        {MORSE_ID, MORSE_LETTER, MORSE_POINT_TIRE},
                null, null, null, null, null);


        Button btnPoint = (Button) rootView.findViewById(R.id.btn_point);
        Button btnTire = (Button) rootView.findViewById(R.id.btn_tire);
        final Button btnEnter = (Button) rootView.findViewById(R.id.btn_enter);
        Button btnClear = (Button) rootView.findViewById(R.id.btn_clear);
        final TextView txtPointTire = (TextView) rootView.findViewById(R.id.txt_point_tire);

        if (savedInstanceState != null){
            askPass = savedInstanceState.getString("ask");
            pointTire = savedInstanceState.getString("pointTire");
            txtPointTire.setText(pointTire);
            textLetter = finding(askPass);
            if (textLetter.compareTo("") != 0) {
                btnEnter.setText(textLetter);
            } else {
                btnEnter.setText("Enter");
            }

        }

        btnPoint.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                if (askPass.length() >= 5) {
                    Toast.makeText(getActivity(), "to many simvol", Toast.LENGTH_SHORT).show();
                } else {
//                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                    vibrator.vibrate(RequestCode.pointOrCLear);
                    getSetTextObject.vibration(200);
                    textLetter = "";
                    askPass = askPass + RequestCode.ONE;
                    pointTire = pointTire + RequestCode.POINT;
                    txtPointTire.setText(pointTire);
                    textLetter = finding(askPass);
                    if (textLetter.compareTo("") != 0) {
                        btnEnter.setText(textLetter);
                    } else {
                        btnEnter.setText("Enter");
                    }
                }

            }
        });

        btnTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                if (askPass.length() >= 5) {
                    Toast.makeText(getActivity(), "to many simvol", Toast.LENGTH_SHORT).show();
                } else {
//                    vibrator.vibrate(RequestCode.tireOrEnter);
                    getSetTextObject.vibration(1000);
                    textLetter = "";
                    askPass = askPass + RequestCode.TWO;
                    pointTire = pointTire + RequestCode.TIRE;
                    txtPointTire.setText(pointTire);
                    textLetter = finding(askPass);
                    if (textLetter.compareTo("") != 0) {
                        btnEnter.setText(textLetter);
                    } else {
                        btnEnter.setText("Enter");
                    }
                }
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vibrator.vibrate(100);
                //получаем тексt
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                textMessage = getSetTextObject.getText();
//                Toast.makeText(getActivity(), textMessage, Toast.LENGTH_SHORT).show();
//                    vibrator.vibrate(RequestCode.tireOrEnter);
                //пробел после знака
//                if ((textMessage.compareTo(RequestCode.ASK_CLEAN) != 0) &&
//                        ((textMessage.charAt(textMessage.length() - 1) == '.') ||
//                                (textMessage.charAt(textMessage.length() - 1) == ',') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '!') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '?') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '.')) &&
//                        (textMessage.charAt(textMessage.length() - 1) != ' ') &&
//                        askPass.compareTo(RequestCode.ASK_CLEAN) != 0)
//                    textMessage = textMessage + RequestCode.SPACE;

                if (askPass.compareTo(RequestCode.ASK_CLEAN) == 0) {
                    //знак или пробел
                    if ((textMessage.compareTo(RequestCode.ASK_CLEAN) != 0) && afterEnter) {
                        switch (textMessage.charAt(textMessage.length() - 1)) {

                            case (' '):
                                textMessage = textMessage.substring(0, textMessage.length() - 1) + ".";
                                break;
                            case ('.'):
                                textMessage = textMessage.substring(0, textMessage.length() - 1) + ",";
                                break;
                            case (','):
                                textMessage = textMessage.substring(0, textMessage.length() - 1) + "?";
                                break;
                            case ('?'):
                                textMessage = textMessage.substring(0, textMessage.length() - 1) + "!";
                                break;
                            default:
                                textMessage = textMessage + RequestCode.SPACE;
                                break;
                        }
                    } else {
                        textMessage = textMessage + RequestCode.SPACE;
                    }
                } else {
                    //символ если сущесвует
                    if (textLetter.compareTo(RequestCode.ASK_CLEAN) == 0 && askPass.compareTo(RequestCode.ASK_CLEAN) != 0) {
//                        vibrator.vibrate(RequestCode.bad);
                        Toast.makeText(getActivity(), "No letter", Toast.LENGTH_SHORT).show();
                    } else {
                        textMessage = textMessage + textLetter;
                        askPass = RequestCode.ASK_CLEAN;
                        pointTire = RequestCode.ASK_CLEAN;
                        txtPointTire.setText(pointTire);
                        textLetter = "";
                    }
                }
                btnEnter.setText("Enter");
//                editMessage.setText(textMessage);
                //отправляем текс во фрагмент
                getSetTextObject.onSetText(textMessage);
                scrollDown += 100;
                getSetTextObject.scrollGo(scrollDown);
                afterEnter = true;
            }
        });

        btnEnter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                textMessage = getSetTextObject.getText();
//                getSetTextObject.vibration(100);
//                    vibrator.vibrate(RequestCode.tireOrEnter);
                //пробел после знака
//                if ((textMessage.compareTo(RequestCode.ASK_CLEAN) != 0) &&
//                        ((textMessage.charAt(textMessage.length() - 1) == '.') ||
//                                (textMessage.charAt(textMessage.length() - 1) == ',') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '!') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '?') ||
//                                (textMessage.charAt(textMessage.length() - 1) == '.')) &&
//                        (textMessage.charAt(textMessage.length() - 1) != ' ') &&
//                        askPass.compareTo(RequestCode.ASK_CLEAN) != 0)
//                    textMessage = textMessage + RequestCode.SPACE;

                if (askPass.compareTo(RequestCode.ASK_CLEAN) == 0) {
                    //знак или пробел
                    textMessage = textMessage + "\n";
                } else {
                    //символ если сущесвует
                    if (textLetter.compareTo(RequestCode.ASK_CLEAN) == 0 && askPass.compareTo(RequestCode.ASK_CLEAN) != 0) {
//                        vibrator.vibrate(RequestCode.bad);
                        Toast.makeText(getActivity(), "No letter", Toast.LENGTH_SHORT).show();
                    } else {
                        textLetter = textLetter.toUpperCase();
                        textMessage = textMessage + textLetter;
                        askPass = RequestCode.ASK_CLEAN;
                        pointTire = RequestCode.ASK_CLEAN;
                        txtPointTire.setText(pointTire);
                        textLetter = "";
                    }
                }
                btnEnter.setText("Enter");
//                editMessage.setText(textMessage);
                //отправляем текс во фрагмент
                getSetTextObject.onSetText(textMessage);
                scrollDown += 100;
                getSetTextObject.scrollGo(scrollDown);
                afterEnter = true;
                return true;
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                textMessage = getSetTextObject.getText();
//                vibrator.vibrate(RequestCode.pointOrCLear);
                if (askPass.compareTo(RequestCode.ASK_CLEAN) == 0 && textMessage.compareTo("") != 0) {
                    textMessage = textMessage.substring(0, textMessage.length() - 1);
                    getSetTextObject.onSetText(textMessage);
                    afterEnter = false;
                } else {
                    askPass = RequestCode.ASK_CLEAN;
                    pointTire = RequestCode.ASK_CLEAN;
                    txtPointTire.setText(pointTire);
                    btnEnter.setText("Enter");
                }
            }
        });

        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OnSelectedButtonListener getSetTextObject = (OnSelectedButtonListener) getActivity();
                if (getSetTextObject.mabecheck()) {
                    getSetTextObject.onDeleteNumber();
                }
                return true;
            }
        });

        return rootView;
    }

//
//
//    private int  translateIdToIndex(int id){
//        int index = -1;
//        switch (id){
//            case R.id.btn_enter:
//                index = 1;
//                break;
//            case R.id.btn_clear:
//                index = 2;
//                break;
//        }
//        return index;
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("ask", askPass);
        outState.putString("pointTire", pointTire);
    }

    public String finding(String asking){
        String number;
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            number = cursor.getString(cursor.getColumnIndex(MORSE_POINT_TIRE));
            if (number.equals(asking)) {
                textLetter = cursor.getString(cursor.getColumnIndex(MORSE_LETTER));
                break;
            }
            cursor.moveToNext();
        }
        return textLetter;
    }


    public interface OnSelectedButtonListener{
        void onDeleteNumber();
        void onSetText(String letter);
        String getText();
        void scrollGo(int scroll);
        boolean mabecheck();
        void vibration(int vibrateTime);
    }
}
