package com.seon.calculate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    private EditText mParam1EditText;
    private EditText mParam2EditText;
    private Button mCalculateBtn;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mParam1EditText = (EditText) findViewById(R.id.param_1);
        mParam2EditText = (EditText) findViewById(R.id.param_2);

        setIntentParams(this.getIntent());

        mCalculateBtn = (Button) findViewById(R.id.calcalate_btn);
        mCalculateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String param1 = mParam1EditText.getText().toString();
                String param2 = mParam2EditText.getText().toString();
                if (param1.isEmpty() || param2.isEmpty()) {
                    dialog(0);
                } else {
                    if (Integer.valueOf(param2) == 0) {
                        dialog(1);
                    } else {
                        mPrefs.edit().putString(Params.PARAMS_1, param1)
                                .putString(Params.PARAMS_2, param2)
                                .commit();

                        Intent service = new Intent(MainActivity.this, CalculateService.class);
                        startService(service);
                    }
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntentParams(intent);
    }

    private void setIntentParams(Intent intent){
        Bundle data = intent.getExtras();
        if(data != null){
            boolean flag = data.getBoolean(Params.NOTIFICATION_FLAG, false);
            if(flag){
                mParam1EditText.setText(mPrefs.getString(Params.PARAMS_1, ""));
                mParam2EditText.setText(mPrefs.getString(Params.PARAMS_2, ""));
            }
        }
    }

    private void dialog(final int errorCode) {
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        String errorTip = null;
        if (errorCode == 0) {
            errorTip = "参数不能为空，请重新输入";
        } else if (errorCode == 1) {
            errorTip = "第二个数字不能为0，请重新输入";
        }
        builder.setMessage(errorTip);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (errorCode == 1) mParam2EditText.setText("");
            }
        });
        builder.create().show();
    }
}
