package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Server extends JFrame implements ActionListener {
    Server() {
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(10, 100, 90));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/back.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        p1.add(back);


        setSize(450, 700);
        setLocation(200, 50);
        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

    }

    public static void main(String args[]) {
        new Server();
    }
}

