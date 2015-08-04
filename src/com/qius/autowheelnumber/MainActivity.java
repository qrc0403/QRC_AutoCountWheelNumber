package com.qius.autowheelnumber;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qius.autowheelnumber.R;

public class MainActivity extends ActionBarActivity {

	private ArrayList<String> hoursArray = new ArrayList<String>();
	private TextView text;
	private ListView listView;
	private EditText ed_count;
	private EditText ed_total;
	private ArrayAdapter<String> adapter;
	private int cur;
	private int parseInt;
	private int totalNum;
	private int addCur;
	private int beforsubTotal;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.textView1);
		listView = (ListView) findViewById(R.id.listView);
		ed_count = (EditText) findViewById(R.id.ed_count);
		ed_total = (EditText) findViewById(R.id.ed_total);

		for (int i = 0; i < 10000; i++) {
			hoursArray.add("" + i % 10);
		}

		adapter = new ArrayAdapter<String>(this, R.layout.list_item, hoursArray);
		listView.setAdapter(adapter);
		listView.setDivider(null);
		listView.setEnabled(false);

		listView.setScrollbarFadingEnabled(false);

	}

	public void run(View v) {
		String count = ed_count.getText().toString();
		if (TextUtils.isEmpty(count)) {
			return;
		}
		parseInt = Integer.parseInt(count);
		int total = parseInt + totalNum; // 增加后的总数
		addCur = getCur(total); // 增加后的各位
		final int subTotal = subTotal(total); // 增加后的去掉各位的值
		listView.postDelayed(new Runnable() {

			@Override
			public void run() {
				int j = (listView.getFirstVisiblePosition() + 1) % 10;
				// Log.e("run ====", "j  = " + j + ": addCur = " + addCur);
				if (subTotal > beforsubTotal) {
					if (cur % 10 == 9) {
						text.setText("" + ++beforsubTotal);
					}

				} else {
					if (j == addCur) {
						return;
					}
				}

				listView.smoothScrollToPositionFromTop(cur++, 0);
				listView.postDelayed(this, 100);
			}

		}, 200);
	}

	public void sub(View v) {
		position = 1000 + cur;
		listView.setSelection(position);
		String count = ed_count.getText().toString();
		if (TextUtils.isEmpty(count)) {
			return;
		}
		parseInt = Integer.parseInt(count);
		int total = totalNum - parseInt; // 减少后的总数
		addCur = getCur(total); // 减少后的个位
		final int subTotal = subTotal(total); // 减少后的去掉各位的值
		listView.postDelayed(new Runnable() {

			@Override
			public void run() {
				int j = (listView.getFirstVisiblePosition()) % 10;
				// Log.e("sub ====", "position  = " + position + ": addCur = " +
				// addCur);
				if (subTotal < beforsubTotal) {
					if (position % 10 == 9) {
						text.setText("" + --beforsubTotal);
					}

				} else {
					if (j == addCur) {
						return;
					}
				}

				listView.smoothScrollToPositionFromTop(position--, 0);
				listView.postDelayed(this, 100);
			}

		}, 200);
	}

	public void onClick(View v) {
		String total = ed_total.getText().toString();
		if (TextUtils.isEmpty(total)) {
			Toast.makeText(this, "请设置总数", 0).show();
			return;
		}
		totalNum = Integer.parseInt(total);
		getCur(totalNum);
		listView.setAdapter(adapter);
		listView.setSelection(cur);
		beforsubTotal = subTotal(totalNum);
		text.setText(beforsubTotal + "");
	}

	/**
	 * 截取各位
	 * 
	 * @param totalNum
	 * @return
	 */
	private int getCur(int totalNum) {
		cur = totalNum % 10;
		return cur;
	}

	/**
	 * 返回去除各位数的总数值
	 * 
	 * @param total
	 * @return
	 */
	private int subTotal(int total) {
		String tt = total + "";
		String substring = tt.substring(0, tt.length() - 1);
		int sub = Integer.parseInt(substring);
		return sub;
	}
}
