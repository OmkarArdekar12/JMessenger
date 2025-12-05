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

        ImageIcon b1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/back.png"));
        Image b2 = b1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon b3 = new ImageIcon(b2);
        JLabel back = new JLabel(b3);
        back.setBounds(5, 20, 25, 25);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        p1.add(back);

        ImageIcon u1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/user1.png"));
        Image u2 = u1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon u3 = new ImageIcon(u2);
        JLabel userProfile = new JLabel(u3);
        userProfile.setBounds(45, 10, 50, 50);
        p1.add(userProfile);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/call.png"));
        Image c2 = c1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon c3 = new ImageIcon(c2);
        JLabel phoneCall = new JLabel(c3);
        phoneCall.setBounds(315, 20, 30, 30);
        p1.add(phoneCall);

        ImageIcon v1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image v2 = v1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon v3 = new ImageIcon(v2);
        JLabel videoCall = new JLabel(v3);
        videoCall.setBounds(355, 20, 30, 30);
        p1.add(videoCall);

        ImageIcon m1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/more.png"));
        Image m2 = m1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon m3 = new ImageIcon(m2);
        JLabel moreOptions = new JLabel(m3);
        moreOptions.setBounds(400, 20, 30, 30);
        p1.add(moreOptions);

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

