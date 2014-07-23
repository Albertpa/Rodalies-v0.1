package com.rds.rodalies.alarmas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rds.rodalies.R;

import java.util.List;

public class AlarmListAdapter extends BaseAdapter {

	private Context mContext;
	private List<AlarmModel> mAlarms;

	public AlarmListAdapter(Context context, List<AlarmModel> alarms) {
		mContext = context;
		mAlarms = alarms;
	}
	
	public void setAlarms(List<AlarmModel> alarms) {
		mAlarms = alarms;
	}
	
	@Override
	public int getCount() {
		if (mAlarms != null) {
			return mAlarms.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mAlarms != null) {
			return mAlarms.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (mAlarms != null) {
			return mAlarms.get(position).id;
		}
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.alarm_list_item, parent, false);
		}

        TextView sunday = (TextView) view.findViewById(R.id.alarm_item_sunday);
        TextView monday = (TextView) view.findViewById(R.id.alarm_item_monday);
        TextView tuesday = (TextView) view.findViewById(R.id.alarm_item_tuesday);
        TextView wednesday = (TextView) view.findViewById(R.id.alarm_item_wednesday);
        TextView thursday = (TextView) view.findViewById(R.id.alarm_item_thursday);
        TextView friday = (TextView) view.findViewById(R.id.alarm_item_friday);
        TextView saturday = (TextView) view.findViewById(R.id.alarm_item_saturday);

		AlarmModel model = (AlarmModel) getItem(position);
		
		TextView txtTime = (TextView) view.findViewById(R.id.alarm_item_time);

        if(model.timeHour == -1 && model.timeMinute == -1){
            txtTime.setText(mContext.getString(R.string.crear_alarma));
            sunday.setText("");
            monday.setText("");
            tuesday.setText("");
            wednesday.setText("");
            thursday.setText("");
            friday.setText("");
            saturday.setText("");
        }
        else{
            txtTime.setText(String.format("%02d:%02d", model.timeHour, model.timeMinute));

            sunday.setText(R.string.domingo);
            monday.setText(R.string.lunes);
            tuesday.setText(R.string.martes);
            wednesday.setText(R.string.miercoles);
            thursday.setText(R.string.jueves);
            friday.setText(R.string.viernes);
            saturday.setText(R.string.sabado);

            updateTextColor(sunday, model.getRepeatingDay(AlarmModel.SUNDAY));
            updateTextColor(monday, model.getRepeatingDay(AlarmModel.MONDAY));
            updateTextColor(tuesday, model.getRepeatingDay(AlarmModel.TUESDAY));
            updateTextColor(wednesday, model.getRepeatingDay(AlarmModel.WEDNESDAY));
            updateTextColor(thursday, model.getRepeatingDay(AlarmModel.THURSDAY));
            updateTextColor(friday, model.getRepeatingDay(AlarmModel.FRIDAY));
            updateTextColor(saturday, model.getRepeatingDay(AlarmModel.SATURDAY));

            view.setTag(Long.valueOf(model.id));
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((ListaNotificaciones) mContext).startAlarmDetailsActivity(((Long) view.getTag()).longValue());
                }
            });

            view.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    ((ListaNotificaciones) mContext).borrarAlerta(((Long) view.getTag()).longValue());
                    return true;
                }
            });
        }
		
		return view;
	}
	
	private void updateTextColor(TextView view, boolean isOn) {
		if (isOn) {
			view.setTextColor(Color.parseColor("#008000"));
		} else {
			view.setTextColor(Color.GRAY);
		}
	}

}
