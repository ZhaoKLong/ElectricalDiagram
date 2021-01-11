package com.mxgraph.examples.swing;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Util {
    //经度   longitude
    //纬度   latitude
    private static String localText;

    static String getLocalTxt(String path) {
        if ("".equals(path) || path == null) {
            localText = "";
        } else {
//        File file = new File("G:\\graphDemo\\ElectricalDiagram\\testdata.txt");  //源文本
//        File file = new File("D:\\work\\javaWorkSapce\\ElectricalDiagram\\src\\com\\mxgraph\\examples\\swing\\testdata.txt");  //源文本
            File file = new File(path);  //源文本
//        File file = new File("D:\\code.txt");  //代码+标记文本
            BufferedReader br = null;
            StringBuffer sb = null;
            try {
                //在字节流的基础上套用InputStreamReader转换为字符流
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "UTF-8"));
                sb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //打印文件的所有内容
            // String LocalTxt存放本地文件中的json数据
            localText = new String(sb);
        }
        return localText;
    }

    /**
     * 获取坐标边界
     *
     * @return List
     */
    public static double[] getLocalBorder(JSONArray jsonArray) {
        double minla = 180;    //最小纬度
        double maxla = 0;      //最大纬度
        double minlo = 180;    //最小经度
        double maxlo = 0;      //最大经度
        if (null != jsonArray) {
            int length = jsonArray.size();
            for (int i = 0; i < length; i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                if (Double.parseDouble((String) item.getJSONObject("prev").get("latitude")) < minla) {
                    minla = Double.parseDouble((String) item.getJSONObject("prev").get("latitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("prev").get("latitude")) > maxla) {
                    maxla = Double.parseDouble((String) item.getJSONObject("prev").get("latitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("next").get("latitude")) < minla) {
                    minla = Double.parseDouble((String) item.getJSONObject("next").get("latitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("next").get("latitude")) > maxla) {
                    maxla = Double.parseDouble((String) item.getJSONObject("next").get("latitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("prev").get("longitude")) < minlo) {
                    minlo = Double.parseDouble((String) item.getJSONObject("prev").get("longitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("prev").get("longitude")) > maxlo) {
                    maxlo = Double.parseDouble((String) item.getJSONObject("prev").get("longitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("next").get("longitude")) < minlo) {
                    minlo = Double.parseDouble((String) item.getJSONObject("next").get("longitude"));
                }
                if (Double.parseDouble((String) item.getJSONObject("next").get("longitude")) > maxlo) {
                    maxlo = Double.parseDouble((String) item.getJSONObject("next").get("longitude"));
                }
            }
        }
        return new double[]{minlo, maxlo, minla, maxla};
    }

    /**
     * 计算画幅大小，经纬度与像素比例
     */
    public static void pictureSize(JSONArray jsonArray) {
        JSONArray objects1 = JSONArray.parseArray(localText);
        //TODO
        System.err.println(objects1);
    }

    /**
     * 获取所有线段
     *
     * @return List
     */
    public static List<JSONObject> getAllCable(JSONArray jsonArray) {
        List<JSONObject> list = new ArrayList<>();
        if (null != jsonArray) {
            int length = jsonArray.size();
            for (int i = 0; i < length; i++) {
                list.add(jsonArray.getJSONObject(i));
            }
        }
        return list;
    }

    /**
     * 获取图标相对路径
     *
     * @param type
     * @return
     */
    public static String getIconPath(int type) {
        switch (type) {
            case 1:
                return "src/com/mxgraph/util/icon/" + "publicTransformer" + ".png";
            case 2:
                return "src/com/mxgraph/util/icon/" + "purposeTransformer" + ".png";
            case 3:
                return "src/com/mxgraph/util/icon/" + "circuitBreaker" + ".png";
            case 4:
                return "src/com/mxgraph/util/icon/" + "breaker" + ".png";
            case 5:
                return "src/com/mxgraph/util/icon/" + "loadSwitch" + ".png";
            case 6:
                return "src/com/mxgraph/util/icon/" + "branchBox" + ".png";
            case 13:
                return "src/com/mxgraph/util/icon/" + "boxChangePublic" + ".png";
            case 7:
                return "src/com/mxgraph/util/icon/" + "boxChange" + ".png";
            case 9:
                return "src/com/mxgraph/util/icon/" + "faultIndicator" + ".png";
            case 10:
                return "src/com/mxgraph/util/icon/" + "ringNetworkCabinet" + ".png";
            case 11:
                return "src/com/mxgraph/util/icon/" + "dropFuse" + ".png";
            default:
                return "src/com/mxgraph/util/icon/" + "";
        }
    }

    /**
     * 获取旋转角度
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getDegree(double x1, double y1, double x2, double y2) {
        double atan = Math.atan2(x2 - x1, y1 - y2);
        if (atan > 0) {
            atan = (180 * atan / Math.PI) - 90;
        } else {
            atan = (180 * atan / Math.PI) + 90;
        }
        return atan;
    }

    /**
     * 获取偏移量
     *
     * @param degree
     * @return
     */
    public static double[] getOffset(double degree, double realScale, JSONObject cable, int i, boolean isToPoleId) {
        double realDegree = degree * Math.PI / 180;
        double tan = Math.tan(realDegree);
        double cos = Math.cos(realDegree);
        double sin = Math.sin(realDegree);
        double realLength = (i + 0.5) * realScale;
        double v;
        double x = 0;
        double y = 0;
        double prevLa = 0;
        double nextLa = 0;
        if (isToPoleId) {
            prevLa = (Double.parseDouble((String) cable.getJSONObject("prev").get("latitude")));
            nextLa = (Double.parseDouble((String) cable.getJSONObject("next").get("latitude")));
        } else {
            prevLa = (Double.parseDouble((String) cable.getJSONObject("next").get("latitude")));
            nextLa = (Double.parseDouble((String) cable.getJSONObject("prev").get("latitude")));
        }
        if (tan < 0) {
            v = -((realScale / 2) * (1 - tan)) / (Math.sqrt(tan * tan + 1));
            if (prevLa > nextLa) {
                x = -realLength * cos;
                y = -realLength * sin;
            } else {
                x = realLength * cos;
                y = realLength * sin;
            }
        } else {
            v = -((realScale / 2) * (1 + tan)) / (Math.sqrt(tan * tan + 1));
            if (prevLa > nextLa) {
                x = realLength * cos;
                y = realLength * sin;
            } else {
                x = -realLength * cos;
                y = -realLength * sin;
            }
        }
        return new double[]{v + x, v + y};
    }

    /**
     * 获取实际长度
     */
    public static double getLength(double[] localBorder) {
        double radLat1 = localBorder[2] * Math.PI / 180.0;
        double radLat2 = localBorder[0] * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = localBorder[3] * Math.PI / 180.0 - localBorder[1] * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return Math.round(s * 6378.137 * 10000) / 10;
    }
}
