package cn.com.hfrjl.readheatmeter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.hfrjl.readheatmeter.R;
import cn.com.hfrjl.readheatmeter.activity.MainActivity;
import cn.com.hfrjl.readheatmeter.utils.TDevice;

public class ShowMeterLcdFragment extends BaseFragment {

    @BindView(R.id.lv_showLcd)
    ListView lv_showLcd;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meterlcd_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        String[] itemName = { this.getString(R.string.lcd_meterid), this.getString(R.string.lcd_time),this.getString(R.string.lcd_flowcoef),this.getString(R.string.lcd_flowrate) ,
                this.getString(R.string.lcd_total), this.getString(R.string.lcd_static),this.getString(R.string.lcd_sleeptimeandvol),this.getString(R.string.lcd_startandsize) ,
                this.getString(R.string.lcd_slopeandamend), this.getString(R.string.lcd_ver),this.getString(R.string.lcd_t1),this.getString(R.string.lcd_t2) ,
                this.getString(R.string.lcd_A1), this.getString(R.string.lcd_A2),this.getString(R.string.lcd_A3) };
        List<HashMap<String, Object>> data = new ArrayList<>();
        int length = itemName.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTextView", itemName[i]);
            data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                data, R.layout.lcd_item, new String[] {"ItemTextView" }, new int[] {R.id.ItemTextView });
        lv_showLcd.setAdapter(simpleAdapter);
        lv_showLcd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String tx;
                switch (arg2) {
                    case 0:
                        tx="6820AAAAAAAAAAAAAA1A039A4F003416";
                        MainActivity.writeData(tx);
                        break;
                    case 1:
                        tx="6820AAAAAAAAAAAAAA1A039A1F000416";
                        MainActivity.writeData(tx);
                        break;
                    case 2:
                        tx="6820AAAAAAAAAAAAAA1A039A3F002416";
                        MainActivity.writeData(tx);
                        break;
                    case 3:
                        tx="6820AAAAAAAAAAAAAA1A039AAF009416";
                        MainActivity.writeData(tx);
                        break;
                    case 4:
                        tx="6820AAAAAAAAAAAAAA1A039A7F006416";
                        MainActivity.writeData(tx);
                        break;
                    case 5:
                        tx="6820AAAAAAAAAAAAAA1A039A5F004416";
                        MainActivity.writeData(tx);
                        break;
                    case 6:
                        tx="6820AAAAAAAAAAAAAA1A039AE300C816";
                        MainActivity.writeData(tx);
                        break;
                    case 7:
                        tx="6820AAAAAAAAAAAAAA1A039ACF00B416";
                        MainActivity.writeData(tx);
                        break;
                    case 8:
                        tx="6820AAAAAAAAAAAAAA1A039AE200C716";
                        MainActivity.writeData(tx);
                        break;
                    case 9:
                        tx="6820AAAAAAAAAAAAAA1A039A6F005416";
                        MainActivity.writeData(tx);
                        break;
                    case 10:
                        tx="6820AAAAAAAAAAAAAA1A039ADF00C416";
                        MainActivity.writeData(tx);
                        break;
                    case 11:
                        tx="6820AAAAAAAAAAAAAA1A039AEF00D416";
                        MainActivity.writeData(tx);
                        break;
                    case 12:
                        tx="6820AAAAAAAAAAAAAA3E03901F001E16";
                        MainActivity.writeData(tx);
                        break;
                    case 13:
                        tx="6820AAAAAAAAAAAAAA3E03902F002E16";
                        MainActivity.writeData(tx);
                        break;
                    case 14:
                        tx="6820AAAAAAAAAAAAAA3E03903F003E16";
                        MainActivity.writeData(tx);
                        break;
                    default: break;
                }
            }
        });

        return view;
    }
}
