package cn.com.hfrjl.readheatmeter.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.hfrjl.readheatmeter.bean.MBUS;
import cn.com.hfrjl.readheatmeter.bean.ParameterProtocol;
import cn.com.hfrjl.readheatmeter.bean.Protocol;
import cn.com.hfrjl.readheatmeter.bean.SSumHeat;

@SuppressLint("DefaultLocale")
public class Analysise {
    private static int HexS1ToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

    private static int HexS2ToInt(String S) {
        int r = 0;
        char a[] = S.toCharArray();
        r = HexS1ToInt(a[0]) * 16 + HexS1ToInt(a[1]);
        return r;
    }

    public String getsum(String S, int Start) {
        String r = "";
        int sumL = 0;
        for (int i = (Start * 2); i < S.length(); i = i + 2) {
            sumL = (sumL + HexS2ToInt(S.substring(i, i + 2))) % 256;
        }
        r = Integer.toHexString(sumL / 16) + Integer.toHexString(sumL % 16);
        r = r.toUpperCase();
        return r;
    }

    @SuppressLint("DefaultLocale")
    public static int checksum(String S, int Start, String checksumS) {
        int r = 0;
        int sumL = 0;
        for (int i = (Start * 2); i < S.length(); i = i + 2) {
            sumL = (sumL + HexS2ToInt(S.substring(i, i + 2))) % 256;
        }
        if ((Integer.toHexString(sumL / 16) + Integer.toHexString(sumL % 16)).toUpperCase().equals(checksumS))
            r = 1;
        return r;
    }

    public static int dataunittostandard(Protocol protocol) {
        int r = 1;
        BigDecimal b = null;
        double f1 = 0;
        try {
            double SumCool = protocol.getSumCool();
            protocol.setSumCool(0);
            r = 0;
            switch (protocol.getSumCoolUnit()) {
                case "W*h":
                    b = new BigDecimal(SumCool / 1000);
                    break;
                case "10W*h":
                    b = new BigDecimal(SumCool / 100);
                    break;
                case "100W*h":
                    b = new BigDecimal(SumCool / 10);
                    break;
                case "kW*h":
                    b = new BigDecimal(SumCool);
                    break;
                case "MW*h":
                    b = new BigDecimal(SumCool * 1000);
                    break;
                case "J":
                    b = new BigDecimal(SumCool * 0.0000002778);
                    break;
                case "10J":
                    b = new BigDecimal(SumCool * 0.000002778);
                    break;
                case "100J":
                    b = new BigDecimal(SumCool * 0.00002778);
                    break;
                case "kJ":
                    b = new BigDecimal(SumCool * 0.0002778);
                    break;
                case "10kJ":
                    b = new BigDecimal(SumCool * 0.002778);
                    break;
                case "100kJ":
                    b = new BigDecimal(SumCool * 0.02778);
                    break;
                case "MJ":
                    b = new BigDecimal(SumCool * 0.2778);
                    break;
                case "10MJ":
                    b = new BigDecimal(SumCool * 2.778);
                    break;
                case "100MJ":
                    b = new BigDecimal(SumCool * 27.78);
                    break;
                case "GJ":
                    b = new BigDecimal(SumCool * 277.8);
                    break;
                case "10GJ":
                    b = new BigDecimal(SumCool * 2778);
                    break;
                case "100GJ":
                    b = new BigDecimal(SumCool * 27778);
                    break;
            }
            f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            protocol.setSumCool(f1);
            protocol.setSumCoolUnit("kW*h");

            double SumHeat = protocol.getSumHeat();
            protocol.setSumHeat(0);
            r = 0;
            if (protocol.getSumHeatUnit().equals("W*h")) {
                b = new BigDecimal(Double.valueOf(SumHeat) / 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("10W*h")) {
                b = new BigDecimal(Double.valueOf(SumHeat) / 100);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("100W*h")) {
                b = new BigDecimal(Double.valueOf(SumHeat) / 10);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("kW*h")) {
                b = new BigDecimal(Double.valueOf(SumHeat));
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("MW*h")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("J")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.0000002778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("10J")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.000002778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("100J")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.00002778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("kJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.0002778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("10kJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.002778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("100kJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.02778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("MJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 0.2778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("10MJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 2.778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("100MJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 27.78);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("GJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 277.8);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("10GJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 2778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            } else if (protocol.getSumHeatUnit().equals("100GJ")) {
                b = new BigDecimal(Double.valueOf(SumHeat) * 27778);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setSumHeat(f1);
                r = 1;
            }
            protocol.setSumHeatUnit("kW*h");

            double Total = protocol.getTotal();
            protocol.setTotal(0);
            r = 0;
            if (protocol.getTotalUnit().equals("mL")) {
                b = new BigDecimal(Double.valueOf(Total) / 1000000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("10mL")) {
                b = new BigDecimal(Double.valueOf(Total) / 100000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("100mL")) {
                b = new BigDecimal(Double.valueOf(Total) / 10000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("L")) {
                b = new BigDecimal(Double.valueOf(Total) / 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("10L")) {
                b = new BigDecimal(Double.valueOf(Total) / 100);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("100L")) {
                b = new BigDecimal(Double.valueOf(Total) / 10);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            } else if (protocol.getTotalUnit().equals("m³")) {
                b = new BigDecimal(Double.valueOf(Total));
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setTotal(f1);
                r = 1;
            }
            protocol.setTotalUnit("m³");

            double OppositeTotal = protocol.getOppositeTotal();
            protocol.setOppositeTotal(0);
            r = 0;
            if (protocol.getOppositeTotalUnit().equals("mL")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 1000000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("10mL")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 100000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("100mL")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 10000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("L")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("10L")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 100);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("100L")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal) / 10);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            } else if (protocol.getOppositeTotalUnit().equals("m³")) {
                b = new BigDecimal(Double.valueOf(OppositeTotal));
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setOppositeTotal(f1);
                r = 1;
            }
            protocol.setOppositeTotalUnit("m³");

            double Power = protocol.getPower();
            protocol.setPower(0);
            r = 0;
            if (protocol.getPowerUnit().equals("W")) {
                b = new BigDecimal(Double.valueOf(Power) / 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("10W")) {
                b = new BigDecimal(Double.valueOf(Power) / 100);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("100W")) {
                b = new BigDecimal(Double.valueOf(Power) / 10);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("kW")) {
                b = new BigDecimal(Double.valueOf(Power));
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            }
            if (protocol.getPowerUnit().equals("10kW")) {
                b = new BigDecimal(Double.valueOf(Power) * 10);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("100kW")) {
                b = new BigDecimal(Double.valueOf(Power) * 100);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("MW")) {
                b = new BigDecimal(Double.valueOf(Power) * 1000);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            } else if (protocol.getPowerUnit().equals("GJ/h")) {
                b = new BigDecimal(Double.valueOf(Power) * 1000 / 3.6);
                f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setPower(f1);
                r = 1;
            }
            protocol.setPowerUnit("kW");

            double FlowRate = protocol.getFlowRate();
            protocol.setFlowRate(0);
            r = 0;
            if (protocol.getFlowRateUnit().equals("mL/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 1000000);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("10mL/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 100000);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("100mL/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 10000);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("L/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 1000);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            }
            if (protocol.getFlowRateUnit().equals("10L/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 100);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("100L/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) / 10);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("m³/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate));
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("10m³/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) * 10);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            } else if (protocol.getFlowRateUnit().equals("100m³/h")) {
                b = new BigDecimal(Double.valueOf(FlowRate) * 100);
                f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                protocol.setFlowRate(f1);
                r = 1;
            }
            protocol.setFlowRateUnit("m³/h");
            r = 1;
        } catch (Exception e) {
            r = 0;
        }
        return r;
    }

    //用于解析的方法
    private static void initProtocol(Protocol protocol) {
        protocol.setMeterID("-");
        protocol.setMBUSAddress("-");
        protocol.setProductTypeTX("20");
        protocol.setSumCool(0);
        protocol.setSumCoolUnit("kW*h");
        protocol.setSumHeat(0);
        protocol.setSumHeatUnit("kW*h");
        protocol.setTotal(0);
        protocol.setTotalUnit("m³");
        protocol.setOppositeTotal(0);
        protocol.setOppositeTotalUnit("m³");
        protocol.setPower(0);
        protocol.setPowerUnit("kW");
        protocol.setFlowRate(0);
        protocol.setFlowRateUnit("m³/h");
        protocol.setSumOpenValveM(0);
        protocol.setCloseTime("-");
        protocol.setLosePowerTime("-");
        protocol.setLoseConTime("-");
        protocol.setInsideT(0);
        protocol.setInsideTSet("-");
        protocol.setValveStatus("-");
        protocol.setT1InP(0);
        protocol.setT2InP(0);
        protocol.setWorkTimeInP(0);
        protocol.setTimeInP("-");
        protocol.setVol("-");
        protocol.setStatus("-");
    }

    private static int MCAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 94);
        DecimalFormat df = new DecimalFormat(".##");
        try {
            double volF = Frequent.HexS2ToInt(DataArea.substring(4, 4 + 2)) * 2 / 100f;
            protocol.setVol(df.format(volF));
            r = 1;
        } catch (Exception e) {
            protocol.setVol("err 电压 " + DataArea.substring(4, 4 + 2));
            r = 0;
        }
        if (r == 0) return r;

        try {
            String coolS = DataArea.substring(12, 12 + 2) + DataArea.substring(10, 10 + 2) + DataArea.substring(8, 8 + 2) + DataArea.substring(6, 6 + 2);
            Integer.valueOf(coolS);
            protocol.setSumCool(Double.valueOf(coolS));
            r = 1;
        } catch (Exception e) {
            protocol.setSumCool(0);
            r = 0;
        }
        if (DataArea.substring(14, 14 + 2).equals("02")) protocol.setSumCoolUnit("W*h");
        else if (DataArea.substring(14, 14 + 2).equals("03")) protocol.setSumCoolUnit("10W*h");
        else if (DataArea.substring(14, 14 + 2).equals("04")) protocol.setSumCoolUnit("100W*h");
        else if (DataArea.substring(14, 14 + 2).equals("05")) protocol.setSumCoolUnit("kW*h");
        else if (DataArea.substring(14, 14 + 2).equals("0B")) protocol.setSumCoolUnit("kJ");
        else if (DataArea.substring(14, 14 + 2).equals("0C")) protocol.setSumCoolUnit("10kJ");
        else if (DataArea.substring(14, 14 + 2).equals("0D")) protocol.setSumCoolUnit("100kJ");
        else if (DataArea.substring(14, 14 + 2).equals("0E")) protocol.setSumCoolUnit("MJ");
        else if (DataArea.substring(14, 14 + 2).equals("0F")) protocol.setSumCoolUnit("10MJ");
        else if (DataArea.substring(14, 14 + 2).equals("10")) protocol.setSumCoolUnit("100MJ");
        else if (DataArea.substring(14, 14 + 2).equals("11")) protocol.setSumCoolUnit("GJ");
        else if (DataArea.substring(14, 14 + 2).equals("12")) protocol.setSumCoolUnit("10GJ");
        else if (DataArea.substring(14, 14 + 2).equals("13")) protocol.setSumCoolUnit("100GJ");
        else protocol.setSumCoolUnit("err " + DataArea.substring(14, 14 + 2));
        if (r == 0) return r;

        try {
            String heatS = DataArea.substring(22, 22 + 2) + DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(heatS);
            protocol.setSumHeat(Double.valueOf(heatS));
            r = 1;
        } catch (Exception e) {
            protocol.setSumHeat(0);
            r = 0;
        }
        if (DataArea.substring(24, 24 + 2).equals("02")) protocol.setSumHeatUnit("W*h");
        else if (DataArea.substring(24, 24 + 2).equals("03")) protocol.setSumHeatUnit("10W*h");
        else if (DataArea.substring(24, 24 + 2).equals("04")) protocol.setSumHeatUnit("100W*h");
        else if (DataArea.substring(24, 24 + 2).equals("05")) protocol.setSumHeatUnit("kW*h");
        else if (DataArea.substring(24, 24 + 2).equals("0B")) protocol.setSumHeatUnit("kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0C")) protocol.setSumHeatUnit("10kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0D")) protocol.setSumHeatUnit("100kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0E")) protocol.setSumHeatUnit("MJ");
        else if (DataArea.substring(24, 24 + 2).equals("0F")) protocol.setSumHeatUnit("10MJ");
        else if (DataArea.substring(24, 24 + 2).equals("10")) protocol.setSumHeatUnit("100MJ");
        else if (DataArea.substring(24, 24 + 2).equals("11")) protocol.setSumHeatUnit("GJ");
        else if (DataArea.substring(24, 24 + 2).equals("12")) protocol.setSumHeatUnit("10GJ");
        else if (DataArea.substring(24, 24 + 2).equals("13")) protocol.setSumHeatUnit("100GJ");
        else protocol.setSumHeatUnit("kW*h");
        if (r == 0) return r;

        try {
            String powerS = DataArea.substring(32, 32 + 2) + DataArea.substring(30, 30 + 2) + DataArea.substring(28, 28 + 2) + DataArea.substring(26, 26 + 2);
            Integer.valueOf(powerS);
            protocol.setPower(Double.valueOf(powerS));
            r = 1;
        } catch (Exception e) {
            protocol.setPower(0);
            r = 0;
        }
        if (DataArea.substring(34, 34 + 2).equals("14")) protocol.setPowerUnit("W");
        else if (DataArea.substring(34, 34 + 2).equals("15")) protocol.setPowerUnit("10W");
        else if (DataArea.substring(34, 34 + 2).equals("16")) protocol.setPowerUnit("100W");
        else if (DataArea.substring(34, 34 + 2).equals("17")) protocol.setPowerUnit("kW");
        else protocol.setPowerUnit("kW");
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(42, 42 + 2) + DataArea.substring(40, 40 + 2) + DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2);
            Integer.valueOf(flowrateS);
            protocol.setFlowRate(Double.valueOf(flowrateS));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        if (DataArea.substring(44, 44 + 2).equals("32")) protocol.setFlowRateUnit("L/h");
        else if (DataArea.substring(44, 44 + 2).equals("33")) protocol.setFlowRateUnit("10L/h");
        else if (DataArea.substring(44, 44 + 2).equals("34")) protocol.setFlowRateUnit("100L/h");
        else if (DataArea.substring(44, 44 + 2).equals("35")) protocol.setFlowRateUnit("m³/h");
        else protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        try {
            String totalS = DataArea.substring(52, 52 + 2) + DataArea.substring(50, 50 + 2) + DataArea.substring(48, 48 + 2) + DataArea.substring(46, 46 + 2);
            Integer.valueOf(totalS);
            protocol.setTotal(Double.valueOf(totalS));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        if (DataArea.substring(54, 54 + 2).equals("26")) protocol.setTotalUnit("mL");
        else if (DataArea.substring(54, 54 + 2).equals("27")) protocol.setTotalUnit("10mL");
        else if (DataArea.substring(54, 54 + 2).equals("28")) protocol.setTotalUnit("100mL");
        else if (DataArea.substring(54, 54 + 2).equals("29")) protocol.setTotalUnit("L");
        else if (DataArea.substring(54, 54 + 2).equals("2A")) protocol.setTotalUnit("10L");
        else if (DataArea.substring(54, 54 + 2).equals("2B")) protocol.setTotalUnit("100L");
        else if (DataArea.substring(54, 54 + 2).equals("2C")) protocol.setTotalUnit("m³");
        else protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String t1S = DataArea.substring(60, 60 + 2) + DataArea.substring(58, 58 + 2) + "." + DataArea.substring(56, 56 + 2);
            Double.valueOf(t1S);
            protocol.setT1InP(Double.valueOf(t1S));
            r = 1;
        } catch (Exception e) {
            protocol.setT1InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t2S = DataArea.substring(66, 66 + 2) + DataArea.substring(64, 64 + 2) + "." + DataArea.substring(62, 62 + 2);
            Double.valueOf(t2S);
            protocol.setT2InP(Double.valueOf(t2S));
            r = 1;
        } catch (Exception e) {
            protocol.setT2InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String worktimeinpS = DataArea.substring(72, 72 + 2) + DataArea.substring(70, 70 + 2) + DataArea.substring(68, 68 + 2);
            Integer.valueOf(worktimeinpS);
            protocol.setWorkTimeInP(Integer.valueOf(worktimeinpS));
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        String timeinpS = DataArea.substring(86, 86 + 2) + DataArea.substring(84, 84 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(80, 80 + 2) + " " + DataArea.substring(78, 78 + 2) + ":" + DataArea.substring(76, 76 + 2) + ":" + DataArea.substring(74, 74 + 2);
        protocol.setTimeInP(timeinpS);
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(90, 90 + 2));
        String status = "";
        char a[] = statusb.toCharArray();
        if (a[2] == '1') {
            status = status + "低电";
        } else if (a[3] == '1') {
            status = status + "流量故障";
        } else if (a[4] == '1') {
            status = status + "PCB故障";
        } else if (a[5] == '1') {
            status = status + "无水";
        } else if (a[6] == '1') {
            status = status + "铂电阻断路";
        } else if (a[7] == '1') {
            status = status + "铂电阻短路";
        }
        if (a[0] == '0' & a[1] == '0' & a[2] == '0' & a[3] == '0' & a[4] == '0' & a[5] == '0' & a[6] == '0' & a[7] == '0')
            status = "正常";
        protocol.setStatus(status);
        return r;
    }

    private static int WCAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 46);

        try {
            String totalS = DataArea.substring(12, 12 + 2) + DataArea.substring(10, 10 + 2) + DataArea.substring(8, 8 + 2) + DataArea.substring(6, 6 + 2);
            Integer.valueOf(totalS);
            protocol.setTotal(Double.valueOf(totalS));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        if (DataArea.substring(14, 14 + 2).equals("26")) protocol.setTotalUnit("mL");
        else if (DataArea.substring(14, 14 + 2).equals("27")) protocol.setTotalUnit("10mL");
        else if (DataArea.substring(14, 14 + 2).equals("28")) protocol.setTotalUnit("100mL");
        else if (DataArea.substring(14, 14 + 2).equals("29")) protocol.setTotalUnit("L");
        else if (DataArea.substring(14, 14 + 2).equals("2A")) protocol.setTotalUnit("10L");
        else if (DataArea.substring(14, 14 + 2).equals("2B")) protocol.setTotalUnit("100L");
        else if (DataArea.substring(14, 14 + 2).equals("2C")) protocol.setTotalUnit("m³");
        else protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(22, 22 + 2) + DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(flowrateS);
            protocol.setFlowRate(Double.valueOf(flowrateS));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        if (DataArea.substring(24, 24 + 2).equals("32")) protocol.setFlowRateUnit("L/h");
        else if (DataArea.substring(24, 24 + 2).equals("33")) protocol.setFlowRateUnit("10L/h");
        else if (DataArea.substring(24, 24 + 2).equals("34")) protocol.setFlowRateUnit("100L/h");
        else if (DataArea.substring(24, 24 + 2).equals("35")) protocol.setFlowRateUnit("m³/h");
        else protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        String timeinpS = DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2) + "-" + DataArea.substring(34, 34 + 2) + "-" + DataArea.substring(32, 32 + 2) + " " + DataArea.substring(30, 30 + 2) + ":" + DataArea.substring(28, 28 + 2) + ":" + DataArea.substring(26, 26 + 2);
        protocol.setTimeInP(timeinpS);
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(40, 40 + 2));
        String status = "";
        char a[] = statusb.toCharArray();
        if (a[2] == '1') {
            status = status + "低电";
        } else if (a[3] == '1') {
            status = status + "流量故障";
        } else if (a[4] == '1') {
            status = status + "PCB故障";
        } else if (a[5] == '1') {
            status = status + "无水";
        } else if (a[6] == '1') {
            status = status + "铂电阻断路";
        } else if (a[7] == '1') {
            status = status + "铂电阻短路";
        }
        if (a[0] == '0' & a[1] == '0' & a[2] == '0' & a[3] == '0' & a[4] == '0' & a[5] == '0' & a[6] == '0' & a[7] == '0')
            status = "正常";
        protocol.setStatus(status);

        String valveStatusS = DataArea.substring(42, 42 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("-");

        return r;
    }

    private static int WCAnalysisJSMTRxStr1(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 56);

        try {
            String totalS = DataArea.substring(12, 12 + 2) + DataArea.substring(10, 10 + 2) + DataArea.substring(8, 8 + 2) + DataArea.substring(6, 6 + 2);
            Integer.valueOf(totalS);
            protocol.setTotal(Double.valueOf(totalS));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        if (DataArea.substring(14, 14 + 2).equals("26")) protocol.setTotalUnit("mL");
        else if (DataArea.substring(14, 14 + 2).equals("27")) protocol.setTotalUnit("10mL");
        else if (DataArea.substring(14, 14 + 2).equals("28")) protocol.setTotalUnit("100mL");
        else if (DataArea.substring(14, 14 + 2).equals("29")) protocol.setTotalUnit("L");
        else if (DataArea.substring(14, 14 + 2).equals("2A")) protocol.setTotalUnit("10L");
        else if (DataArea.substring(14, 14 + 2).equals("2B")) protocol.setTotalUnit("100L");
        else if (DataArea.substring(14, 14 + 2).equals("2C")) protocol.setTotalUnit("m³");
        else protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String oppositetotalS = DataArea.substring(22, 22 + 2) + DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(oppositetotalS);
            protocol.setOppositeTotal(Double.valueOf(oppositetotalS));
            r = 1;
        } catch (Exception e) {
            protocol.setOppositeTotal(0);
            r = 0;
        }
        if (DataArea.substring(24, 24 + 2).equals("26")) protocol.setOppositeTotalUnit("mL");
        else if (DataArea.substring(24, 24 + 2).equals("27")) protocol.setOppositeTotalUnit("10mL");
        else if (DataArea.substring(24, 24 + 2).equals("28"))
            protocol.setOppositeTotalUnit("100mL");
        else if (DataArea.substring(24, 24 + 2).equals("29")) protocol.setOppositeTotalUnit("L");
        else if (DataArea.substring(24, 24 + 2).equals("2A")) protocol.setOppositeTotalUnit("10L");
        else if (DataArea.substring(24, 24 + 2).equals("2B")) protocol.setOppositeTotalUnit("100L");
        else if (DataArea.substring(24, 24 + 2).equals("2C")) protocol.setOppositeTotalUnit("m³");
        else protocol.setOppositeTotalUnit("m³");
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(32, 32 + 2) + DataArea.substring(30, 30 + 2) + DataArea.substring(28, 28 + 2) + DataArea.substring(26, 26 + 2);
            Integer.valueOf(flowrateS);
            protocol.setFlowRate(Double.valueOf(flowrateS));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        if (DataArea.substring(34, 34 + 2).equals("32")) protocol.setFlowRateUnit("L/h");
        else if (DataArea.substring(34, 34 + 2).equals("33")) protocol.setFlowRateUnit("10L/h");
        else if (DataArea.substring(34, 34 + 2).equals("34")) protocol.setFlowRateUnit("100L/h");
        else if (DataArea.substring(34, 34 + 2).equals("35")) protocol.setFlowRateUnit("m³/h");
        else protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        String timeinpS = DataArea.substring(48, 48 + 2) + DataArea.substring(46, 46 + 2) + "-" + DataArea.substring(44, 44 + 2) + "-" + DataArea.substring(42, 42 + 2) + " " + DataArea.substring(40, 40 + 2) + ":" + DataArea.substring(38, 38 + 2) + ":" + DataArea.substring(36, 36 + 2);
        protocol.setTimeInP(timeinpS);
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(50, 50 + 2));
        String status = "";
        char a[] = statusb.toCharArray();
        if (a[2] == '1') {
            status = status + "低电";
        } else if (a[3] == '1') {
            status = status + "流量故障";
        } else if (a[4] == '1') {
            status = status + "PCB故障";
        } else if (a[5] == '1') {
            status = status + "无水";
        } else if (a[6] == '1') {
            status = status + "铂电阻断路";
        } else if (a[7] == '1') {
            status = status + "铂电阻短路";
        }
        if (a[0] == '0' & a[1] == '0' & a[2] == '0' & a[3] == '0' & a[4] == '0' & a[5] == '0' & a[6] == '0' & a[7] == '0')
            status = "正常";
        protocol.setStatus(status);

        String valveStatusS = DataArea.substring(52, 52 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("-");

        return r;
    }

    private static int MCAnalysisSDSLRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 2) + Data.substring(6, 6 + 2) + Data.substring(8, 8 + 2) + Data.substring(10, 10 + 2));
        String DataArea = Data.substring(22, 22 + 94);

        try {
            String heatS = DataArea.substring(22, 22 + 2) + DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(heatS);
            protocol.setSumHeat(Double.valueOf(heatS));
            r = 1;
        } catch (Exception e) {
            protocol.setSumHeat(0);
            r = 0;
        }
        if (DataArea.substring(24, 24 + 2).equals("02")) protocol.setSumHeatUnit("W*h");
        else if (DataArea.substring(24, 24 + 2).equals("03")) protocol.setSumHeatUnit("10W*h");
        else if (DataArea.substring(24, 24 + 2).equals("04")) protocol.setSumHeatUnit("100W*h");
        else if (DataArea.substring(24, 24 + 2).equals("05")) protocol.setSumHeatUnit("kW*h");
        else if (DataArea.substring(24, 24 + 2).equals("0B")) protocol.setSumHeatUnit("kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0C")) protocol.setSumHeatUnit("10kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0D")) protocol.setSumHeatUnit("100kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0E")) protocol.setSumHeatUnit("MJ");
        else if (DataArea.substring(24, 24 + 2).equals("0F")) protocol.setSumHeatUnit("10MJ");
        else if (DataArea.substring(24, 24 + 2).equals("10")) protocol.setSumHeatUnit("100MJ");
        else if (DataArea.substring(24, 24 + 2).equals("11")) protocol.setSumHeatUnit("GJ");
        else if (DataArea.substring(24, 24 + 2).equals("12")) protocol.setSumHeatUnit("10GJ");
        else if (DataArea.substring(24, 24 + 2).equals("13")) protocol.setSumHeatUnit("100GJ");
        else protocol.setSumHeatUnit("kW*h");
        if (r == 0) return r;

        try {
            String powerS = DataArea.substring(32, 32 + 2) + DataArea.substring(30, 30 + 2) + DataArea.substring(28, 28 + 2) + DataArea.substring(26, 26 + 2);
            Integer.valueOf(powerS);
            protocol.setPower(Double.valueOf(powerS));
            r = 1;
        } catch (Exception e) {
            protocol.setPower(0);
            r = 0;
        }
        if (DataArea.substring(34, 34 + 2).equals("14")) protocol.setPowerUnit("W");
        else if (DataArea.substring(34, 34 + 2).equals("15")) protocol.setPowerUnit("10W");
        else if (DataArea.substring(34, 34 + 2).equals("16")) protocol.setPowerUnit("100W");
        else if (DataArea.substring(34, 34 + 2).equals("17")) protocol.setPowerUnit("kW");
        else protocol.setPowerUnit("kW");
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(42, 42 + 2) + DataArea.substring(40, 40 + 2) + DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2);
            Integer.valueOf(flowrateS);
            protocol.setFlowRate(Double.valueOf(flowrateS));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        if (DataArea.substring(44, 44 + 2).equals("32")) protocol.setFlowRateUnit("L/h");
        else if (DataArea.substring(44, 44 + 2).equals("33")) protocol.setFlowRateUnit("10L/h");
        else if (DataArea.substring(44, 44 + 2).equals("34")) protocol.setFlowRateUnit("100L/h");
        else if (DataArea.substring(44, 44 + 2).equals("35")) protocol.setFlowRateUnit("m³/h");
        else protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        try {
            String totalS = DataArea.substring(52, 52 + 2) + DataArea.substring(50, 50 + 2) + DataArea.substring(48, 48 + 2) + DataArea.substring(46, 46 + 2);
            Integer.valueOf(totalS);
            protocol.setTotal(Double.valueOf(totalS));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        if (DataArea.substring(54, 54 + 2).equals("26")) protocol.setTotalUnit("mL");
        else if (DataArea.substring(54, 54 + 2).equals("27")) protocol.setTotalUnit("10mL");
        else if (DataArea.substring(54, 54 + 2).equals("28")) protocol.setTotalUnit("100mL");
        else if (DataArea.substring(54, 54 + 2).equals("29")) protocol.setTotalUnit("L");
        else if (DataArea.substring(54, 54 + 2).equals("2A")) protocol.setTotalUnit("10L");
        else if (DataArea.substring(54, 54 + 2).equals("2B")) protocol.setTotalUnit("100L");
        else if (DataArea.substring(54, 54 + 2).equals("2C")) protocol.setTotalUnit("m³");
        else protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String t1S = DataArea.substring(60, 60 + 2) + DataArea.substring(58, 58 + 2) + "." + DataArea.substring(56, 56 + 2);
            Double.valueOf(t1S);
            protocol.setT1InP(Double.valueOf(t1S));
            r = 1;
        } catch (Exception e) {
            protocol.setT1InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t2S = DataArea.substring(66, 66 + 2) + DataArea.substring(64, 64 + 2) + "." + DataArea.substring(62, 62 + 2);
            Double.valueOf(t2S);
            protocol.setT2InP(Double.valueOf(t2S));
            r = 1;
        } catch (Exception e) {
            protocol.setT2InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String worktimeinpS = DataArea.substring(72, 72 + 2) + DataArea.substring(70, 70 + 2) + DataArea.substring(68, 68 + 2);
            Integer.valueOf(worktimeinpS);
            protocol.setWorkTimeInP(Integer.valueOf(worktimeinpS));
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        String timeinpS = DataArea.substring(86, 86 + 2) + DataArea.substring(84, 84 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(80, 80 + 2) + " " + DataArea.substring(78, 78 + 2) + ":" + DataArea.substring(76, 76 + 2) + ":" + DataArea.substring(74, 74 + 2);
        protocol.setTimeInP(timeinpS);
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(90, 90 + 2));
        String status = "";
        char b[] = statusb.toCharArray();
        if (b[2] == '1') status = status + "低电";
        if (b[3] == '1') status = status + "超量程";
        if (b[4] == '1') status = status + "PCB故障";
        if (b[5] == '1') status = status + "无水";
        if (b[6] == '1') status = status + "sener断路";
        if (b[7] == '1') status = status + "sener短路";
        if (b[0] == '0' & b[1] == '0' & b[2] == '0' & b[3] == '0' & b[4] == '0' & b[5] == '0' & b[6] == '0' & b[7] == '0')
            status = "正常";
        protocol.setStatus(status);
        return r;
    }

    private static int MVCAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 94);
        DecimalFormat df = new DecimalFormat(".##");
        try {
            double volF = Frequent.HexS2ToInt(DataArea.substring(4, 4 + 2)) * 2 / 100f;
            protocol.setVol(df.format(volF));
            r = 1;
        } catch (Exception e) {
            protocol.setVol("err 电压 " + DataArea.substring(4, 4 + 2));
            r = 0;
        }
        if (r == 0) return r;

        protocol.setCloseTime("20" + DataArea.substring(10, 10 + 2) + "-" + DataArea.substring(8, 8 + 2) + "-" + DataArea.substring(6, 6 + 2));

        try {
            String insideTS = DataArea.substring(14, 14 + 2) + DataArea.substring(12, 12 + 2);
            double insideT = Integer.valueOf(insideTS) / 10f;
            protocol.setInsideT(Double.valueOf(df.format(insideT)));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideT(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String heatS = DataArea.substring(22, 22 + 2) + DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(heatS);
            protocol.setSumHeat(Double.valueOf(heatS));
            r = 1;
        } catch (Exception e) {
            protocol.setSumHeat(0);
            r = 0;
        }
        if (DataArea.substring(24, 24 + 2).equals("02")) protocol.setSumHeatUnit("W*h");
        else if (DataArea.substring(24, 24 + 2).equals("03")) protocol.setSumHeatUnit("10W*h");
        else if (DataArea.substring(24, 24 + 2).equals("04")) protocol.setSumHeatUnit("100W*h");
        else if (DataArea.substring(24, 24 + 2).equals("05")) protocol.setSumHeatUnit("kW*h");
        else if (DataArea.substring(24, 24 + 2).equals("0B")) protocol.setSumHeatUnit("kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0C")) protocol.setSumHeatUnit("10kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0D")) protocol.setSumHeatUnit("100kJ");
        else if (DataArea.substring(24, 24 + 2).equals("0E")) protocol.setSumHeatUnit("MJ");
        else if (DataArea.substring(24, 24 + 2).equals("0F")) protocol.setSumHeatUnit("10MJ");
        else if (DataArea.substring(24, 24 + 2).equals("10")) protocol.setSumHeatUnit("100MJ");
        else if (DataArea.substring(24, 24 + 2).equals("11")) protocol.setSumHeatUnit("GJ");
        else if (DataArea.substring(24, 24 + 2).equals("12")) protocol.setSumHeatUnit("10GJ");
        else if (DataArea.substring(24, 24 + 2).equals("13")) protocol.setSumHeatUnit("100GJ");
        else protocol.setSumHeatUnit("kW*h");
        if (r == 0) return r;
        String permissionb = Frequent.HexS2ToBinS(DataArea.substring(52, 52 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(50, 50 + 2));
        String permission = "";
        char a[] = permissionb.toCharArray();
        if (a[1] == '1') permission = permission + "冷量使能+";
        if (a[2] == '1') permission = permission + "远程预付费使能+";
        if (a[3] == '1') permission = permission + "远程不供电使能+";
        if (a[4] == '1') permission = permission + "自检使能+";
        if (a[5] == '1') permission = permission + "无线不使能+";
        if (a[6] == '1') permission = permission + "远程设定温度使能+";
        if (a[7] == '0') permission = permission + "停止通讯-开阀+";
        if (a[7] == '1') permission = permission + "停止通讯-关阀+";
        if (a[8] == '1') permission = permission + "停止通讯使能+";
        if (a[9] == '0') permission = permission + "断电-开阀+";
        if (a[9] == '1') permission = permission + "断电-关阀+";
        if (a[10] == '1') permission = permission + "断电使能+";
        if (a[11] == '1') permission = permission + "室温测量使能+";
        if (a[12] == '1') permission = permission + "T2测量使能+";
        if (a[13] == '1') permission = permission + "T1测量使能+";
        if (a[14] == '1') permission = permission + "截止日期使能+";
        if (a[15] == '1') permission = permission + "远程锁闭使能+";
        //protocol.setPermission(DataArea.substring(52,52+2)+DataArea.substring(50,50+2)+"-"+permission);

        try {
            String insideTSetS = DataArea.substring(34, 34 + 2) + DataArea.substring(32, 32 + 2);
            double insideTSet = Integer.valueOf(insideTSetS) / 10f;
            protocol.setInsideT(Double.valueOf(df.format(insideTSet)));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideT(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(42, 42 + 2) + DataArea.substring(40, 40 + 2) + DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2);
            Integer.valueOf(flowrateS);
            protocol.setFlowRate(Double.valueOf(flowrateS));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        if (DataArea.substring(44, 44 + 2).equals("32")) protocol.setFlowRateUnit("L/h");
        else if (DataArea.substring(44, 44 + 2).equals("33")) protocol.setFlowRateUnit("10L/h");
        else if (DataArea.substring(44, 44 + 2).equals("34")) protocol.setFlowRateUnit("100L/h");
        else if (DataArea.substring(44, 44 + 2).equals("35")) protocol.setFlowRateUnit("m³/h");
        else protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        try {
            String totalS = DataArea.substring(52, 52 + 2) + DataArea.substring(50, 50 + 2) + DataArea.substring(48, 48 + 2) + DataArea.substring(46, 46 + 2);
            Integer.valueOf(totalS);
            protocol.setTotal(Double.valueOf(totalS));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        if (DataArea.substring(54, 54 + 2).equals("26")) protocol.setTotalUnit("mL");
        else if (DataArea.substring(54, 54 + 2).equals("27")) protocol.setTotalUnit("10mL");
        else if (DataArea.substring(54, 54 + 2).equals("28")) protocol.setTotalUnit("100mL");
        else if (DataArea.substring(54, 54 + 2).equals("29")) protocol.setTotalUnit("L");
        else if (DataArea.substring(54, 54 + 2).equals("2A")) protocol.setTotalUnit("10L");
        else if (DataArea.substring(54, 54 + 2).equals("2B")) protocol.setTotalUnit("100L");
        else if (DataArea.substring(54, 54 + 2).equals("2C")) protocol.setTotalUnit("m³");
        else protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String t1S = DataArea.substring(60, 60 + 2) + DataArea.substring(58, 58 + 2) + "." + DataArea.substring(56, 56 + 2);
            protocol.setT1InP(Double.valueOf(t1S));
            r = 1;
        } catch (Exception e) {
            protocol.setT1InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t2S = DataArea.substring(66, 66 + 2) + DataArea.substring(64, 64 + 2) + "." + DataArea.substring(62, 62 + 2);
            protocol.setT2InP(Double.valueOf(t2S));
            r = 1;
        } catch (Exception e) {
            protocol.setT2InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String worktimeinpS = DataArea.substring(72, 72 + 2) + DataArea.substring(70, 70 + 2) + DataArea.substring(68, 68 + 2);
            Integer.valueOf(worktimeinpS);
            protocol.setWorkTimeInP(Integer.valueOf(worktimeinpS));
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        String timeinpS = DataArea.substring(86, 86 + 2) + DataArea.substring(84, 84 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(80, 80 + 2) + " " + DataArea.substring(78, 78 + 2) + ":" + DataArea.substring(76, 76 + 2) + ":" + DataArea.substring(74, 74 + 2);
        protocol.setTimeInP(timeinpS);

        String valveStatusS = DataArea.substring(88, 88 + 2);
        if (valveStatusS.equals("55")) {
            protocol.setValveStatus("阀开");
            r = 1;
        } else if (valveStatusS.equals("99")) {
            protocol.setValveStatus("阀关");
            r = 1;
        } else if (valveStatusS.equals("59")) {
            protocol.setValveStatus("异常");
            r = 1;
        } else {
            protocol.setValveStatus("-");
            r = 0;
        }
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(90, 90 + 2));
        String status = "";
        char b[] = statusb.toCharArray();
        if (b[2] == '1') {
            status = status + "低电";
        } else if (b[3] == '1') {
            status = status + "流量故障";
        } else if (b[4] == '1') {
            status = status + "PCB故障";
        } else if (b[5] == '1') {
            status = status + "无水";
        } else if (b[6] == '1') {
            status = status + "铂电阻断路";
        } else if (b[7] == '1') {
            status = status + "铂电阻短路";
        }
        if (b[0] == '0' & b[1] == '0' & b[2] == '0' & b[3] == '0' & b[4] == '0' & b[5] == '0' & b[6] == '0' & b[7] == '0')
            status = "正常";
        protocol.setStatus(status);
        return r;
    }

    private static int VAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String valveStatusS = Data.substring(28, 28 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("?");
        r = 1;
        return r;
    }

    private static int PAnalysisJSMTRxStr(String Data, ParameterProtocol parameterprotocol) {
        int r = 0;
        parameterprotocol.setMeterid(Data.substring(4, 4 + 8));
        parameterprotocol.setQnx(Data.substring(32, 32 + 2) + Data.substring(30, 30 + 2) + Data.substring(28, 28 + 2));
        parameterprotocol.setQn2x(Data.substring(38, 38 + 2) + Data.substring(36, 36 + 2) + Data.substring(34, 34 + 2));
        parameterprotocol.setQn1x(Data.substring(44, 44 + 2) + Data.substring(42, 42 + 2) + Data.substring(40, 40 + 2));
        parameterprotocol.setQminx(Data.substring(50, 50 + 2) + Data.substring(48, 48 + 2) + Data.substring(46, 46 + 2));
        parameterprotocol.setT1x(Data.substring(56, 56 + 2) + Data.substring(54, 54 + 2) + Data.substring(52, 52 + 2));
        parameterprotocol.setT2x(Data.substring(62, 62 + 2) + Data.substring(60, 60 + 2) + Data.substring(58, 58 + 2));

        parameterprotocol.setT0_10(Data.substring(68, 68 + 2) + Data.substring(66, 66 + 2) + Data.substring(64, 64 + 2));
        parameterprotocol.setT10_30(Data.substring(74, 74 + 2) + Data.substring(72, 72 + 2) + Data.substring(70, 70 + 2));
        parameterprotocol.setT30_50(Data.substring(80, 80 + 2) + Data.substring(78, 78 + 2) + Data.substring(76, 76 + 2));
        parameterprotocol.setT50_70(Data.substring(86, 86 + 2) + Data.substring(84, 84 + 2) + Data.substring(82, 82 + 2));
        parameterprotocol.setT70_90(Data.substring(92, 92 + 2) + Data.substring(90, 90 + 2) + Data.substring(88, 88 + 2));
        parameterprotocol.setChecktime(Data.substring(98, 98 + 2) + Data.substring(96, 96 + 2) + Data.substring(94, 94 + 2));
        parameterprotocol.setAmendx(Data.substring(104, 104 + 2) + Data.substring(102, 102 + 2) + Data.substring(100, 100 + 2));
        parameterprotocol.setUnitStr(Data.substring(110, 110 + 2) + Data.substring(108, 108 + 2) + Data.substring(106, 106 + 2));
        parameterprotocol.setSlope(Data.substring(116, 116 + 2) + Data.substring(114, 114 + 2) + Data.substring(112, 112 + 2));
        parameterprotocol.setStartx(Data.substring(122, 122 + 2) + Data.substring(120, 120 + 2) + Data.substring(118, 118 + 2));
        parameterprotocol.setMetersize(Data.substring(126, 126 + 2) + Data.substring(124, 124 + 2));
        parameterprotocol.setVer(Data.substring(134, 134 + 2) + Data.substring(132, 132 + 2) + Data.substring(130, 130 + 2));
        parameterprotocol.setDivid1(Data.substring(140, 140 + 2) + Data.substring(138, 138 + 2) + Data.substring(136, 136 + 2));
        parameterprotocol.setDivid2(Data.substring(146, 146 + 2) + Data.substring(144, 144 + 2) + Data.substring(142, 142 + 2));
        parameterprotocol.setDivid3(Data.substring(152, 152 + 2) + Data.substring(150, 150 + 2) + Data.substring(148, 148 + 2));
        parameterprotocol.setSleeptime(Data.substring(162, 162 + 2) + Data.substring(160, 160 + 2));
        parameterprotocol.setPointwhere(Data.substring(158, 158 + 2) + Data.substring(156, 156 + 2) + Data.substring(154, 154 + 2));
        r = 1;
        return r;
    }

    private static int AAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 94);
        DecimalFormat df = new DecimalFormat(".##");
        try {
            double volF = Frequent.HexS2ToInt(DataArea.substring(4, 4 + 2)) * 2 / 100f;
            protocol.setVol(df.format(volF));
            r = 1;
        } catch (Exception e) {
            protocol.setVol("err 电压 " + DataArea.substring(4, 4 + 2));
            r = 0;
        }
        if (r == 0) return r;

        String closetime = "20" + DataArea.substring(10, 10 + 2) + "-" + DataArea.substring(8, 8 + 2) + "-" + DataArea.substring(6, 6 + 2);
        protocol.setCloseTime(closetime);
        String losePowerTime = DataArea.substring(14, 14 + 2) + DataArea.substring(12, 12 + 2);
        protocol.setLosePowerTime(losePowerTime);
        try {
            String sumOpenValveMS = DataArea.substring(20, 20 + 2) + DataArea.substring(18, 18 + 2) + DataArea.substring(16, 16 + 2);
            Integer.valueOf(sumOpenValveMS);
            protocol.setSumOpenValveM(Integer.valueOf(sumOpenValveMS));
            double sumopenvalveh = Integer.valueOf(sumOpenValveMS) / 60f;
            r = 1;
        } catch (Exception e) {
            protocol.setSumOpenValveM(0);
            r = 0;
        }
        if (r == 0) return r;
        String loseConTime = DataArea.substring(24, 24 + 2) + DataArea.substring(22, 22 + 2);
        protocol.setLoseConTime(loseConTime);
        try {
            String insideTS = DataArea.substring(28, 28 + 2) + DataArea.substring(26, 26 + 2);
            double insidet = Integer.valueOf(insideTS) / 10f;
            protocol.setInsideT(Double.valueOf(df.format(insidet)));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideT(0);
            r = 0;
        }
        if (r == 0) return r;
        try {
            String insideTSetS = DataArea.substring(32, 32 + 2) + DataArea.substring(30, 30 + 2);
            double insideTSet = Integer.valueOf(insideTSetS) / 10f;
            protocol.setInsideTSet(df.format(insideTSet));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideTSet("0");
            r = 0;
        }
        if (r == 0) return r;
        String loseConTimeSet = DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2);
        //protocol.setLoseConTimeSet(loseConTimeSet);
        String losePowerTimeSet = DataArea.substring(42, 42 + 2) + DataArea.substring(40, 40 + 2);
        //protocol.setLosePowerTimeSet(losePowerTimeSet);
        String sumOpenValveSS = DataArea.substring(48, 48 + 2) + DataArea.substring(46, 46 + 2);
        String permissionb = Frequent.HexS2ToBinS(DataArea.substring(52, 52 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(50, 50 + 2));
        String permission = "";
        char a[] = permissionb.toCharArray();
        if (a[3] == '1') permission = permission + "远程不供电使能+";
        if (a[4] == '1') permission = permission + "自检使能+";
        if (a[5] == '1') permission = permission + "无线不使能+";
        if (a[6] == '1') permission = permission + "远程设定温度使能+";
        if (a[7] == '0') permission = permission + "停止通讯-开阀+";
        if (a[7] == '1') permission = permission + "停止通讯-关阀+";
        if (a[8] == '1') permission = permission + "停止通讯使能+";
        if (a[9] == '0') permission = permission + "断电-开阀+";
        if (a[9] == '1') permission = permission + "断电-关阀+";
        if (a[10] == '1') permission = permission + "断电使能+";
        if (a[11] == '1') permission = permission + "室温测量使能+";
        if (a[12] == '1') permission = permission + "T2测量使能+";
        if (a[13] == '1') permission = permission + "T1测量使能+";
        if (a[14] == '1') permission = permission + "截止日期使能+";
        if (a[15] == '1') permission = permission + "远程锁闭使能+";
        //protocol.setPermission(DataArea.substring(52,52+2)+DataArea.substring(50,50+2)+"-"+permission);
        try {
            String t2S = DataArea.substring(66, 66 + 2) + DataArea.substring(64, 64 + 2) + "." + DataArea.substring(62, 62 + 2);
            Double.valueOf(t2S);
            protocol.setT2InP(Double.valueOf(t2S));
            r = 1;
        } catch (Exception e) {
            protocol.setT2InP(0);
            r = 0;
        }
        if (r == 0) return r;
        try {
            String worktimeinpS = DataArea.substring(72, 72 + 2) + DataArea.substring(70, 70 + 2) + DataArea.substring(68, 68 + 2);
            Integer.valueOf(worktimeinpS);
            protocol.setWorkTimeInP(Integer.valueOf(worktimeinpS));
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        String timeinpS = DataArea.substring(86, 86 + 2) + DataArea.substring(84, 84 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(80, 80 + 2) + " " + DataArea.substring(78, 78 + 2) + ":" + DataArea.substring(76, 76 + 2) + ":" + DataArea.substring(74, 74 + 2);
        protocol.setTimeInP(timeinpS);

        String valveStatusS = DataArea.substring(88, 88 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("-");
        return r;
    }

    private static int VSmartAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        String DataArea = Data.substring(22, 22 + 94);
        DecimalFormat df = new DecimalFormat(".##");
        try {
            double volF = Frequent.HexS2ToInt(DataArea.substring(4, 4 + 2)) * 2 / 100f;
            protocol.setVol(df.format(volF));
            r = 1;
        } catch (Exception e) {
            protocol.setVol("err 电压 " + DataArea.substring(4, 4 + 2));
            r = 0;
        }
        if (r == 0) return r;

        String closetime = "20" + DataArea.substring(10, 10 + 2) + "-" + DataArea.substring(8, 8 + 2) + "-" + DataArea.substring(6, 6 + 2);
        protocol.setCloseTime(closetime);


        try {
            String sumHeatS = DataArea.substring(40, 40 + 2) + DataArea.substring(38, 38 + 2) + DataArea.substring(36, 36 + 2) + DataArea.substring(34, 34 + 2);
            Integer.valueOf(sumHeatS);
            protocol.setSumHeat(Integer.valueOf(sumHeatS));
            protocol.setSumHeatUnit("kW*h");
            r = 1;
        } catch (Exception e) {
            protocol.setSumHeat(0);
            protocol.setSumHeatUnit("kW*h");
            r = 0;
        }
        if (r == 0) return r;
        String permissionb = Frequent.HexS2ToBinS(DataArea.substring(52, 52 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(50, 50 + 2));
        String permission = "";
        char a[] = permissionb.toCharArray();
        if (a[4] == '1') permission = permission + "自检使能+";
        if (a[14] == '1') permission = permission + "截止日期使能+";
        if (a[15] == '1') permission = permission + "远程锁闭使能+";
        //protocol.setPermission(DataArea.substring(52,52+2)+DataArea.substring(50,50+2)+"-"+permission);

        try {
            int worktimeinp = Frequent.HexS4ToInt(DataArea.substring(72, 72 + 2) + DataArea.substring(70, 70 + 2));
            protocol.setWorkTimeInP(worktimeinp);
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        String timeinpS = DataArea.substring(86, 86 + 2) + DataArea.substring(84, 84 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(80, 80 + 2) + " " + DataArea.substring(78, 78 + 2) + ":" + DataArea.substring(76, 76 + 2) + ":" + DataArea.substring(74, 74 + 2);
        protocol.setTimeInP(timeinpS);

        String valveStatusS = DataArea.substring(88, 88 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("-");
        r = 1;
        return r;
    }

    private static int MEAnalysisRxStr(String Data, Protocol protocol) {
        int r = 1;
        initProtocol(protocol);
        protocol.setProductTypeTX("20");
        protocol.setMBUSAddress(Data.substring(10, 10 + 2));
        protocol.setMeterID(Data.substring(20, 20 + 2) + Data.substring(18, 18 + 2) + Data.substring(16, 16 + 2) + Data.substring(14, 14 + 2));
        String LenS = Data.substring(2, 2 + 2);
        String DataArea = Data.substring(8, 8 + Frequent.HexS2ToInt(LenS) * 2);
        String statusb = Frequent.HexS2ToBinS(DataArea.substring(24, 24 + 2));
        String status = "";
        char b[] = statusb.toCharArray();
        if (b[2] == '1') status = status + "低电";
        if (b[3] == '1') status = status + "超量程";
        if (b[4] == '1') status = status + "PCB故障";
        if (b[5] == '1') status = status + "无水";
        if (b[6] == '1') status = status + "sener断路";
        if (b[7] == '1') status = status + "sener短路";
        if (b[0] == '0' & b[1] == '0' & b[2] == '0' & b[3] == '0' & b[4] == '0' & b[5] == '0' & b[6] == '0' & b[7] == '0')
            status = "正常";
        protocol.setStatus(status);

        String heat = "0";
        String otherenergy = "";
        protocol.setSumHeatUnit("kWh");
        int SumHeat1 = DataArea.indexOf("0C03");
        int SumHeat2 = DataArea.indexOf("0C04");
        int SumHeat3 = DataArea.indexOf("0C05");
        int SumHeat4 = DataArea.indexOf("0C06");
        int SumHeat5 = DataArea.indexOf("0C07");
        int SumHeat6 = DataArea.indexOf("0CFB00");
        int SumHeat7 = DataArea.indexOf("0CFB01");
        int SumHeat8 = DataArea.indexOf("0C0E");
        int SumHeat9 = DataArea.indexOf("0C0F");
        int SumHeat10 = DataArea.indexOf("0CFB08");
        int SumHeat11 = DataArea.indexOf("0CFB09");
        if (SumHeat1 >= 0) {
            heat = DataArea.substring(SumHeat1 + 10, SumHeat1 + 10 + 2) + DataArea.substring(SumHeat1 + 8, SumHeat1 + 8 + 2) + DataArea.substring(SumHeat1 + 6, SumHeat1 + 6 + 2) + DataArea.substring(SumHeat1 + 4, SumHeat1 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat1 + 4, DataArea.length() - SumHeat1 - 4);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
            protocol.setSumHeatUnit("W*h");
        } else if (SumHeat2 >= 0) {
            heat = DataArea.substring(SumHeat2 + 10, SumHeat2 + 10 + 2) + DataArea.substring(SumHeat2 + 8, SumHeat2 + 8 + 2) + DataArea.substring(SumHeat2 + 6, SumHeat2 + 6 + 2) + DataArea.substring(SumHeat2 + 4, SumHeat2 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat2 + 4, DataArea.length() - SumHeat2 - 4);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
            protocol.setSumHeatUnit("10W*h");
        } else if (SumHeat3 >= 0) {
            heat = DataArea.substring(SumHeat3 + 10, SumHeat3 + 10 + 2) + DataArea.substring(SumHeat3 + 8, SumHeat3 + 8 + 2) + DataArea.substring(SumHeat3 + 6, SumHeat3 + 6 + 2) + DataArea.substring(SumHeat3 + 4, SumHeat3 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat3 + 4, DataArea.length() - SumHeat3 - 4);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
            protocol.setSumHeatUnit("100W*h");
        } else if (SumHeat4 >= 0) {
            heat = DataArea.substring(SumHeat4 + 10, SumHeat4 + 10 + 2) + DataArea.substring(SumHeat4 + 8, SumHeat4 + 8 + 2) + DataArea.substring(SumHeat4 + 6, SumHeat4 + 6 + 2) + DataArea.substring(SumHeat4 + 4, SumHeat4 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat4 + 4, DataArea.length() - SumHeat4 - 4);
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
            protocol.setSumHeatUnit("kW*h");
        } else if (SumHeat5 >= 0) {
            heat = DataArea.substring(SumHeat5 + 10, SumHeat5 + 10 + 2) + DataArea.substring(SumHeat5 + 8, SumHeat5 + 8 + 2) + DataArea.substring(SumHeat5 + 6, SumHeat5 + 6 + 2) + DataArea.substring(SumHeat5 + 4, SumHeat5 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat5 + 4, DataArea.length() - SumHeat5 - 4);
                r = 1;
            } catch (Exception e) {
                r = -5;
            }
            protocol.setSumHeatUnit("10kW*h");
        } else if (SumHeat6 >= 0) {
            heat = DataArea.substring(SumHeat6 + 10, SumHeat6 + 10 + 2) + DataArea.substring(SumHeat6 + 8, SumHeat6 + 8 + 2) + DataArea.substring(SumHeat6 + 6, SumHeat6 + 6 + 2) + DataArea.substring(SumHeat6 + 4, SumHeat6 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat6 + 4, DataArea.length() - SumHeat6 - 4);
                r = 1;
            } catch (Exception e) {
                r = -6;
            }
            protocol.setSumHeatUnit("100kW*h");
        } else if (SumHeat7 >= 0) {
            heat = DataArea.substring(SumHeat7 + 10, SumHeat7 + 10 + 2) + DataArea.substring(SumHeat7 + 8, SumHeat7 + 8 + 2) + DataArea.substring(SumHeat7 + 6, SumHeat7 + 6 + 2) + DataArea.substring(SumHeat7 + 4, SumHeat7 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat7 + 4, DataArea.length() - SumHeat7 - 4);
                r = 1;
            } catch (Exception e) {
                r = -7;
            }
            protocol.setSumHeatUnit("MW*h");
        } else if (SumHeat8 >= 0) {
            heat = DataArea.substring(SumHeat8 + 10, SumHeat8 + 10 + 2) + DataArea.substring(SumHeat8 + 8, SumHeat8 + 8 + 2) + DataArea.substring(SumHeat8 + 6, SumHeat8 + 6 + 2) + DataArea.substring(SumHeat8 + 4, SumHeat8 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat8 + 4, DataArea.length() - SumHeat8 - 4);
                r = 1;
            } catch (Exception e) {
                r = -8;
            }
            protocol.setSumHeatUnit("MJ");
        } else if (SumHeat9 >= 0) {
            heat = DataArea.substring(SumHeat9 + 10, SumHeat9 + 10 + 2) + DataArea.substring(SumHeat9 + 8, SumHeat9 + 8 + 2) + DataArea.substring(SumHeat9 + 6, SumHeat9 + 6 + 2) + DataArea.substring(SumHeat9 + 4, SumHeat9 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat9 + 4, DataArea.length() - SumHeat9 - 4);
                r = 1;
            } catch (Exception e) {
                r = -9;
            }
            protocol.setSumHeatUnit("10MJ");
        } else if (SumHeat10 >= 0) {
            heat = DataArea.substring(SumHeat10 + 10, SumHeat10 + 10 + 2) + DataArea.substring(SumHeat10 + 8, SumHeat10 + 8 + 2) + DataArea.substring(SumHeat10 + 6, SumHeat10 + 6 + 2) + DataArea.substring(SumHeat10 + 4, SumHeat10 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat10 + 4, DataArea.length() - SumHeat10 - 4);
                r = 1;
            } catch (Exception e) {
                r = -10;
            }
            protocol.setSumHeatUnit("100MJ");
        } else if (SumHeat11 >= 0) {
            heat = DataArea.substring(SumHeat11 + 10, SumHeat11 + 10 + 2) + DataArea.substring(SumHeat11 + 8, SumHeat11 + 8 + 2) + DataArea.substring(SumHeat11 + 6, SumHeat11 + 6 + 2) + DataArea.substring(SumHeat11 + 4, SumHeat11 + 4 + 2);
            try {
                Integer.valueOf(heat);
                otherenergy = DataArea.substring(SumHeat11 + 4, DataArea.length() - SumHeat11 - 4);
                r = 1;
            } catch (Exception e) {
                r = -11;
            }
            protocol.setSumHeatUnit("GJ");
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -1) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -2) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -3) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -4) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -5) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -6) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -7) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -8) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -9) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -10) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -11) {
                protocol.setSumHeat(0);
                return r;
            }
        } else protocol.setSumHeat(Double.valueOf(heat));


        //双热单热处理部分
        int Heat1 = otherenergy.indexOf("0C03");
        int Heat2 = otherenergy.indexOf("0C04");
        int Heat3 = otherenergy.indexOf("0C05");
        int Heat4 = otherenergy.indexOf("0C06");
        int Heat5 = otherenergy.indexOf("0C07");
        int Heat6 = otherenergy.indexOf("0CFB00");
        int Heat7 = otherenergy.indexOf("0CFB01");
        int Heat8 = otherenergy.indexOf("0C0E");
        int Heat9 = otherenergy.indexOf("0C0F");
        int Heat10 = otherenergy.indexOf("0CFB08");
        int Heat11 = otherenergy.indexOf("0CFB09");
        if (Heat1 >= 0) {
            heat = otherenergy.substring(Heat1 + 10, Heat1 + 10 + 2) + otherenergy.substring(Heat1 + 8, Heat1 + 8 + 2) + otherenergy.substring(Heat1 + 6, Heat1 + 6 + 2) + otherenergy.substring(Heat1 + 4, Heat1 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
            protocol.setSumHeatUnit("W*h");
        } else if (Heat2 >= 0) {
            heat = otherenergy.substring(Heat2 + 10, Heat2 + 10 + 2) + otherenergy.substring(Heat2 + 8, Heat2 + 8 + 2) + otherenergy.substring(Heat2 + 6, Heat2 + 6 + 2) + otherenergy.substring(Heat2 + 4, Heat2 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
            protocol.setSumHeatUnit("10W*h");
        } else if (Heat3 >= 0) {
            heat = otherenergy.substring(Heat3 + 10, Heat3 + 10 + 2) + otherenergy.substring(Heat3 + 8, Heat3 + 8 + 2) + otherenergy.substring(Heat3 + 6, Heat3 + 6 + 2) + otherenergy.substring(Heat3 + 4, Heat3 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
            protocol.setSumHeatUnit("100W*h");
        } else if (Heat4 >= 0) {
            heat = otherenergy.substring(Heat4 + 10, Heat4 + 10 + 2) + otherenergy.substring(Heat4 + 8, Heat4 + 8 + 2) + otherenergy.substring(Heat4 + 6, Heat4 + 6 + 2) + otherenergy.substring(Heat4 + 4, Heat4 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
            protocol.setSumHeatUnit("kW*h");
        } else if (Heat5 >= 0) {
            heat = otherenergy.substring(Heat5 + 10, Heat5 + 10 + 2) + otherenergy.substring(Heat5 + 8, Heat5 + 8 + 2) + otherenergy.substring(Heat5 + 6, Heat5 + 6 + 2) + otherenergy.substring(Heat5 + 4, Heat5 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -5;
            }
            protocol.setSumHeatUnit("10kW*h");
        } else if (Heat6 >= 0) {
            heat = otherenergy.substring(Heat6 + 10, Heat6 + 10 + 2) + otherenergy.substring(Heat6 + 8, Heat6 + 8 + 2) + otherenergy.substring(Heat6 + 6, Heat6 + 6 + 2) + otherenergy.substring(Heat6 + 4, Heat6 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -6;
            }
            protocol.setSumHeatUnit("100kW*h");
        } else if (Heat7 >= 0) {
            heat = otherenergy.substring(Heat7 + 10, Heat7 + 10 + 2) + otherenergy.substring(Heat7 + 8, Heat7 + 8 + 2) + otherenergy.substring(Heat7 + 6, Heat7 + 6 + 2) + otherenergy.substring(Heat7 + 4, Heat7 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -7;
            }
            protocol.setSumHeatUnit("MW*h");
        } else if (Heat8 >= 0) {
            heat = otherenergy.substring(Heat8 + 10, Heat8 + 10 + 2) + otherenergy.substring(Heat8 + 8, Heat8 + 8 + 2) + otherenergy.substring(Heat8 + 6, Heat8 + 6 + 2) + otherenergy.substring(Heat8 + 4, Heat8 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -8;
            }
            protocol.setSumHeatUnit("MJ");
        } else if (Heat9 >= 0) {
            heat = otherenergy.substring(Heat9 + 10, Heat9 + 10 + 2) + otherenergy.substring(Heat9 + 8, Heat9 + 8 + 2) + otherenergy.substring(Heat9 + 6, Heat9 + 6 + 2) + otherenergy.substring(Heat9 + 4, Heat9 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -9;
            }
            protocol.setSumHeatUnit("10MJ");
        } else if (Heat10 >= 0) {
            heat = otherenergy.substring(Heat10 + 10, Heat10 + 10 + 2) + otherenergy.substring(Heat10 + 8, Heat10 + 8 + 2) + otherenergy.substring(Heat10 + 6, Heat10 + 6 + 2) + otherenergy.substring(Heat10 + 4, Heat10 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -10;
            }
            protocol.setSumHeatUnit("100MJ");
        } else if (Heat11 >= 0) {
            heat = otherenergy.substring(Heat11 + 10, Heat11 + 10 + 2) + otherenergy.substring(Heat11 + 8, Heat11 + 8 + 2) + otherenergy.substring(Heat11 + 6, Heat11 + 6 + 2) + otherenergy.substring(Heat11 + 4, Heat11 + 4 + 2);
            try {
                Integer.valueOf(heat);
                protocol.setSumCool(protocol.getSumHeat());
                protocol.setSumCoolUnit(protocol.getSumHeatUnit());
                r = 1;
            } catch (Exception e) {
                r = -11;
            }
            protocol.setSumHeatUnit("GJ");
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -1) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -2) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -3) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -4) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -5) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -6) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -7) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -8) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -9) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -10) {
                protocol.setSumHeat(0);
                return r;
            } else if (r == -11) {
                protocol.setSumHeat(0);
                return r;
            }
        } else protocol.setSumHeat(Double.valueOf(heat));


        String total = "0";
        protocol.setTotalUnit("m³");
        int Total1 = DataArea.indexOf("0C10");
        int Total2 = DataArea.indexOf("0C11");
        int Total3 = DataArea.indexOf("0C12");
        int Total4 = DataArea.indexOf("0C13");
        int Total5 = DataArea.indexOf("0C14");
        int Total6 = DataArea.indexOf("0C15");
        int Total7 = DataArea.indexOf("0C16");
        if (Total1 >= 0) {
            total = DataArea.substring(Total1 + 10, Total1 + 10 + 2) + DataArea.substring(Total1 + 8, Total1 + 8 + 2) + DataArea.substring(Total1 + 6, Total1 + 6 + 2) + DataArea.substring(Total1 + 4, Total1 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
            protocol.setTotalUnit("mL");
        } else if (Total2 >= 0) {
            total = DataArea.substring(Total2 + 10, Total2 + 10 + 2) + DataArea.substring(Total2 + 8, Total2 + 8 + 2) + DataArea.substring(Total2 + 6, Total2 + 6 + 2) + DataArea.substring(Total2 + 4, Total2 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
            protocol.setTotalUnit("10mL");
        } else if (Total3 >= 0) {
            total = DataArea.substring(Total3 + 10, Total3 + 10 + 2) + DataArea.substring(Total3 + 8, Total3 + 8 + 2) + DataArea.substring(Total3 + 6, Total3 + 6 + 2) + DataArea.substring(Total3 + 4, Total3 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
            protocol.setTotalUnit("100mL");
        } else if (Total4 >= 0) {
            total = DataArea.substring(Total4 + 10, Total4 + 10 + 2) + DataArea.substring(Total4 + 8, Total4 + 8 + 2) + DataArea.substring(Total4 + 6, Total4 + 6 + 2) + DataArea.substring(Total4 + 4, Total4 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
            protocol.setTotalUnit("L");
        } else if (Total5 >= 0) {
            total = DataArea.substring(Total5 + 10, Total5 + 10 + 2) + DataArea.substring(Total5 + 8, Total5 + 8 + 2) + DataArea.substring(Total5 + 6, Total5 + 6 + 2) + DataArea.substring(Total5 + 4, Total5 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -5;
            }
            protocol.setTotalUnit("10L");
        } else if (Total6 >= 0) {
            total = DataArea.substring(Total6 + 10, Total6 + 10 + 2) + DataArea.substring(Total6 + 8, Total6 + 8 + 2) + DataArea.substring(Total6 + 6, Total6 + 6 + 2) + DataArea.substring(Total6 + 4, Total6 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -6;
            }
            protocol.setTotalUnit("100L");
        } else if (Total7 >= 0) {
            total = DataArea.substring(Total7 + 10, Total7 + 10 + 2) + DataArea.substring(Total7 + 8, Total7 + 8 + 2) + DataArea.substring(Total7 + 6, Total7 + 6 + 2) + DataArea.substring(Total7 + 4, Total7 + 4 + 2);
            try {
                Integer.valueOf(total);
                r = 1;
            } catch (Exception e) {
                r = -7;
            }
            protocol.setTotalUnit("m³");
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setTotal(0);
                return r;
            } else if (r == -1) {
                protocol.setTotal(0);
                return r;
            } else if (r == -2) {
                protocol.setTotal(0);
                return r;
            } else if (r == -3) {
                protocol.setTotal(0);
                return r;
            } else if (r == -4) {
                protocol.setTotal(0);
                return r;
            } else if (r == -5) {
                protocol.setTotal(0);
                return r;
            } else if (r == -6) {
                protocol.setTotal(0);
                return r;
            } else if (r == -7) {
                protocol.setTotal(0);
                return r;
            } else if (r == -8) {
                protocol.setTotal(0);
                return r;
            }
        } else protocol.setTotal(Double.valueOf(total));

        String power = "0";
        protocol.setPowerUnit("kW");
        int Power1 = DataArea.indexOf("0B28");
        int Power2 = DataArea.indexOf("0B29");
        int Power3 = DataArea.indexOf("0B2A");
        int Power4 = DataArea.indexOf("0B2B");
        int Power5 = DataArea.indexOf("0B2C");
        int Power6 = DataArea.indexOf("0B2D");
        int Power7 = DataArea.indexOf("0B2E");
        int Power8 = DataArea.indexOf("0B2F");
        if (Power1 >= 0) {
            power = DataArea.substring(Power1 + 8, Power1 + 8 + 2) + DataArea.substring(Power1 + 6, Power1 + 6 + 2) + DataArea.substring(Power1 + 4, Power1 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
            protocol.setPowerUnit("0.001W");
        } else if (Power2 >= 0) {
            power = DataArea.substring(Power2 + 8, Power2 + 8 + 2) + DataArea.substring(Power2 + 6, Power2 + 6 + 2) + DataArea.substring(Power2 + 4, Power2 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
            protocol.setPowerUnit("0.01W");
        } else if (Power3 >= 0) {
            power = DataArea.substring(Power3 + 8, Power3 + 8 + 2) + DataArea.substring(Power3 + 6, Power3 + 6 + 2) + DataArea.substring(Power3 + 4, Power3 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
            protocol.setPowerUnit("0.1W");
        } else if (Power4 >= 0) {
            power = DataArea.substring(Power4 + 8, Power4 + 8 + 2) + DataArea.substring(Power4 + 6, Power4 + 6 + 2) + DataArea.substring(Power4 + 4, Power4 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
            protocol.setPowerUnit("W");
        } else if (Power5 >= 0) {
            power = DataArea.substring(Power5 + 8, Power5 + 8 + 2) + DataArea.substring(Power5 + 6, Power5 + 6 + 2) + DataArea.substring(Power5 + 4, Power5 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -5;
            }
            protocol.setPowerUnit("10W");
        } else if (Power6 >= 0) {
            power = DataArea.substring(Power6 + 8, Power6 + 8 + 2) + DataArea.substring(Power6 + 6, Power6 + 6 + 2) + DataArea.substring(Power6 + 4, Power6 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -6;
            }
            protocol.setPowerUnit("100W");
        } else if (Power7 >= 0) {
            power = DataArea.substring(Power7 + 8, Power7 + 8 + 2) + DataArea.substring(Power7 + 6, Power7 + 6 + 2) + DataArea.substring(Power7 + 4, Power7 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -7;
            }
            protocol.setPowerUnit("kW");
        } else if (Power8 >= 0) {
            power = DataArea.substring(Power8 + 8, Power8 + 8 + 2) + DataArea.substring(Power8 + 6, Power8 + 6 + 2) + DataArea.substring(Power8 + 4, Power8 + 4 + 2);
            try {
                Integer.valueOf(power);
                r = 1;
            } catch (Exception e) {
                r = -8;
            }
            protocol.setPowerUnit("10kW");
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setPower(0);
                return r;
            } else if (r == -1) {
                protocol.setPower(0);
                return r;
            } else if (r == -2) {
                protocol.setPower(0);
                return r;
            } else if (r == -3) {
                protocol.setPower(0);
                return r;
            } else if (r == -4) {
                protocol.setPower(0);
                return r;
            } else if (r == -5) {
                protocol.setPower(0);
                return r;
            } else if (r == -6) {
                protocol.setPower(0);
                return r;
            } else if (r == -7) {
                protocol.setPower(0);
                return r;
            } else if (r == -8) {
                protocol.setPower(0);
                return r;
            }
        } else protocol.setPower(Double.valueOf(power));

        String flowrate = "0";
        protocol.setFlowRateUnit("m³/h");
        int FlowRate1 = DataArea.indexOf("0B38");
        int FlowRate2 = DataArea.indexOf("0B39");
        int FlowRate3 = DataArea.indexOf("0B3A");
        int FlowRate4 = DataArea.indexOf("0B3B");
        int FlowRate5 = DataArea.indexOf("0B3C");
        int FlowRate6 = DataArea.indexOf("0B3D");
        int FlowRate7 = DataArea.indexOf("0B3E");
        if (FlowRate1 >= 0) {
            flowrate = DataArea.substring(FlowRate1 + 8, FlowRate1 + 8 + 2) + DataArea.substring(FlowRate1 + 6, FlowRate1 + 6 + 2) + DataArea.substring(FlowRate1 + 4, FlowRate1 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
            protocol.setFlowRateUnit("mL/h");
        } else if (FlowRate2 >= 0) {
            flowrate = DataArea.substring(FlowRate2 + 8, FlowRate2 + 8 + 2) + DataArea.substring(FlowRate2 + 6, FlowRate2 + 6 + 2) + DataArea.substring(FlowRate2 + 4, FlowRate2 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
            protocol.setFlowRateUnit("10mL/h");
        } else if (FlowRate3 >= 0) {
            flowrate = DataArea.substring(FlowRate3 + 8, FlowRate3 + 8 + 2) + DataArea.substring(FlowRate3 + 6, FlowRate3 + 6 + 2) + DataArea.substring(FlowRate3 + 4, FlowRate3 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
            protocol.setFlowRateUnit("100mL/h");
        } else if (FlowRate4 >= 0) {
            flowrate = DataArea.substring(FlowRate4 + 8, FlowRate4 + 8 + 2) + DataArea.substring(FlowRate4 + 6, FlowRate4 + 6 + 2) + DataArea.substring(FlowRate4 + 4, FlowRate4 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
            protocol.setFlowRateUnit("L/h");
        } else if (FlowRate5 >= 0) {
            flowrate = DataArea.substring(FlowRate5 + 8, FlowRate5 + 8 + 2) + DataArea.substring(FlowRate5 + 6, FlowRate5 + 6 + 2) + DataArea.substring(FlowRate5 + 4, FlowRate5 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -5;
            }
            protocol.setFlowRateUnit("10L/h");
        } else if (FlowRate6 >= 0) {
            flowrate = DataArea.substring(FlowRate6 + 8, FlowRate6 + 8 + 2) + DataArea.substring(FlowRate6 + 6, FlowRate6 + 6 + 2) + DataArea.substring(FlowRate6 + 4, FlowRate6 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -6;
            }
            protocol.setFlowRateUnit("100L/h");
        } else if (FlowRate7 >= 0) {
            flowrate = DataArea.substring(FlowRate7 + 8, FlowRate7 + 8 + 2) + DataArea.substring(FlowRate7 + 6, FlowRate7 + 6 + 2) + DataArea.substring(FlowRate7 + 4, FlowRate7 + 4 + 2);
            try {
                Integer.valueOf(flowrate);
                r = 1;
            } catch (Exception e) {
                r = -7;
            }
            protocol.setFlowRateUnit("m³/h");
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -1) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -2) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -3) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -4) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -5) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -6) {
                protocol.setFlowRate(0);
                return r;
            } else if (r == -7) {
                protocol.setFlowRate(0);
                return r;
            }
        } else protocol.setFlowRate(Double.valueOf(flowrate));

        String Temp1 = "0";
        int T11 = DataArea.indexOf("0A58");
        int T12 = DataArea.indexOf("0A59");
        int T13 = DataArea.indexOf("0A5A");
        int T14 = DataArea.indexOf("0A5B");
        if (T11 >= 0) {
            Temp1 = DataArea.substring(T11 + 6, T11 + 6 + 2) + DataArea.substring(T11 + 4, T11 + 4 + 2);
            try {
                Temp1 = String.valueOf(Double.valueOf(Temp1) / 1000);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
        } else if (T12 >= 0) {
            Temp1 = DataArea.substring(T12 + 6, T12 + 6 + 2) + DataArea.substring(T12 + 4, T12 + 4 + 2);
            try {
                Temp1 = String.valueOf(Double.valueOf(Temp1) / 100);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
        } else if (T13 >= 0) {
            Temp1 = DataArea.substring(T13 + 6, T13 + 6 + 2) + DataArea.substring(T13 + 4, T13 + 4 + 2);
            try {
                Temp1 = String.valueOf(Double.valueOf(Temp1) / 10);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
        } else if (T14 >= 0) {
            Temp1 = DataArea.substring(T14 + 6, T14 + 6 + 2) + DataArea.substring(T14 + 4, T14 + 4 + 2);
            try {
                Temp1 = String.valueOf(Double.valueOf(Temp1));
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setT1InP(0);
                return r;
            } else if (r == -1) {
                protocol.setT1InP(0);
                return r;
            } else if (r == -2) {
                protocol.setT1InP(0);
                return r;
            } else if (r == -3) {
                protocol.setT1InP(0);
                return r;
            } else if (r == -4) {
                protocol.setT1InP(0);
                return r;
            }
        } else protocol.setT1InP(Double.valueOf(Temp1));

        String Temp2 = "0";
        int T21 = DataArea.indexOf("0A5C");
        int T22 = DataArea.indexOf("0A5D");
        int T23 = DataArea.indexOf("0A5E");
        int T24 = DataArea.indexOf("0A5F");
        if (T21 >= 0) {
            Temp2 = DataArea.substring(T21 + 6, T21 + 6 + 2) + DataArea.substring(T21 + 4, T21 + 4 + 2);
            try {
                Temp2 = String.valueOf(Double.valueOf(Temp2) / 1000);
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
        } else if (T22 >= 0) {
            Temp2 = DataArea.substring(T22 + 6, T22 + 6 + 2) + DataArea.substring(T22 + 4, T22 + 4 + 2);
            try {
                Temp2 = String.valueOf(Double.valueOf(Temp2) / 100);
                r = 1;
            } catch (Exception e) {
                r = -2;
            }
        } else if (T23 >= 0) {
            Temp2 = DataArea.substring(T23 + 6, T23 + 6 + 2) + DataArea.substring(T23 + 4, T23 + 4 + 2);
            try {
                Temp2 = String.valueOf(Double.valueOf(Temp2) / 10);
                r = 1;
            } catch (Exception e) {
                r = -3;
            }
        } else if (T24 >= 0) {
            Temp2 = DataArea.substring(T24 + 6, T24 + 6 + 2) + DataArea.substring(T24 + 4, T24 + 4 + 2);
            try {
                Temp2 = String.valueOf(Double.valueOf(Temp2));
                r = 1;
            } catch (Exception e) {
                r = -4;
            }
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setT2InP(0);
                return r;
            } else if (r == -1) {
                protocol.setT2InP(0);
                return r;
            } else if (r == -2) {
                protocol.setT2InP(0);
                return r;
            } else if (r == -3) {
                protocol.setT2InP(0);
                return r;
            } else if (r == -4) {
                protocol.setT2InP(0);
                return r;
            }
        } else protocol.setT2InP(Double.valueOf(Temp2));


        String WorkTime = "0";
        int WorkTime1 = DataArea.indexOf("0B26");
        if (WorkTime1 >= 0) {
            WorkTime = DataArea.substring(WorkTime1 + 8, WorkTime1 + 8 + 2) + DataArea.substring(WorkTime1 + 6, WorkTime1 + 6 + 2) + DataArea.substring(WorkTime1 + 4, WorkTime1 + 4 + 2);
            try {
                WorkTime = String.valueOf(Integer.valueOf(WorkTime));
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setWorkTimeInP(0);
                return r;
            } else if (r == -1) {
                protocol.setWorkTimeInP(0);
                return r;
            }
        } else protocol.setWorkTimeInP(Integer.valueOf(WorkTime));

        String Time = "0";
        int Time1 = DataArea.indexOf("046D");
        if (Time1 >= 0) {
            Time = Frequent.HexS2ToBinS(DataArea.substring(Time1 + 10, Time1 + 10 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(Time1 + 8, Time1 + 8 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(Time1 + 6, Time1 + 6 + 2)) + Frequent.HexS2ToBinS(DataArea.substring(Time1 + 4, Time1 + 4 + 2));
            char timechar[] = Time.toCharArray();
            String year = "0" + timechar[0] + timechar[1] + timechar[2] + timechar[3] + timechar[8] + timechar[9] + timechar[10];
            String month = "" + timechar[4] + timechar[5] + timechar[6] + timechar[7];
            String day = "000" + timechar[11] + timechar[12] + timechar[13] + timechar[14] + timechar[15];
            String hundreadyear = "00" + timechar[17] + timechar[18];
            String hour = "000" + timechar[19] + timechar[20] + timechar[21] + timechar[22] + timechar[23];
            String min = "00" + timechar[26] + timechar[27] + timechar[28] + timechar[29] + timechar[30] + timechar[31];
            year = Frequent.BinS8ToHexS2(year);
            month = "0" + Frequent.BinS4ToHexS2(month);
            day = Frequent.BinS8ToHexS2(day);
            hundreadyear = "0" + Frequent.BinS4ToHexS2(hundreadyear);
            hour = Frequent.BinS8ToHexS2(hour);
            min = Frequent.BinS8ToHexS2(min);
            int yeari = 2000 + Frequent.HexS2ToInt(hundreadyear) * 100 + Frequent.HexS2ToInt(year);
            int monthi = Frequent.HexS2ToInt(month);
            int dayi = Frequent.HexS2ToInt(day);
            int houri = Frequent.HexS2ToInt(hour);
            int mini = Frequent.HexS2ToInt(min);
            year = String.valueOf(yeari);
            month = String.valueOf(monthi);
            day = String.valueOf(dayi);
            hour = String.valueOf(houri);
            min = String.valueOf(mini);
            switch (year.length()) {
                case 0:
                    year = "0000";
                    break;
                case 1:
                    year = "000" + year;
                    break;
                case 2:
                    year = "00" + year;
                    break;
                case 3:
                    year = "0" + year;
                    break;
                case 4:
                    break;
                default:
                    break;
            }
            switch (month.length()) {
                case 0:
                    month = "00";
                    break;
                case 1:
                    month = "0" + month;
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            switch (day.length()) {
                case 0:
                    day = "00";
                    break;
                case 1:
                    day = "0" + day;
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            switch (hour.length()) {
                case 0:
                    hour = "00";
                    break;
                case 1:
                    hour = "0" + hour;
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            switch (min.length()) {
                case 0:
                    min = "00";
                    break;
                case 1:
                    min = "0" + min;
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            try {
                Time = year + "-" + month + "-" + day + " " + hour + ":" + min + ":00";
                r = 1;
            } catch (Exception e) {
                r = -1;
            }
        }
        if (r != 1) {
            if (r == 0) {
                protocol.setTimeInP("err ");
                return r;
            } else if (r == -1) {
                protocol.setTimeInP("err " + DataArea.substring(Time1 + 10, Time1 + 10 + 2) + DataArea.substring(Time1 + 8, Time1 + 8 + 2) + DataArea.substring(Time1 + 6, Time1 + 6 + 2) + DataArea.substring(Time1 + 4, Time1 + 4 + 2));
                return r;
            }
        } else protocol.setTimeInP(Time);
        r = 1;
        return r;
    }

    private static int MAnalysisJSMTRxStr(String Data, MBUS mbus) {
        int r = 0;
        String valveStatusS = Data.substring(26, 26 + 2);
        if (valveStatusS.equals("99")) mbus.setStatus("已执行");
        else if (valveStatusS.equals("59")) mbus.setStatus("短路");
        else mbus.setStatus("?");
        r = 1;
        return r;
    }

    public static int ReportAnalysisJSMTRxStr(String Data, Protocol protocol) {
        int r = 0;
        initProtocol(protocol);
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setProductTypeTX(Data.substring(2, 2 + 2));
        protocol.setMeterID(Data.substring(4, 4 + 8));
        DecimalFormat df = new DecimalFormat(".##");
        String DataArea = "";
        try {
            DataArea = Data.substring(40, 40 + Data.length() - 40 - 4);
            r = 1;
        } catch (Exception e) {
            r = 0;
        }
        if (r == 0) return r;
        try {
            String heatS = DataArea.substring(0, 0 + 8);
            int heat = Frequent.HexS8ToInt(heatS);
            protocol.setSumHeat(Double.valueOf(df.format(heat)));
            r = 1;
        } catch (Exception e) {
            protocol.setSumHeat(0);
            r = 0;
        }
        protocol.setSumHeatUnit("kW*h");
        if (r == 0) return r;

        try {
            String totalS = DataArea.substring(8, 8 + 8);
            int total = Frequent.HexS8ToInt(totalS);
            protocol.setTotal(Double.valueOf(df.format(total)));
            r = 1;
        } catch (Exception e) {
            protocol.setTotal(0);
            r = 0;
        }
        protocol.setTotalUnit("m³");
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(16, 16 + 8);
            double flowrate = Integer.valueOf(flowrateS) / 100f;
            protocol.setFlowRate(Double.valueOf(df.format(flowrate)));
            r = 1;
        } catch (Exception e) {
            protocol.setFlowRate(0);
            r = 0;
        }
        protocol.setFlowRateUnit("m³/h");
        if (r == 0) return r;

        try {
            String t1S = DataArea.substring(24, 24 + 4) + "." + DataArea.substring(28, 28 + 2);
            double t1 = Double.valueOf(t1S);
            protocol.setT1InP(Double.valueOf(df.format(t1)));
            r = 1;
        } catch (Exception e) {
            protocol.setT1InP(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t2S = DataArea.substring(30, 30 + 4) + "." + DataArea.substring(34, 34 + 2);
            double t2 = Double.valueOf(t2S);
            protocol.setT2InP(Double.valueOf(df.format(t2)));
            r = 1;
        } catch (Exception e) {
            protocol.setT2InP(0);
            r = 0;
        }
        if (r == 0) return r;

        String Status = DataArea.substring(36, 36 + 2);
        if (Status.endsWith("00")) {
            protocol.setStatus("正常");
        } else if (Status.endsWith("01")) {
            protocol.setStatus("无水");
        } else if (Status.endsWith("02")) {
            protocol.setStatus("室温失联");
        } else if (Status.endsWith("03")) {
            protocol.setStatus("失联");
        } else if (Status.endsWith("04")) {
            protocol.setStatus("-");
        } else if (Status.endsWith("05")) {
            protocol.setStatus("其他");
        }

        try {
            String insideTSetS = DataArea.substring(38, 38 + 6);
            double insideTSet = Integer.valueOf(insideTSetS) / 100f;
            protocol.setInsideTSet(df.format(insideTSet));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideTSet("0");
            r = 0;
        }
        if (r == 0) return r;

        try {
            String insideTS = DataArea.substring(46, 46 + 6);
            double insidet = Integer.valueOf(insideTS) / 100f;
            if (DataArea.substring(44, 44 + 2).endsWith("FF")) {
                insidet = 0 - insidet;
            }
            protocol.setInsideT(Double.valueOf(df.format(insidet)));
            r = 1;
        } catch (Exception e) {
            protocol.setInsideT(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String sumOpenValveMS = DataArea.substring(52, 52 + 8);
            int sumOpenValveM = Frequent.HexS8ToInt(sumOpenValveMS);
            protocol.setSumOpenValveM(Double.valueOf(df.format(sumOpenValveM)));
            r = 1;
        } catch (Exception e) {
//				   protocol.setSumOpenValveS("0");
            r = 0;
        }
        if (r == 0) return r;

        try {
            String sumOpenValveHS = DataArea.substring(60, 60 + 8);
            int sumOpenValveH = Frequent.HexS8ToInt(sumOpenValveHS);
//		    	   protocol.setSumOpenValveH(df.format(sumOpenValveH));
            r = 1;
        } catch (Exception e) {
//				   protocol.setSumOpenValveS("0");
            r = 0;
        }
        if (r == 0) return r;

        String valveStatusS = DataArea.substring(68, 68 + 2);
        if (valveStatusS.equals("55")) protocol.setValveStatus("阀开");
        else if (valveStatusS.equals("99")) protocol.setValveStatus("阀关");
        else if (valveStatusS.equals("59")) protocol.setValveStatus("异常");
        else protocol.setValveStatus("-");

        try {
            String worktimeinpS = DataArea.substring(70, 70 + 8);
            int worktimeinp = Frequent.HexS8ToInt(worktimeinpS);
            protocol.setWorkTimeInP(worktimeinp);
            r = 1;
        } catch (Exception e) {
            protocol.setWorkTimeInP(0);
            r = 0;
        }
        if (r == 0) return r;

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String timeinpS = DataArea.substring(78, 78 + 2) + DataArea.substring(80, 80 + 2) + "-" + DataArea.substring(82, 82 + 2) + "-" + DataArea.substring(84, 84 + 2) + " " + DataArea.substring(86, 86 + 2) + ":" + DataArea.substring(88, 88 + 2) + ":" + DataArea.substring(90, 90 + 2);

            Date timeinpdate = (Date) formatDate.parse(timeinpS);
            protocol.setTimeInP(formatDate.format(timeinpdate));
        } catch (Exception e) {
            protocol.setTimeInP(formatDate.format(new Date(System.currentTimeMillis())));
        }


        return r;
    }

    private static void initSSumHeat(SSumHeat ssumheat) {
        ssumheat.setImei("");
        ssumheat.setMeterid("");
        ssumheat.setSumheat(0);
        ssumheat.setTotal(0);
        ssumheat.setFlowrate(0);
        ssumheat.setT1inp(0);
        ssumheat.setT2inp(0);
        ssumheat.setStatus("");
        ssumheat.setSumheat1(0);
        ssumheat.setSumheat2(0);
        ssumheat.setDsumheat(0);
        ssumheat.setInsidetset("");
        ssumheat.setInsidet(0);
        ssumheat.setEqualinsidet(0);
        ssumheat.setSumopenvalvem1(0);
        ssumheat.setSumopenvalvem2(0);
        ssumheat.setDsumopenvalveh(0);
        ssumheat.setValvestatus("");
        ssumheat.setDutyratio(0);
        ssumheat.setSsumheat(0);
        ssumheat.setTotalssumheat(0);
        ssumheat.setSysstatus("");
        ssumheat.setAvgtime(new Date(System.currentTimeMillis()));
    }

    private static int AvgAnalysisJSMTRxStr(String Data, SSumHeat ssumheat) {
        int r = 0;
        initSSumHeat(ssumheat);
        ssumheat.setImei(Data.substring(29, 29 + 11));
        ssumheat.setMeterid(Data.substring(4, 4 + 8));
        DecimalFormat df = new DecimalFormat(".##");
        String DataArea = "";
        try {
            DataArea = Data.substring(40, 40 + Data.length() - 40 - 4);
            r = 1;
        } catch (Exception e) {
            r = 0;
        }
        if (r == 0) return r;
        try {
            String heatS = DataArea.substring(0, 0 + 8);
            int heat = Frequent.HexS8ToInt(heatS);
            ssumheat.setSumheat(Double.valueOf(df.format(heat)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSumheat(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String totalS = DataArea.substring(8, 8 + 8);
            int total = Frequent.HexS8ToInt(totalS);
            ssumheat.setTotal(Double.valueOf(df.format(total)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setTotal(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String flowrateS = DataArea.substring(16, 16 + 8);
            double flowrate = Integer.valueOf(flowrateS) / 100f;
            ssumheat.setFlowrate(Double.valueOf(df.format(flowrate)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setFlowrate(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t1S = DataArea.substring(24, 24 + 4) + "." + DataArea.substring(28, 28 + 2);
            double t1 = Double.valueOf(t1S);
            ssumheat.setT1inp(Double.valueOf(df.format(t1)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setT1inp(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String t2S = DataArea.substring(30, 30 + 4) + "." + DataArea.substring(34, 34 + 2);
            double t2 = Double.valueOf(t2S);
            ssumheat.setT2inp(Double.valueOf(df.format(t2)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setT2inp(0);
            r = 0;
        }
        if (r == 0) return r;

        String Status = DataArea.substring(36, 36 + 2);
        if (Status.endsWith("00")) {
            ssumheat.setStatus("正常");
        } else if (Status.endsWith("01")) {
            ssumheat.setStatus("无水");
        } else if (Status.endsWith("02")) {
            ssumheat.setStatus("-");
        } else if (Status.endsWith("03")) {
            ssumheat.setStatus("失联");
        } else if (Status.endsWith("04")) {
            ssumheat.setStatus("其他");
        }

        try {
            String heat1S = DataArea.substring(38, 38 + 8);
            int heat1 = Frequent.HexS8ToInt(heat1S);
            ssumheat.setSumheat1(Double.valueOf(df.format(heat1)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSumheat1(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String heat2S = DataArea.substring(46, 46 + 8);
            int heat2 = Frequent.HexS8ToInt(heat2S);
            ssumheat.setSumheat2(Double.valueOf(df.format(heat2)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSumheat2(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String dheatS = DataArea.substring(54, 54 + 8);
            int dheat = Frequent.HexS8ToInt(dheatS);
            ssumheat.setDsumheat(Double.valueOf(df.format(dheat)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setDsumheat(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String insideTSetS = DataArea.substring(62, 62 + 6);
            double insideTSet = Integer.valueOf(insideTSetS) / 100f;
            ssumheat.setInsidetset(df.format(insideTSet));
            r = 1;
        } catch (Exception e) {
            ssumheat.setInsidetset("0");
            r = 0;
        }
        if (r == 0) return r;

        try {
            String insideTS = DataArea.substring(70, 70 + 6);
            double insidet = Integer.valueOf(insideTS) / 100f;
            if (DataArea.substring(68, 68 + 2).endsWith("FF")) {
                insidet = 0 - insidet;
            }
            ssumheat.setInsidet(Double.valueOf(df.format(insidet)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setInsidet(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String equalinsideTS = DataArea.substring(78, 78 + 6);
            double equalinsidet = Integer.valueOf(equalinsideTS) / 100f;
            if (DataArea.substring(76, 76 + 2).endsWith("FF")) {
                equalinsidet = 0 - equalinsidet;
            }
            ssumheat.setEqualinsidet(Double.valueOf(df.format(equalinsidet)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setEqualinsidet(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String sumOpenValveM1S = DataArea.substring(84, 84 + 8);
            int sumOpenValveM1 = Frequent.HexS8ToInt(sumOpenValveM1S);
            ssumheat.setSumopenvalvem1(Double.valueOf(df.format(sumOpenValveM1)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSumopenvalvem1(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String sumOpenValveM2S = DataArea.substring(92, 92 + 8);
            int sumOpenValveM2 = Frequent.HexS8ToInt(sumOpenValveM2S);
            ssumheat.setSumopenvalvem2(Double.valueOf(df.format(sumOpenValveM2)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSumopenvalvem2(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String dsumOpenValveHS = DataArea.substring(100, 100 + 8);
            int dsumOpenValveH = Frequent.HexS8ToInt(dsumOpenValveHS);
            ssumheat.setDsumopenvalveh(Double.valueOf(df.format(dsumOpenValveH)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setDsumopenvalveh(0);
            r = 0;
        }
        if (r == 0) return r;

        String ValveStatus = DataArea.substring(108, 108 + 2);
        if (ValveStatus.endsWith("55")) {
            ssumheat.setValvestatus("阀开");
        } else if (ValveStatus.endsWith("99")) {
            ssumheat.setValvestatus("阀关");
        } else if (ValveStatus.endsWith("59")) {
            ssumheat.setValvestatus("异常");
        } else if (ValveStatus.endsWith("00")) {
            ssumheat.setValvestatus("其他");
        }
        try {
            String dutyratioS = DataArea.substring(110, 110 + 6);
            double dutyratio = Integer.valueOf(dutyratioS) / 100f;
            ssumheat.setDutyratio(Double.valueOf(df.format(dutyratio)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setDutyratio(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String ssumheatS = DataArea.substring(116, 116 + 8);
            int ssumheatd = Frequent.HexS8ToInt(ssumheatS);
            ssumheat.setSsumheat(Double.valueOf(df.format(ssumheatd)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setSsumheat(0);
            r = 0;
        }
        if (r == 0) return r;

        try {
            String totalssumheatS = DataArea.substring(124, 124 + 8);
            int totalssumheat = Frequent.HexS8ToInt(totalssumheatS);
            ssumheat.setTotalssumheat(Double.valueOf(df.format(totalssumheat)));
            r = 1;
        } catch (Exception e) {
            ssumheat.setTotalssumheat(0);
            r = 0;
        }
        if (r == 0) return r;

        String SysStatus = DataArea.substring(132, 132 + 2);
        if (SysStatus.endsWith("00")) {
            ssumheat.setSysstatus("正常");
        } else if (SysStatus.endsWith("01")) {
            ssumheat.setSysstatus("室温失联");
        } else if (SysStatus.endsWith("02")) {
            ssumheat.setSysstatus("-");
        } else if (SysStatus.endsWith("03")) {
            ssumheat.setSysstatus("失联");
        } else if (SysStatus.endsWith("04")) {
            ssumheat.setSysstatus("其他");
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String avgtimeS = DataArea.substring(134, 134 + 2) + DataArea.substring(136, 136 + 2) + "-" + DataArea.substring(138, 138 + 2) + "-" + DataArea.substring(140, 140 + 2) + " " + DataArea.substring(142, 142 + 2) + ":" + DataArea.substring(144, 144 + 2) + ":" + DataArea.substring(146, 146 + 2);
            Date avgtime = (Date) formatDate.parse(avgtimeS);
            ssumheat.setAvgtime(avgtime);
        } catch (Exception e) {
            ssumheat.setAvgtime(new Date(System.currentTimeMillis()));
        }

        return r;
    }

    public static int analysiseData(String RX, Protocol protocol, SSumHeat ssumheat, MBUS mbus, ParameterProtocol parameterprotcol) {
        //	RX="68393968080072181111118836150D0D0000000974080970080C0F340000000C0F088521000C15723211000B2D8002010B3C7317000A5922730A5D2423D816";
        //热量表            	   String RX="682094000007001111812E902F000000000005000000000500000000140000000032000000002C2729009026005001003855092503142000007816";
        //阀控表                              String RX="682022540126001111812E901FE931129992010000000005000C0370020000000032000000002A0021009820009788005144112103142099077516";
        //英文                 	   String RX="682F2F680800720500253397362707850000000974080970080C10461900000B3B2600000A599032046D3A13D9130B260200004616";
        //阀门                  	   String RX="6820225401260011118404A01700991F16";
        //内部参数                         String RX="FEFE6820432008350011119148901F007437010137012936011836010000000000000210006029000549003768005887000000001200000036000030002002002000000735000010060030020036000010000016006716";
        //通断控制器                    String RX="684947268622001111812E901FB3311299000087270000009402000201FFFF1E0032340720002C000000000000882700550712191114205507C816";
        //水表                                   String RX="682F2F680800721211111188362707850000000974080970080C15829507000B3C3415000A598327046D2407D1150B264404000D16";
        //热量表                              String RX="68393968080072181111118836150D0D0000000974080970080C0F340000000C0F088521000C15723211000B2D8002010B3C7317000A5922730A5D2423D816";
        //时间面积分摊   报表  String RX="6851000000000001111111120011118438903F00000000000000000000000000000000000000030030000000324000056085000009000100000027201307281000300000006401017416";
        //时间面积分摊   分摊  String RX="6851111111120011118549903F00000000000001000000000000000000000000000000000000021530231001531258000000102700300000003240000032400005608500056085000000000000000000000000001491600220130726160031C316";
        int r = 0;
        try {
            int HeadPI = RX.indexOf("68");
            if (HeadPI >= 0) {
                String HeadS = RX.substring(HeadPI, HeadPI + 2);
                String ProductTypeS = RX.substring(HeadPI + 2, HeadPI + 2 + 2);
                String ProductTypeS1 = RX.substring(HeadPI + 2 + 2, HeadPI + 2 + 2 + 2);
                String HeadS1 = RX.substring(HeadPI + 2 + 2 + 2, HeadPI + 2 + 2 + 2 + 2);
                String LenS, Data, MasterCS, ViceCS1, ViceCS2;
                if (HeadS.equals("68") & ProductTypeS.equals("10")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("91") & ViceCS1.equals("90") & ViceCS2.equals("1F")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (PAnalysisJSMTRxStr(Data, parameterprotcol) == 1) {
                                    return r = 11;
                                }
                            }
                        }
                        if (MasterCS.equals("81") & ViceCS1.equals("90") & (ViceCS2.equals("1F") || ViceCS2.equals("2F"))) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (Data.substring(20, 20 + 2).equals("16")) {
                                    if (WCAnalysisJSMTRxStr(Data, protocol) == 1) {
                                        return r = 1;
                                    }
                                }
                                if (Data.substring(20, 20 + 2).equals("1B")) {
                                    if (WCAnalysisJSMTRxStr1(Data, protocol) == 1) {
                                        return r = 1;
                                    }
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("20")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("42") & ViceCS1.equals("90") & (ViceCS2.equals("1F") || ViceCS2.equals("2F"))) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {

                            }
                        }
                        if (MasterCS.equals("81") & ViceCS1.equals("90") & (ViceCS2.equals("1F") || ViceCS2.equals("2F"))) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (MVCAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                } else {
                                    if (MCAnalysisJSMTRxStr(Data, protocol) == 1) {
                                        return r = 1;
                                    }
                                }
                            }
                        }
                        if (MasterCS.equals("81") & ViceCS1.equals("1F") & ViceCS2.equals("90")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (MCAnalysisSDSLRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                        if (MasterCS.equals("84") & ViceCS1.equals("A0") & ViceCS2.equals("17")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (VAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                        if (MasterCS.equals("91") & ViceCS1.equals("90") & ViceCS2.equals("1F")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (PAnalysisJSMTRxStr(Data, parameterprotcol) == 1) {
                                    return r = 11;
                                }
                            }
                        }
                        if (MasterCS.equals("CD") & ViceCS1.equals("90") & ViceCS2.equals("2F")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {

                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("25")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("81") & ViceCS1.equals("1F") & ViceCS2.equals("90")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (MCAnalysisSDSLRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("48")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("84") & ViceCS1.equals("A0") & ViceCS2.equals("18")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (MAnalysisJSMTRxStr(Data, mbus) == 1) {
                                    return r = 2;
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("49")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("81") & ViceCS1.equals("90") & (ViceCS2.equals("1F") || ViceCS2.equals("2F"))) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (AAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                        if (MasterCS.equals("84") & ViceCS1.equals("A0") & ViceCS2.equals("17")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (VAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("50")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("81") & ViceCS1.equals("90") & (ViceCS2.equals("1F") || ViceCS2.equals("2F"))) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (VSmartAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                        if (MasterCS.equals("84") & ViceCS1.equals("A0") & ViceCS2.equals("17")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (VAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 1;
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals("51")) {
                    LenS = RX.substring(HeadPI + 20, HeadPI + 20 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 13) * 2);
                        MasterCS = Data.substring(18, 18 + 2);
                        ViceCS1 = Data.substring(22, 22 + 2);
                        ViceCS2 = Data.substring(24, 24 + 2);
                        if (MasterCS.equals("84") & ViceCS1.equals("90") & ViceCS2.equals("3F")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (ReportAnalysisJSMTRxStr(Data, protocol) == 1) {
                                    return r = 3;
                                }
                            }
                        }
                        if (MasterCS.equals("85") & ViceCS1.equals("90") & ViceCS2.equals("3F")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2), 0, Data.substring((Frequent.HexS2ToInt(LenS) + 13) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 13) * 2 - 2)) == 1) {
                                if (AvgAnalysisJSMTRxStr(Data, ssumheat) == 1) {
                                    return r = 4;
                                }
                            }
                        }
                    }
                }
                if (HeadS.equals("68") & ProductTypeS.equals(ProductTypeS1) & HeadS.equals(HeadS1)) {
                    LenS = RX.substring(HeadPI + 2, HeadPI + 2 + 2);
                    if (RX.substring(HeadPI + (Frequent.HexS2ToInt(LenS) + 6) * 2 - 2, HeadPI + (Frequent.HexS2ToInt(LenS) + 6) * 2).equals("16")) {
                        Data = RX.substring(HeadPI, HeadPI + (Frequent.HexS2ToInt(LenS) + 6) * 2);
                        MasterCS = Data.substring(8, 8 + 2);
                        ViceCS1 = Data.substring(12, 12 + 2);
                        if (MasterCS.equals("08") & ViceCS1.equals("72")) {
                            if (Frequent.checksum(Data.substring(0, (Frequent.HexS2ToInt(LenS) + 6) * 2 - 2 - 2), 4, Data.substring((Frequent.HexS2ToInt(LenS) + 6) * 2 - 2 - 2, (Frequent.HexS2ToInt(LenS) + 6) * 2 - 2)) == 1) {
                                if (MEAnalysisRxStr(Data, protocol) == 1) {
                                    r = 1;
                                }
                            }
                        }
                    }
                }
            }
            return r;
        } catch (Exception e) {
            r = 0;
            return r;
        }
    }
}
