package cn.com.hfrjl.readheatmeter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.com.hfrjl.readheatmeter.R;
import cn.com.hfrjl.readheatmeter.bean.HeatMeterRecord;

public class MeterDataAdapter extends BaseAdapter {
    private List<HeatMeterRecord> list;
    private LayoutInflater inflater;

    public MeterDataAdapter(Context context, List<HeatMeterRecord> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeatMeterRecord heatMeterRecord = (HeatMeterRecord) this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_read_meter_data, parent, false);
            viewHolder.mTextl1 = (TextView) convertView.findViewById(R.id.text_l1);
            viewHolder.mTextl2 = (TextView) convertView.findViewById(R.id.text_l2);
            viewHolder.mTextl3 = (TextView) convertView.findViewById(R.id.text_l3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextl1.setText(heatMeterRecord.getList1());
        viewHolder.mTextl2.setText(heatMeterRecord.getList2());
        viewHolder.mTextl3.setText(heatMeterRecord.getList3());
        return convertView;
    }

    public static class ViewHolder {
        private TextView mTextl1;
        private TextView mTextl2;
        private TextView mTextl3;

    }

}