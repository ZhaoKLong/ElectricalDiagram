package com.mxgraph.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mxgraph.entity.Trunkline;

/**
 * @description: 测试工具类
 * @author: Zhaokl
 * @create: 2020-09-04 17:35
 **/
public class TestUtils {
    /**
     * 文件流数据
     */
    private String inNext;

    /**
     * 本地详细数据
     */
    private static JSONArray jsonArray;

    /**
     * 线路列表
     */
    private static List<Trunkline> trunklineList = new ArrayList<>();

    public TestUtils() {
        File inputFile = new File("src/com/mxgraph/util/测试线路.txt");
        if (inputFile.isFile() && inputFile.exists()) { //判断文件是否存在
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(inputFile), "utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                inNext = bufferedReader.readLine();
                read.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            inNext = "";
            System.err.println("路径错误");
        }
//        File inputFile = new File("src/com/mxgraph/util/测试线路.txt");
//        File newFile = new File("");
//        String absolutePath = newFile.getAbsolutePath();
//        String s = absolutePath.replaceAll("\\\\", "/");
//        System.out.println(s + "/src/com/mxgraph/util/测试线路.txt");
//        File inputFile = new File(s + "/src/com/mxgraph/util/测试线路.txt");
//        try {
//            inNext = new Scanner(inputFile).next();
//        } catch (FileNotFoundException e) {
//            inNext = "";
//        }
        if (!"".equals(inNext) && null != inNext) {
            jsonArray = JSON.parseArray(inNext);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Trunkline trunkline = new Trunkline();
                trunkline.setTrunklineId((Integer) jsonObject.get("trunklineId"));
                trunkline.setName((String) jsonObject.get("name"));
                trunkline.setData((JSONArray) jsonObject.get("data"));
                if (null != jsonObject.get("children") && "[]" != jsonObject.get("children") && "" != jsonObject.get("children")) {
                    JSONArray childrenJsonArray = (JSONArray) jsonObject.get("children");
                    List<Trunkline> childrenList = new ArrayList<>();
                    for (int j = 0; j < childrenJsonArray.size(); j++) {
                        JSONObject childrenJsonObject = (JSONObject) childrenJsonArray.get(j);
                        Trunkline childrenTrunkline = new Trunkline();
                        childrenTrunkline.setTrunklineId((Integer) childrenJsonObject.get("trunklineId"));
                        childrenTrunkline.setName((String) childrenJsonObject.get("name"));
                        childrenTrunkline.setData((JSONArray) childrenJsonObject.get("data"));
                        childrenTrunkline.setParentId((Integer) jsonObject.get("trunklineId"));
                        childrenList.add(childrenTrunkline);
                    }
                    trunkline.setChildren(childrenList);
                }
                if (null != jsonObject.get("parentId")) {
                    trunkline.setParentId((Integer) jsonObject.get("parentId"));
                }
                trunklineList.add(trunkline);
            }
        }
    }

    /**
     * 获取详细数据
     *
     * @return 详细数据
     */
    public static List<Trunkline> getJsonArray(Integer trunklineId) {
        List<Trunkline> showTrunklineList = new ArrayList<>();
        for (Trunkline trunkline : trunklineList) {
            if (trunkline.getParentId().equals(trunklineId) || trunkline.getTrunklineId().equals(trunklineId)) {
                showTrunklineList.add(trunkline);
            }
        }
        return showTrunklineList;
    }

    /**
     * 获取线路数据
     *
     * @return 获取线路数据
     */
    public static List<Trunkline> getTrunklineList() {
        return trunklineList;
    }

    /**
     * 根据Id获取线路
     *
     * @param trunklineId
     * @return
     */
    public static Trunkline getTrunklineById(Integer trunklineId) {
        Trunkline tunkline = new Trunkline();
        for (Trunkline trunklineItem : trunklineList) {
            if (trunklineItem.getTrunklineId().equals(trunklineId)) {
                tunkline = trunklineItem;
            }
        }
        return tunkline;
    }

    /**
     * 根据名字获取线路
     *
     * @param trunklineName
     * @return
     */
    public static Trunkline getTrunklineByName(String trunklineName) {
        Trunkline tunkline = new Trunkline();
        for (Trunkline trunklineItem : trunklineList) {
            if (trunklineItem.getName().equals(trunklineName)) {
                tunkline = trunklineItem;
            } else if (null != trunklineItem.getChildren() && trunklineItem.getChildren().size() != 0) {
                for (Trunkline trunklineChild : trunklineItem.getChildren()) {
                    if (trunklineChild.getName().equals(trunklineName)) {
                        tunkline = trunklineChild;
                    }
                }
            }
        }
        return tunkline;
    }

    /**
     * 刷新object数据
     *
     * @return object数据
     * @throws FileNotFoundException
     */
    public List<Trunkline> refreshData() throws FileNotFoundException {
        File inputFile = new File("src/com/mxgraph/util/测试线路.txt");
        inNext = new Scanner(inputFile).next();
        jsonArray = JSON.parseArray(inNext);
        return getJsonArray(0);
    }

    /**
     * 更新object数据
     */
    public void updateDate() {
        //TODO
    }

    /**
     * object转double
     *
     * @param object
     * @return
     */
    public static double objectToDouble(Object object) {
        double data = 0;
        if (object.getClass() == Integer.class) {
            data += ((Integer) object).doubleValue();
        } else {
            data += (Double) object;
        }
        return data;
    }
}
