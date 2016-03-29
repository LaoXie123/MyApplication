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
				// ��ص���������
				String level = "" + intent.getIntExtra("level", 0);
				// ����������
				String scale = "" + intent.getIntExtra("scale", 0);
				// ��ط���
				String voltage = "" + intent.getIntExtra("voltage", 0);
				// ����¶�
				String temperature = "" + intent.getIntExtra("temperature", 0);
				// ���״̬��������һ������
				// BatteryManager.BATTERY_STATUS_CHARGING ��ʾ�ǳ��״̬
				// BatteryManager.BATTERY_STATUS_DISCHARGING �ŵ���
				// BatteryManager.BATTERY_STATUS_NOT_CHARGING δ���
				// BatteryManager.BATTERY_STATUS_FULL �����
				int checkStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
				String status = "";
				switch (checkStatus) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					status = "�����";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					status = "�ŵ���";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					status = "δ���";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					status = "�����";
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					status = "δ֪״̬";
					break;
				default:
					break;
				}

				// ������� BatteryManager.BATTERY_PLUGGED_AC ��ʾ�ǳ�������������ֵ����ʾ�� USB
				String plugged = "";
				int checkPlugged = intent.getIntExtra("plugged", 0);
				switch (checkPlugged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					plugged = "�����";
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					plugged = "USB";
					break;
				default:
					break;
				}
				// ��ؽ������������Ҳ��һ������
				// BatteryManager.BATTERY_HEALTH_GOOD ����
				// BatteryManager.BATTERY_HEALTH_OVERHEAT ����
				// BatteryManager.BATTERY_HEALTH_DEAD û��
				// BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ����ѹ
				// BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE δ֪����
				String health = "";
				int checkHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
				switch (checkHealth) {
				case BatteryManager.BATTERY_HEALTH_GOOD:
					health = "����";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					health = "����";
					break;
				case BatteryManager.BATTERY_HEALTH_COLD:
					health = "����";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					health = "û��";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					health = "����ѹ";
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					health = "δ֪����";
					break;
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					health = "δ֪״̬";
					break;
				default:
					break;
				}
				String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
				StringBuffer sb = new StringBuffer();
				sb.append("��ص�����" + level + "\n");
				sb.append("���������" + scale + "\n");
				sb.append("��ط�����" + voltage + "\n");
				sb.append("����¶ȣ�" + temperature + "\n");
				sb.append("���״̬��" + status + "\n");
				sb.append("������ͣ�" + plugged + "\n");
				sb.append("����״����" + health + "\n");
				sb.append("��ؼ�����" + technology + "\n");
				textView.setText(sb);
				StringBuffer sbOther = new StringBuffer();
				sbOther.append("���״̬������С��ŵ��С�δ��硢�ѳ���" + "\n");
				sbOther.append("������ͣ��������USB" + "\n");
				sbOther.append("����״�������á����ȡ����䡢û�硢����ѹ��δ֪״̬��δ֪����" + "\n");
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
