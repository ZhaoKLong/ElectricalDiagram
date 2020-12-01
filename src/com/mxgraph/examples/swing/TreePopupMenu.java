package com.mxgraph.examples.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreePopupMenu extends JScrollPane implements MouseListener, ActionListener {

    //    private static final long serialVersionUID = 1L;
    JTree tree;
    JPopupMenu popMenu;//右键菜单
    JMenuItem addItem;//添加
    JMenuItem delItem;//删除
    JMenuItem editItem;//修改
    Container con = new Container();

    public TreePopupMenu() {
        DefaultMutableTreeNode root=new DefaultMutableTreeNode();//使用DefaultMutableTreeNode的构造器创建根节点
        DefaultMutableTreeNode node1=new DefaultMutableTreeNode("蔬菜");//使用DefaultMutableTreeNode的构造器创建四个枝节点
        DefaultMutableTreeNode node2=new DefaultMutableTreeNode("水果");
        DefaultMutableTreeNode node3=new DefaultMutableTreeNode("礼品");
        DefaultMutableTreeNode node4=new DefaultMutableTreeNode("家用小物件");
        root.add(node1); //将四个枝节点添加到根节点中
        root.add(node2);
        root.add(node3);
        root.add(node4);
        DefaultMutableTreeNode leafnode=new DefaultMutableTreeNode("白菜"); //利用DefaultMutableTreeNode的构造器构造器创建出叶节点，再将页节点分别添加到不同的枝节点上
        node1.add(leafnode);
        leafnode=new DefaultMutableTreeNode("大蒜");
        node1.add(leafnode);
        leafnode=new DefaultMutableTreeNode("土豆");
        node1.add(leafnode);
        leafnode=new DefaultMutableTreeNode("苹果");
        node2.add(leafnode);
        leafnode=new DefaultMutableTreeNode("香蕉");
        node2.add(leafnode);
        leafnode=new DefaultMutableTreeNode("西瓜");
        node2.add(leafnode);

        leafnode=new DefaultMutableTreeNode("礼品");
        node3.add(leafnode);

        leafnode=new DefaultMutableTreeNode("茅台酒");
        node3.add(leafnode);
        leafnode=new DefaultMutableTreeNode("营养麦片");
        node3.add(leafnode);
        leafnode=new DefaultMutableTreeNode("保健食品");
        node3.add(leafnode);

        leafnode=new DefaultMutableTreeNode("味精");
        node4.add(leafnode);

        leafnode=new DefaultMutableTreeNode("酱油");
        node4.add(leafnode);
        leafnode=new DefaultMutableTreeNode("洗洁精");
        node4.add(leafnode);
        leafnode=new DefaultMutableTreeNode("保险袋");
        node4.add(leafnode);


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
        if (path == null) {
            return;
        }
        tree.setSelectionPath(path);

        if (e.getButton() == 3) {
            popMenu.show(tree, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (e.getSource() == addItem) {
            ((DefaultTreeModel) tree.getModel()).insertNodeInto(new DefaultMutableTreeNode("Test"),
                    node, node.getChildCount());
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
 

