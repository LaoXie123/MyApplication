package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private IntentFilter mIntentFilter;
	TextView textView, other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		textView = (TextView) findViewById(R.id.textView);
		other = (TextView) findViewById(R.id.other);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				// 电池电量，数字
				String level = "" + intent.getIntExtra("level", 0);
				// 电池最大容量
				String scale = "" + intent.getIntExtra("scale", 0);
				// 电池伏数
				String voltage = "" + intent.getIntExtra("voltage", 0);
				// 电池温度
				String temperature = "" + intent.getIntExtra("temperature", 0);
				// 电池状态，返回是一个数字
				// BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
				// BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
				// BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
				// BatteryManager.BATTERY_STATUS_FULL 电池满
				int checkStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
				String status = "";
				switch (checkStatus) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					status = "充电中";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					status = "放电中";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					status = "未充电";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					status = "电充满";
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					status = "未知状态";
					break;
				default:
					break;
				}

				// 充电类型 BatteryManager.BATTERY_PLUGGED_AC 表示是充电器，不是这个值，表示是 USB
				String plugged = "";
				int checkPlugged = intent.getIntExtra("plugged", 0);
				switch (checkPlugged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					plugged = "充电器";
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					plugged = "USB";
					break;
				default:
					break;
				}
				// 电池健康情况，返回也是一个数字
				// BatteryManager.BATTERY_HEALTH_GOOD 良好
				// BatteryManager.BATTERY_HEALTH_OVERHEAT 过热
				// BatteryManager.BATTERY_HEALTH_DEAD 没电
				// BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE 过电压
				// BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE 未知错误
				String health = "";
				int checkHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
				switch (checkHealth) {
				case BatteryManager.BATTERY_HEALTH_GOOD:
					health = "良好";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					health = "过热";
					break;
				case BatteryManager.BATTERY_HEALTH_COLD:
					health = "过冷";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					health = "没电";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					health = "过电压";
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					health = "未知错误";
					break;
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					health = "未知状态";
					break;
				default:
					break;
				}
				String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
				StringBuffer sb = new StringBuffer();
				sb.append("电池电量：" + level + "\n");
				sb.append("最大容量：" + scale + "\n");
				sb.append("电池伏数：" + voltage + "\n");
				sb.append("电池温度：" + temperature + "\n");
				sb.append("电池状态：" + status + "\n");
				sb.append("充电类型：" + plugged + "\n");
				sb.append("健康状况：" + health + "\n");
				sb.append("电池技术：" + technology + "\n");
				textView.setText(sb);
				StringBuffer sbOther = new StringBuffer();
				sbOther.append("电池状态：充电中、放电中、未充电、已充满" + "\n");
				sbOther.append("充电类型：充电器、USB" + "\n");
				sbOther.append("健康状况：良好、过热、过冷、没电、过电压、未知状态、未知错误" + "\n");
				other.setText(sbOther);
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, mIntentFilter);
	}

}
