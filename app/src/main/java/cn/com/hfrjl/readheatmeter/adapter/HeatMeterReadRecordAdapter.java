package cn.com.hfrjl.readheatmeter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.com.hfrjl.readheatmeter.R;
import cn.com.hfrjl.readheatmeter.bean.HeatMeterReadRecord;

public class HeatMeterReadRecordAdapter extends BaseAdapter {

    private List<HeatMeterReadRecord> list;
    private LayoutInflater inflater;

    public HeatMeterReadRecordAdapter(Context context, List<HeatMeterReadRecord> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void changeList(List<HeatMeterReadRecord> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_read_heat_meter_record, parent, false);
            holder.tv_meterId = (TextView) convertView.findViewById(R.id.tv_meterId);
            holder.tv_recordTime = (TextView) convertView.findViewById(R.id.tv_recordTime);
            holder.tv_productType = (TextView) convertView.findViewById(R.id.tv_productType);
            holder.tv_sumCool = (TextView) convertView.findViewById(R.id.tv_sumCool);
            holder.tv_sumHeat = (TextView) convertView.findViewById(R.id.tv_sumHeat);
            holder.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            holder.tv_power = (TextView) convertView.findViewById(R.id.tv_power);
            holder.tv_flowRate = (TextView) convertView.findViewById(R.id.tv_flowRate);
            holder.tv_closeTime = (TextView) convertView.findViewById(R.id.tv_closeTime);
            holder.tv_valveStatus = (TextView) convertView.findViewById(R.id.tv_valveStatus);
            holder.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
            holder.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
            holder.tv_workTime = (TextView) convertView.findViewById(R.id.tv_workTime);
            holder.tv_meterTime = (TextView) convertView.findViewById(R.id.tv_meterTime);
            holder.tv_voltage = (TextView) convertView.findViewById(R.id.tv_voltage);
            holder.tv_meterStatus = (TextView) convertView.findViewById(R.id.tv_meterStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HeatMeterReadRecord heatMeterReadRecord = list.get(position);
        holder.tv_meterId.setText(heatMeterReadRecord.getMeterId());
        holder.tv_recordTime.setText(heatMeterReadRecord.getRecordTime());
        holder.tv_productType.setText(heatMeterReadRecord.getProductType());
        holder.tv_sumCool.setText(heatMeterReadRecord.getSumCool());
        holder.tv_sumHeat.setText(heatMeterReadRecord.getSumHeat());
        holder.tv_total.setText(heatMeterReadRecord.getTotal());
        holder.tv_power.setText(heatMeterReadRecord.getPower());
        holder.tv_flowRate.setText(heatMeterReadRecord.getFlowRate());
        holder.tv_closeTime.setText(heatMeterReadRecord.getCloseTime());
        holder.tv_valveStatus.setText(heatMeterReadRecord.getValveStatus());
        holder.tv_t1.setText(heatMeterReadRecord.getT1());
        holder.tv_t2.setText(heatMeterReadRecord.getT2());
        holder.tv_workTime.setText(heatMeterReadRecord.getWorkTime());
        holder.tv_meterTime.setText(heatMeterReadRecord.getMeterTime());
        holder.tv_voltage.setText(heatMeterReadRecord.getVoltage());
        holder.tv_meterStatus.setText(heatMeterReadRecord.getMeterStatus());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_meterId, tv_recordTime, tv_productType, tv_sumCool, tv_sumHeat, tv_total, tv_power, tv_flowRate, tv_closeTime,
                tv_valveStatus, tv_t1, tv_t2, tv_workTime, tv_meterTime, tv_voltage, tv_meterStatus;
    }
}
