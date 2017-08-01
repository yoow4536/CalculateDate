package com.widget.yoo.aty;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import com.widget.yoo.R;
import com.widget.yoo.util.Yoo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final SharedPreferences preferences = getSharedPreferences("yoo", Context.MODE_PRIVATE);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);

        Date nowDate = new Date();
        int day=nowDate.getDay();
        int year=nowDate.getYear();
        int month=nowDate.getMonth();

        datePicker.updateDate(year,month,day);
        datePicker.init(2017, 8, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                // 获取一个日历对象，并初始化为当前选中的时间
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                SimpleDateFormat format = new SimpleDateFormat(Yoo.FORMATE_DATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("targetDate", format.format(calendar.getTime()));
                editor.commit();
                String targetDate = preferences.getString("targetDate", "unset");
                Toast.makeText(SecondActivity.this, "from preference :" + targetDate, Toast.LENGTH_SHORT).show();
                //datePicker.updateDate(i,i1,i2);
            }
        });
    }
}
