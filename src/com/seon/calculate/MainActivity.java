package com.seon.calculate;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText mParam1EditText;
	private EditText mParam2EditText;
	private Button mCalcalateBtn;
	
	private BroadcastReceiver mBootStartReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mParam1EditText = (EditText) findViewById(R.id.param_1);
		mParam2EditText = (EditText) findViewById(R.id.param_2);
		mCalcalateBtn = (Button) findViewById(R.id.calcalate_btn);

		mCalcalateBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String param1 = mParam1EditText.getText().toString();
				String param2 = mParam2EditText.getText().toString();
				if(param1.isEmpty() || param2.isEmpty()){
					dialog(0);
				}else{
					if (Integer.valueOf(param2) == 0) {
						dialog(1);
					}else{
						
					}
				}
			}
		});
	}

	private void dialog(final int errorCode) {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		String errorTip = null;
		if(errorCode == 0){
			errorTip = "参数不能为空，请重新输入";
		}else if(errorCode == 1){
			errorTip = "参数2不能为0，请重新输入";
		}
		builder.setMessage(errorTip);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(errorCode == 1) mParam2EditText.setText("");
			}
		});
		builder.create().show();
	}

}
