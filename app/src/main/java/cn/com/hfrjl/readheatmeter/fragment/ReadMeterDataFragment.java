package cn.com.hfrjl.readheatmeter.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.com.hfrjl.readheatmeter.R;
import cn.com.hfrjl.readheatmeter.activity.MainActivity;
import cn.com.hfrjl.readheatmeter.adapter.MeterDataAdapter;
import cn.com.hfrjl.readheatmeter.bean.HeatMeterRecord;
import cn.com.hfrjl.readheatmeter.bean.Protocol;
import cn.com.hfrjl.readheatmeter.sqlite.HeatMeterRecordSQLiteOpenHelper;
import cn.com.hfrjl.readheatmeter.utils.Analysise;
import cn.com.hfrjl.readheatmeter.utils.ExcelUtils;

public class ReadMeterDataFragment extends BaseFragment {

    private static ArrayList<HeatMeterRecord> heatMeterRecordArrayList = new ArrayList<>();
    private TextView tv_meterId;
    private Button btn_readmeter;
    private ListView listView;
    public Context context;
    public String[] saveData;
    public HeatMeterRecordSQLiteOpenHelper mHeatMeterRecordSQLiteOpenHelper;
    private ArrayList<ArrayList<String>> recordList;
    private String[] title_heatMeter = {"出厂编码", "读表时间", "产品类型", "累计冷量", "累计热量", "累积流量", "制热功率", "瞬时流速",
            "截止日期", "阀门状态", "进水温度", "出水温度", "工作时间", "表具时间", "电池电压", "表具状态"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meterdata_layout, container, false);
        context = getContext();
        initView(view);
        mHeatMeterRecordSQLiteOpenHelper = new HeatMeterRecordSQLiteOpenHelper(context);
        mHeatMeterRecordSQLiteOpenHelper.open();
        recordList = new ArrayList<>();
        return view;
    }

    @Override
    public void initView(View view) {
        tv_meterId = view.findViewById(R.id.tv_meterId);
        btn_readmeter = view.findViewById(R.id.Buttonreadparameter);
        Button btn_exportRecord = view.findViewById(R.id.btn_exportRecord);
        btn_readmeter.setOnClickListener(onClickListener);
        btn_exportRecord.setOnClickListener(onClickListener);
        listView = view.findViewById(R.id.listViewreaddata);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Buttonreadparameter:
                    String tx;
                    if ((btn_readmeter.getText().equals("获取表号"))) {
                        tv_meterId.setText("");
                        MainActivity.smsg = "";
                        tx = "6820AAAAAAAAAAAAAA1A039A2F001416";
                        MainActivity.writeData(tx);
                    }
                    if ((btn_readmeter.getText().equals("读表数据"))) {
                        tv_meterId.setText(tv_meterId.getText().toString().replace(" ", ""));
                        if (tv_meterId.getText().toString().length() != 8) {
                            btn_readmeter.setText("获取表号");
                        } else {
                            tx = readmeter(tv_meterId.getText().toString(), "20", "001111");
                            MainActivity.smsg = "";
                            MainActivity.writeData(tx);
                        }
                    }
                    break;
                case R.id.btn_exportRecord:
                    exportToExcel(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA).format(new Date()) + "抄表记录");
                    mHeatMeterRecordSQLiteOpenHelper.deleteTable();
                    recordList.clear();
                    heatMeterRecordArrayList.clear();
                    MeterDataAdapter adapter = new MeterDataAdapter(context, heatMeterRecordArrayList);
                    listView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    public String readmeter(String meterid, String producttypetx, String factorycode) {
        String r;
        Analysise analysise = new Analysise();
        String cs = analysise.getsum("68" + producttypetx + meterid + factorycode + "0103901F00", 0);
        r = "68" + producttypetx + meterid + factorycode + "0103901F00" + cs + "16";
        return r;
    }

    @SuppressLint("SimpleDateFormat")
    public void addListView(Protocol protocol) {
        String showReport = "";
        if (!protocol.getMeterID().equals("")) {
            String meterId = protocol.getMeterID();
            String currentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            if (protocol.getProductTypeTX().equals("20")) {
                showReport = showReport + "产品类型:" + protocol.getProductTypeTX() + " ";
                showReport = showReport + "累计冷量:" + protocol.getSumCool() + " " + protocol.getSumCoolUnit() + " ";
                showReport = showReport + "累计热量:" + protocol.getSumHeat() + " " + protocol.getSumHeatUnit() + " ";
                showReport = showReport + "累计流量:" + protocol.getTotal() + " " + protocol.getTotalUnit() + " ";
                showReport = showReport + "制热功率:" + protocol.getPower() + " " + protocol.getPowerUnit() + " ";
                showReport = showReport + "瞬时流速:" + protocol.getFlowRate() + " " + protocol.getFlowRateUnit() + " ";
                showReport = showReport + "截止日期:" + protocol.getCloseTime() + " ";
                showReport = showReport + "阀门状态:" + protocol.getValveStatus() + " ";
                showReport = showReport + "T1:" + protocol.getT1InP() + " " + "℃" + " ";
                showReport = showReport + "T2:" + protocol.getT2InP() + " " + "℃" + " ";
                showReport = showReport + "工作时间:" + protocol.getWorkTimeInP() + " " + "h" + " ";
                showReport = showReport + "表具时间:" + protocol.getTimeInP() + " ";
                showReport = showReport + "电池电压:" + protocol.getVol() + " " + "V" + " ";
                showReport = showReport + "表具状态:" + protocol.getStatus();
                saveData = new String[]{meterId, currentTime, protocol.getProductTypeTX(),
                        protocol.getSumCool() + " " + protocol.getSumCoolUnit(), protocol.getSumHeat() + " " + protocol.getSumHeatUnit(),
                        protocol.getTotal() + " " + protocol.getTotalUnit(), protocol.getPower() + " " + protocol.getPowerUnit(),
                        protocol.getFlowRate() + " " + protocol.getFlowRateUnit(), protocol.getCloseTime(), protocol.getValveStatus(),
                        protocol.getT1InP() + " " + "℃", protocol.getT2InP() + " " + "℃", protocol.getWorkTimeInP() + " " + "h",
                        protocol.getTimeInP(), protocol.getVol() + " " + "V", protocol.getStatus()};
                if (canSave(saveData)) {
                    ContentValues values = new ContentValues();
                    values.put("meterId", meterId);
                    values.put("recordTime", currentTime);
                    values.put("productType", protocol.getProductTypeTX());
                    values.put("sumCool", protocol.getSumCool() + " " + protocol.getSumCoolUnit());
                    values.put("sumHeat", protocol.getSumHeat() + " " + protocol.getSumHeatUnit());
                    values.put("total", protocol.getTotal() + " " + protocol.getTotalUnit());
                    values.put("power", protocol.getPower() + " " + protocol.getPowerUnit());
                    values.put("flowRate", protocol.getFlowRate() + " " + protocol.getFlowRateUnit());
                    values.put("closeTime", protocol.getCloseTime());
                    values.put("valveStatus", protocol.getValveStatus());
                    values.put("t1", protocol.getT1InP() + " " + "℃");
                    values.put("t2", protocol.getT2InP() + " " + "℃");
                    values.put("workTime", protocol.getWorkTimeInP() + " " + "h");
                    values.put("meterTime", protocol.getTimeInP());
                    values.put("voltage", protocol.getVol() + " " + "V");
                    values.put("meterStatus", protocol.getStatus());
                    mHeatMeterRecordSQLiteOpenHelper.insert("heatMeter_record", values);
                }
            }
            heatMeterRecordArrayList.add(0, new HeatMeterRecord(meterId, currentTime, showReport));
        }
        MeterDataAdapter adapter = new MeterDataAdapter(context, heatMeterRecordArrayList);
        listView.setAdapter(adapter);
    }

    //导出到excel
    public void exportToExcel(String name) {
        File file = new File(getSDPath() + "/HangFa");
        if (!file.exists()) {
            makeDir(file);
        }
        ExcelUtils.initExcel(file.toString() + "/" + name + ".xls", title_heatMeter);
        ExcelUtils.writeObjListToExcel(getHeatMeterRecordData(), getSDPath() + "/HangFa/" + name + ".xls", context);
    }

    private ArrayList<ArrayList<String>> getHeatMeterRecordData() {
        Cursor mCursor = mHeatMeterRecordSQLiteOpenHelper.exeSql("select * from heatMeter_record");
        while (mCursor.moveToNext()) {
            ArrayList<String> beanList = new ArrayList<>();
            beanList.add(mCursor.getString(1));
            beanList.add(mCursor.getString(2));
            beanList.add(mCursor.getString(3));
            beanList.add(mCursor.getString(4));
            beanList.add(mCursor.getString(5));
            beanList.add(mCursor.getString(6));
            beanList.add(mCursor.getString(7));
            beanList.add(mCursor.getString(8));
            beanList.add(mCursor.getString(9));
            beanList.add(mCursor.getString(10));
            beanList.add(mCursor.getString(11));
            beanList.add(mCursor.getString(12));
            beanList.add(mCursor.getString(13));
            beanList.add(mCursor.getString(14));
            beanList.add(mCursor.getString(15));
            beanList.add(mCursor.getString(16));
            recordList.add(beanList);
        }
        mCursor.close();
        return recordList;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public String getSDPath() {
        File sdDir;
        String dir = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            dir = sdDir.toString();
        }
        return dir;

    }

    private boolean canSave(String[] data) {
        boolean isOk = false;
        for (int i = 0; i < data.length; i++) {
            if (i > 0 && i < data.length) {
                if (!TextUtils.isEmpty(data[i])) {
                    isOk = true;
                }
            }
        }
        return isOk;
    }

}
