package com.mxgraph.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mxgraph.entity.excelEntity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mxgraph.util.TestUtils.objectToDouble;

/**
 * @description:
 * @author: Zhaokl
 * @create: 2021-01-13 11:00
 **/
public class excelUtil {

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public static void simpleWrite(String name, JSONArray jsonArray) {
        String fileName = name + ".xlsx";
        int sheetNum = 5;
        List<Class> sheetClassList = new ArrayList<>();
        List<String> sheetNameList = new ArrayList<>();
        List<List> data = data(name, jsonArray);
        sheetClassList.add(BaseHeadData.class);
        sheetClassList.add(TransformerHeadData.class);
        sheetClassList.add(CapacitorHeadData.class);
        sheetClassList.add(SwitchHeadData.class);
        sheetClassList.add(CrossOverHeadData.class);
        sheetNameList.add(name + "基础资料");
        sheetNameList.add("配电变压器台账");
        sheetNameList.add("电容器台账");
        sheetNameList.add("开关台账");
        sheetNameList.add("交叉跨越记录");
        ExcelWriter excelWriter = null;
        try {
            // 指定文件
            excelWriter = EasyExcel.write(fileName).build();
            for (int i = 0; i < sheetNum; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetNameList.get(i)).head(sheetClassList.get(i)).build();
                excelWriter.write(data.get(i), writeSheet);
            }
        } finally {
            // finish关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private static List<List> data(String name, JSONArray jsonArray) {
        int trunklineId = 0;
        int transformerNum = 0;
        int capacitorNum = 0;
        int switchNum = 0;
        int crossOverNum = 0;
        List<List> totalList = new ArrayList<>();
        List<BaseData> list1 = new ArrayList<>();
        List<TransformerData> list2 = new ArrayList<>();
        List<CapacitorData> list3 = new ArrayList<>();
        List<SwitchData> list4 = new ArrayList<>();
        List<CrossOverData> list5 = new ArrayList<>();
        BaseData baseData = new BaseData();
        baseData.setIndex(1);
        baseData.setTrunklineName(name);
        baseData.setTotalLength((double) 0);
        baseData.setMainLength((double) 0);
        baseData.setBranchLength((double) 0);
        baseData.setCableLength((double) 0);
        baseData.setBareWireLength((double) 0);
        baseData.setSwitchNum(0);
        baseData.setCapacitorNum(0);
        baseData.setTransformerNum(0);
        baseData.setTransformerCapacity((double) 0);
        baseData.setBoxTransformerNum(0);
        baseData.setBoxTransformerCapacity((double) 0);
        baseData.setPoleTransformerNum(0);
        baseData.setPoleTransformerCapacity((double) 0);
        baseData.setPublicTransformerNum(0);
        baseData.setPublicTransformerCapacity((double) 0);
        baseData.setPrivateTransformerNum(0);
        baseData.setPrivateTransformerCapacity((double) 0);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            double length = objectToDouble(jsonObject.get("length")) / 1000;
            if (i == 0 && jsonObject.get("trunklineId") != null) {
                trunklineId = (Integer) jsonObject.get("trunklineId");
            }
            if ((Integer) jsonObject.getJSONObject("prev").get("isFactory") == 1) {
                baseData.setFactory((String) jsonObject.getJSONObject("prev").get("name"));
            } else if ((Integer) jsonObject.getJSONObject("next").get("isFactory") == 1) {
                baseData.setFactory((String) jsonObject.getJSONObject("next").get("name"));
            }
            baseData.setTotalLength(baseData.getTotalLength() + length);
//            if (trunklineId == (Integer) jsonObject.get("trunklineId")) {
//                baseData.setMainLength(baseData.getMainLength() + length);
//            } else {
//                baseData.setBranchLength(baseData.getBranchLength() + length);
//            }
            // TODO
            baseData.setMainLength(baseData.getMainLength() + length);
            if (jsonObject.get("type") != null) {
                baseData.setCableLength(baseData.getCableLength() + length);
            } else {
                baseData.setBareWireLength(baseData.getBareWireLength() + length);
            }
            try {
                JSONArray equipmentList = jsonObject.getJSONObject("prev").getJSONArray("equipmentList");
                String poleName = (String) jsonObject.getJSONObject("prev").get("name");
                if (equipmentList.size() != 0) {
                    for (int j = 0; j < equipmentList.size(); j++) {
                        JSONObject prevEquipment = (JSONObject) equipmentList.get(j);
                        String equipmentName = (String) prevEquipment.get("name");
                        Integer type = (Integer) prevEquipment.get("type");
                        String model = (String) prevEquipment.get("model");
                        double capacity = objectToDouble(prevEquipment.get("capacity"));
                        String putInto = (String) prevEquipment.get("putInto");
                        String vendor = (String) prevEquipment.get("vendor");
                        String remark = (String) prevEquipment.get("remark");
                        // 负荷开关
                        if (type == 5) {
                            SwitchData switchData = new SwitchData();
                            switchData.setIndex(++switchNum);
                            switchData.setName(name);
                            switchData.setPoleId(poleName);
                            switchData.setModel(model);
                            switchData.setVendor(vendor);
                            if (!"".equals(putInto)) {
                                switchData.setPutInto(sf.parse(putInto));
                            }
                            baseData.setSwitchNum(baseData.getSwitchNum() + 1);
                            list4.add(switchData);
                            // 电容器
                        } else if (type == 14) {
                            CapacitorData capacitorData = new CapacitorData();
                            capacitorData.setIndex(++capacitorNum);
                            capacitorData.setPoleId(poleName);
                            capacitorData.setModel(model);
                            capacitorData.setNum(1);
                            capacitorData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                capacitorData.setPutInto(sf.parse(putInto));
                            }
                            capacitorData.setVendor(vendor);
                            capacitorData.setRemark(remark);
                            baseData.setCapacitorNum(baseData.getCapacitorNum() + 1);
                            list3.add(capacitorData);
                            // 公变
                        } else if (type == 1) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("公变");
                            transformerData.setType("柱上变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setPoleTransformerNum(baseData.getPoleTransformerNum() + 1);
                            baseData.setPoleTransformerCapacity(baseData.getPoleTransformerCapacity() + capacity);
                            baseData.setPublicTransformerNum(baseData.getPublicTransformerNum() + 1);
                            baseData.setPublicTransformerCapacity(baseData.getPublicTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 专变
                        } else if (type == 2) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("专变");
                            transformerData.setType("柱上变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setPoleTransformerNum(baseData.getPoleTransformerNum() + 1);
                            baseData.setPoleTransformerCapacity(baseData.getPoleTransformerCapacity() + capacity);
                            baseData.setPrivateTransformerNum(baseData.getPrivateTransformerNum() + 1);
                            baseData.setPrivateTransformerCapacity(baseData.getPrivateTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 箱专变
                        } else if (type == 7) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("专变");
                            transformerData.setType("箱变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setBoxTransformerNum(baseData.getBoxTransformerNum() + 1);
                            baseData.setBoxTransformerCapacity(baseData.getBoxTransformerCapacity() + capacity);
                            baseData.setPrivateTransformerNum(baseData.getPrivateTransformerNum() + 1);
                            baseData.setPrivateTransformerCapacity(baseData.getPrivateTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 箱公变
                        } else if (type == 13) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("公变");
                            transformerData.setType("箱变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setBoxTransformerNum(baseData.getBoxTransformerNum() + 1);
                            baseData.setBoxTransformerCapacity(baseData.getBoxTransformerCapacity() + capacity);
                            baseData.setPublicTransformerNum(baseData.getPublicTransformerNum() + 1);
                            baseData.setPublicTransformerCapacity(baseData.getPublicTransformerCapacity() + capacity);
                            list2.add(transformerData);
                        }
                    }
                }
                equipmentList = jsonObject.getJSONObject("next").getJSONArray("equipmentList");
                poleName = (String) jsonObject.getJSONObject("next").get("name");
                if (equipmentList.size() != 0) {
                    for (int j = 0; j < equipmentList.size(); j++) {
                        JSONObject prevEquipment = (JSONObject) equipmentList.get(j);
                        String equipmentName = (String) prevEquipment.get("name");
                        Integer type = (Integer) prevEquipment.get("type");
                        String model = (String) prevEquipment.get("model");
                        double capacity = objectToDouble(prevEquipment.get("capacity"));
                        String putInto = (String) prevEquipment.get("putInto");
                        String vendor = (String) prevEquipment.get("vendor");
                        String remark = (String) prevEquipment.get("remark");
                        // 负荷开关
                        if (type == 5) {
                            SwitchData switchData = new SwitchData();
                            switchData.setIndex(++switchNum);
                            switchData.setName(name);
                            switchData.setPoleId(poleName);
                            switchData.setModel(model);
                            switchData.setVendor(vendor);
                            if (!"".equals(putInto)) {
                                switchData.setPutInto(sf.parse(putInto));
                            }
                            baseData.setSwitchNum(baseData.getSwitchNum() + 1);
                            list4.add(switchData);
                            // 电容器
                        } else if (type == 14) {
                            CapacitorData capacitorData = new CapacitorData();
                            capacitorData.setIndex(++capacitorNum);
                            capacitorData.setPoleId(poleName);
                            capacitorData.setModel(model);
                            capacitorData.setNum(1);
                            capacitorData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                capacitorData.setPutInto(sf.parse(putInto));
                            }
                            capacitorData.setVendor(vendor);
                            capacitorData.setRemark(remark);
                            baseData.setCapacitorNum(baseData.getCapacitorNum() + 1);
                            list3.add(capacitorData);
                            // 公变
                        } else if (type == 1) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("公变");
                            transformerData.setType("柱上变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setPoleTransformerNum(baseData.getPoleTransformerNum() + 1);
                            baseData.setPoleTransformerCapacity(baseData.getPoleTransformerCapacity() + capacity);
                            baseData.setPublicTransformerNum(baseData.getPublicTransformerNum() + 1);
                            baseData.setPublicTransformerCapacity(baseData.getPublicTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 专变
                        } else if (type == 2) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("专变");
                            transformerData.setType("柱上变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setPoleTransformerNum(baseData.getPoleTransformerNum() + 1);
                            baseData.setPoleTransformerCapacity(baseData.getPoleTransformerCapacity() + capacity);
                            baseData.setPrivateTransformerNum(baseData.getPrivateTransformerNum() + 1);
                            baseData.setPrivateTransformerCapacity(baseData.getPrivateTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 箱专变
                        } else if (type == 7) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("专变");
                            transformerData.setType("箱变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setBoxTransformerNum(baseData.getBoxTransformerNum() + 1);
                            baseData.setBoxTransformerCapacity(baseData.getBoxTransformerCapacity() + capacity);
                            baseData.setPrivateTransformerNum(baseData.getPrivateTransformerNum() + 1);
                            baseData.setPrivateTransformerCapacity(baseData.getPrivateTransformerCapacity() + capacity);
                            list2.add(transformerData);
                            // 箱公变
                        } else if (type == 13) {
                            TransformerData transformerData = new TransformerData();
                            transformerData.setIndex(++transformerNum);
                            transformerData.setName(equipmentName);
                            transformerData.setIsPublic("公变");
                            transformerData.setType("箱变");
                            transformerData.setModel(model);
                            transformerData.setCapacity(capacity);
                            if (!"".equals(putInto)) {
                                transformerData.setPutInto(sf.parse(putInto));
                            }
                            transformerData.setVendor(vendor);
                            baseData.setTransformerNum(baseData.getTransformerNum() + 1);
                            baseData.setTransformerCapacity(baseData.getTransformerCapacity() + capacity);
                            baseData.setBoxTransformerNum(baseData.getBoxTransformerNum() + 1);
                            baseData.setBoxTransformerCapacity(baseData.getBoxTransformerCapacity() + capacity);
                            baseData.setPublicTransformerNum(baseData.getPublicTransformerNum() + 1);
                            baseData.setPublicTransformerCapacity(baseData.getPublicTransformerCapacity() + capacity);
                            list2.add(transformerData);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ((int) jsonObject.get("isAcross") == 1) {
                CrossOverData crossOverData = new CrossOverData();
                crossOverData.setIndex(++crossOverNum);
                crossOverData.setName((String) jsonObject.get("name"));
                crossOverData.setPoleId(jsonObject.getJSONObject("prev").get("name") + "---" + jsonObject.getJSONObject("next").get("name"));
                crossOverData.setRemark((String) jsonObject.get("acrossRemark"));
                list5.add(crossOverData);
            }
        }
        list1.add(baseData);
        totalList.add(list1);
        totalList.add(list2);
        totalList.add(list3);
        totalList.add(list4);
        totalList.add(list5);
        return totalList;
    }
}
