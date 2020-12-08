package com.mxgraph.examples.swing;

import javax.swing.*;

/**
 * @description:
 * @author: Zhaokl
 * @create: 2020-12-07 17:17
 **/
public class MyScrollPane {
    public static JScrollPane getMyScrollPane(String path) {
        return new JScrollPane(new MyPanel(path));
    }
}
