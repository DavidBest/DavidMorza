package com.example.david.morza.fragment;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.david.morza.DBHelper;
import com.example.david.morza.R;

public class Instruction extends Fragment {

    private static final String DB_NAME = "yourdb.sqlite3";
    private static final String TABLE_NAME_1 = "letterForMessage";
    private static final String MORSE_ID = "_id";
    private static final String MORSE_LETTER = "letter";
    private static final String MORSE_POINT_TIRE = "number";
    private static final String MORSE_POINT_TIRE_SECOND = "pointTire";

    SQLiteDatabase database;
    ListView listview;
    SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);

        listview = (ListView) rootView.findViewById(R.id.listV);

        DBHelper myDbHelper = new DBHelper(getActivity(),DB_NAME);
        database = myDbHelper.openDataBase();

        createTaibelPointTire();

        Cursor cursor = database.query(TABLE_NAME_1,
                new String[]
                        {MORSE_ID, MORSE_LETTER , MORSE_POINT_TIRE_SECOND},
                null, null, null, null, null );
        cursor.moveToFirst();

        String[] columns = new String[]{MORSE_LETTER , MORSE_POINT_TIRE_SECOND};
        int[] to = new int[]{R.id.textList1, R.id.textList2};

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.point_tire_instruction_info, cursor, columns, to, 0);

        listview.setAdapter(adapter);

        return rootView;
    }

    private void createTaibelPointTire(){

        ContentValues cv =new ContentValues();
        int rowId = 1;
        String were;
        Cursor cursor1 = database.query(TABLE_NAME_1,
                new String[]
                        {MORSE_ID, MORSE_LETTER , MORSE_POINT_TIRE ,MORSE_POINT_TIRE_SECOND},
                null, null, null, null, null );

        cursor1.moveToFirst();

        if(cursor1.getString(3)==null)
            while (!cursor1.isAfterLast()){
                were = MORSE_ID+ "=" + rowId;
                String second = cursor1.getString(cursor1.getColumnIndex(MORSE_POINT_TIRE));
                int end = second.length();
                char[] pointTire = new char[end];
                second.getChars(0,end,pointTire,0);
                String numberPointTire = "";
                for(char i : pointTire){
                    if (i=='1') {
                        numberPointTire = numberPointTire + ". ";
                    }else{
                        numberPointTire = numberPointTire + "_ ";
                    }
                    second = numberPointTire;
                }
                cv.put( MORSE_POINT_TIRE_SECOND, second );
                database.update(TABLE_NAME_1, cv, were, null);
                rowId++;
                cursor1.moveToNext();
            }
        cursor1.close();

    }
}
