package com.mxgraph.examples.swing;

import com.alibaba.fastjson.JSONArray;
import com.mxgraph.entity.Trunkline;
import com.mxgraph.examples.swing.editor.JTableRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import static com.mxgraph.util.TestUtils.getTrunklineByName;
import static com.mxgraph.util.TestUtils.getTrunklineList;

public class TreePopupMenu extends JScrollPane implements MouseListener, ActionListener {

    private JTree tree;
    /**
     * 右键菜单
     */
    private JPopupMenu popMenu;
    /**
     * 添加
     */
    private JMenuItem addItem;
    /**
     * 删除
     */
    private JMenuItem delItem;
    /**
     * 修改
     */
    private JMenuItem editItem;
    Container con = new Container();

    public TreePopupMenu() {
        //使用DefaultMutableTreeNode的构造器创建根节点
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode leafnode = new DefaultMutableTreeNode("");
        for (Trunkline trunkline : getTrunklineList()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(trunkline.getName());
            root.add(node);
            if (null != trunkline.getChildren() && trunkline.getChildren().size() != 0) {
                for (Trunkline childrenTrunkline : trunkline.getChildren()) {
                    leafnode = new DefaultMutableTreeNode(childrenTrunkline.getName());
                    node.add(leafnode);
                }
            }
        }
        tree = new JTree(root);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(this);
        tree.setCellEditor(new DefaultTreeCellEditor(tree, new DefaultTreeCellRenderer()));
        tree.setRootVisible(false);

        popMenu = new JPopupMenu();
        addItem = new JMenuItem("添加");
        addItem.addActionListener(this);
        delItem = new JMenuItem("删除");
        delItem.addActionListener(this);
        editItem = new JMenuItem("修改");
        editItem.addActionListener(this);

        popMenu.add(addItem);
        popMenu.add(delItem);
        popMenu.add(editItem);
        this.setViewportView(tree);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        // 点击次数
//        System.out.println(e.getClickCount());
        // 点击按键
//        System.out.println(e.getButton());
        if (path == null) {
            return;
        }
        if (e.getClickCount() == 2) {
            DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) path.getLastPathComponent();
            String pathString = lastPathComponent.toString();
            Trunkline trunkline = getTrunklineByName(pathString);
            JSONArray jsonArray = trunkline.getData();
            if (null != trunkline.getChildren() && trunkline.getChildren().size() != 0) {
                for (Trunkline trunklineChild : trunkline.getChildren()) {
                    jsonArray.addAll(trunklineChild.getData());
                }
            }
            JTabPanel.addPanel(trunkline.getName(), jsonArray);
            MyTable.changeData(trunkline.getName(), jsonArray);
            tree.setSelectionPath(path);
        }
        if (e.getButton() == 3) {
            popMenu.show(tree, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (e.getSource() == addItem) {
            ((DefaultTreeModel) tree.getModel()).insertNodeInto(new DefaultMutableTreeNode("Test"), node, node.getChildCount());
            tree.expandPath(tree.getSelectionPath());
        } else if (e.getSource() == delItem) {
            if (node.isRoot()) {
                return;
            }
            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
        } else if (e.getSource() == editItem) {
            tree.startEditingAtPath(tree.getSelectionPath());
        }
    }
}
 

