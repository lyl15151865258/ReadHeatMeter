package cn.com.hfrjl.readheatmeter.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.com.hfrjl.readheatmeter.bean.HeatMeterReadRecord;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtils {
    private static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(jxl.format.Colour.LIGHT_BLUE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("抄表记录", 0);
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context context) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writableWorkbook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writableWorkbook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writableWorkbook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                    }
                }
                writableWorkbook.write();
                CommonUtils.showToast(context, "导出到手机存储中文件夹HangFa成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writableWorkbook != null) {
                    try {
                        writableWorkbook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static List<HeatMeterReadRecord> read2DB(File f, Context context) {
        ArrayList<HeatMeterReadRecord> heatMeterReadRecordArrayList = new ArrayList<>();
        try {
            Workbook course = Workbook.getWorkbook(f);
            Sheet sheet = course.getSheet(0);
            Cell cell;
            for (int i = 1; i < sheet.getRows(); i++) {
                HeatMeterReadRecord heatMeterReadRecord = new HeatMeterReadRecord();
                cell = sheet.getCell(1, i);
                heatMeterReadRecord.setMeterId(cell.getContents());
                cell = sheet.getCell(2, i);
                heatMeterReadRecord.setRecordTime(cell.getContents());
                cell = sheet.getCell(3, i);
                heatMeterReadRecord.setProductType(cell.getContents());
                cell = sheet.getCell(4, i);
                heatMeterReadRecord.setSumCool(cell.getContents());
                cell = sheet.getCell(5, i);
                heatMeterReadRecord.setSumHeat(cell.getContents());
                cell = sheet.getCell(6, i);
                heatMeterReadRecord.setTotal(cell.getContents());
                cell = sheet.getCell(7, i);
                heatMeterReadRecord.setPower(cell.getContents());
                cell = sheet.getCell(8, i);
                heatMeterReadRecord.setFlowRate(cell.getContents());
                cell = sheet.getCell(9, i);
                heatMeterReadRecord.setCloseTime(cell.getContents());
                cell = sheet.getCell(10, i);
                heatMeterReadRecord.setValveStatus(cell.getContents());
                cell = sheet.getCell(11, i);
                heatMeterReadRecord.setT1(cell.getContents());
                cell = sheet.getCell(12, i);
                heatMeterReadRecord.setT2(cell.getContents());
                cell = sheet.getCell(13, i);
                heatMeterReadRecord.setWorkTime(cell.getContents());
                cell = sheet.getCell(14, i);
                heatMeterReadRecord.setMeterTime(cell.getContents());
                cell = sheet.getCell(15, i);
                heatMeterReadRecord.setVoltage(cell.getContents());
                cell = sheet.getCell(16, i);
                heatMeterReadRecord.setMeterStatus(cell.getContents());
                heatMeterReadRecordArrayList.add(heatMeterReadRecord);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return heatMeterReadRecordArrayList;
    }

    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
                .substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
