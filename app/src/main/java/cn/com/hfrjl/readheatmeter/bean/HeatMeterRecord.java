package cn.com.hfrjl.readheatmeter.bean;

public class HeatMeterRecord {
    private String list1;
    private String list2;
    private String list3;

    public HeatMeterRecord() {
        super();
    }

    public HeatMeterRecord(String list1, String list2, String list3) {
        super();
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    public String getList1() {
        return list1;
    }

    public void setList1(String list1) {
        this.list1 = list1;
    }

    public String getList2() {
        return list2;
    }

    public void setList2(String list2) {
        this.list2 = list2;
    }

    public String getList3() {
        return list3;
    }

    public void setList3(String list3) {
        this.list3 = list3;
    }
}