package cn.com.hfrjl.readheatmeter.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LiYuliang on 17/6/5.
 * 蓝牙工具抄热表的数据库
 */

public class HeatMeterRecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "record.db";
    private Context mContext;
    private HeatMeterRecordSQLiteOpenHelper mHeatMeterRecordSQLiteOpenHelper;
    private SQLiteDatabase db;

    public HeatMeterRecordSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 11);
        this.mContext = context;
    }

    public HeatMeterRecordSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 用户第一次使用软件时调用的操作，用于获取数据库创建语句（SW）,然后创建数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists heatMeter_record(id integer primary key,meterId text,recordTime text,productType text,sumCool text,sumHeat text,total text,power text,flowRate text,closeTime text,valveStatus text,t1 text,t2 text,workTime text,meterTime text,voltage text,meterStatus text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /* 打开数据库,如果已经打开就使用，否则创建 */
    public HeatMeterRecordSQLiteOpenHelper open() {
        if (null == mHeatMeterRecordSQLiteOpenHelper) {
            mHeatMeterRecordSQLiteOpenHelper = new HeatMeterRecordSQLiteOpenHelper(mContext);
        }
        db = mHeatMeterRecordSQLiteOpenHelper.getWritableDatabase();
        return this;
    }

    /* 关闭数据库 */
    public void close() {
        db.close();
    }

    /**
     * 添加数据
     */
    public void insert(String tableName, ContentValues values) {
        db.insert(tableName, null, values);
    }

    /**
     * 查询数据
     */
    public Cursor findList(String tableName, String[] columns,
                           String selection, String[] selectionArgs, String groupBy,
                           String having, String orderBy, String limit) {
        return db.query(tableName, columns, selection, selectionArgs, groupBy,
                having, orderBy, limit);
    }

    public Cursor exeSql(String sql) {
        return db.rawQuery(sql, null);
    }

    public void deleteTable() {
        db.delete("heatMeter_record", null, null);
//        db.delete("heatMeter_record", "id>?", new String[]{"-1"});
//        db.close();
    }

}