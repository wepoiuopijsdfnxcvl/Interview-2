package com.klst.client;

import com.klst.client.component.MainPanelCreate;
import com.klst.client.util.Constants;
import com.klst.client.util.Log;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame {

    private static final Logger LOGGER = Log.getLogger(MainFrame.class.getSimpleName());

    public static void main (String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame= new JFrame(Constants.CLIENT_TITLE);
        ImageIcon icon=new ImageIcon(MainFrame.class.getResource("/img/logo.png"));
        jFrame.setIconImage(icon.getImage());
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        jFrame.pack();
        //最大化显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(jFrame.getGraphicsConfiguration());//任务栏高度
        jFrame.setSize((int)width,(int)height - insets.bottom);//设置大小
        jFrame.setLocation(0,0); //设置窗体居中显示
        jFrame.setResizable(false);//禁用最大化按钮

        //主面板加载
        LOGGER.info("main panel begin load...");
        new MainPanelCreate(jFrame);
        LOGGER.info("main panel load completed.");

        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, Constants.SYSTEM_EXIT, Constants.WARNING,2);
                if(option == 0){//0:确认 1：否 2：取消
                    System.exit(0);
                }
            }
        });

    }
}
