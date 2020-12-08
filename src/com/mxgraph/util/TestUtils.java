package com.mxgraph.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @description: 测试工具类
 * @author: Zhaokl
 * @create: 2020-09-04 17:35
 **/
public class TestUtils {
    /**
     * 文件流数据
     */
    private Scanner in;

    /**
     * object数据
     */
    private static JSONArray jsonArray;

    public TestUtils() throws FileNotFoundException {
        File inputFile = new File("src/com/mxgraph/util/test.txt");
        in = new Scanner(inputFile);
        jsonArray = JSON.parseArray(in.next());
    }

    /**
     * 获取object数据
     *
     * @return object数据
     */
    public JSONArray getJsonArray() {
        return jsonArray;
    }

    /**
     * 刷新object数据
     *
     * @return object数据
     * @throws FileNotFoundException
     */
    public JSONArray refreshJsonObject() throws FileNotFoundException {
        File inputFile = new File("src/com/mxgraph/util/test.txt");
        Scanner oldIn = in;
        in = new Scanner(inputFile);
        if (oldIn != in) {
            jsonArray = JSON.parseArray(in.next());
        }
        return getJsonArray();
    }

    /**
     * 更新object数据
     */
}
