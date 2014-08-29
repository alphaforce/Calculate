package com.seon.calculate.activity;

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
import com.seon.calculate.service.CalculateService;
import com.seon.calculate.R;
import com.seon.calculate.common.Params;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    private final static int RESULT_OK = 0;
    private final static int RESULT_IS_EMPTY = 1;
    private final static int RESULT_IS_ZERO = 2;
    private final static int RESULT_TOO_LARGE = 3;

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

                int resultCode = checkInt(param1,param2);
                if(resultCode == RESULT_OK){
                    mPrefs.edit().putInt(Params.PARAMS_1, Integer.parseInt(param1))
                            .putInt(Params.PARAMS_2, Integer.parseInt(param2))
                            .commit();

                    Intent service = new Intent(MainActivity.this, CalculateService.class);
                    startService(service);
                }else {
                    alert(resultCode);
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
                mParam1EditText.setText(String.valueOf(mPrefs.getInt(Params.PARAMS_1, 0)));
                mParam2EditText.setText(String.valueOf(mPrefs.getInt(Params.PARAMS_2, 1)));
            }
        }
    }

    private void alert(int errorCode) {
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        String warning = null;
        if (errorCode == RESULT_EMPTY) {
            warning = "参数不能为空，请重新输入";
        } else if (errorCode == RESULT_IS_ZERO) {
            warning = "第二个数字不能为0，请重新输入";
        }else if(errorCode == RESULT_TO_LARGE){
            warning = "输入数字过大，请重新输入";
        }
        builder.setMessage(warning);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private int checkInt(String param1,String param2){
        int resultCode = 0;
        if(param1.isEmpty() || param2.isEmpty()){
            resultCode = 1;
        }else{
            try {
                int p1 = Integer.parseInt(param1);
                int p2 = Integer.parseInt(param2);

                if(p2 == 0){
                    resultCode = 2;
                }
            }catch (NumberFormatException e){
                resultCode = 3;
            }
        }
        return resultCode;
    }
}
