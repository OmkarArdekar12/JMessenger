package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {
    JTextField text;
    JPanel messagePanel;
    JScrollPane scrollPane;
    Box vertical = Box.createVerticalBox();
    static JFrame frame = new JFrame();
    static DataOutputStream dout;
    Server() {
        frame.setLayout(null);
        frame.setTitle("JMessenger - Server");
    
        JPanel msgHeaderPanel = new JPanel();
        msgHeaderPanel.setBackground(new Color(7, 94, 84));
        msgHeaderPanel.setBounds(0, 0, 450, 70);
        msgHeaderPanel.setLayout(null);
        frame.add(msgHeaderPanel);

        ImageIcon b1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/back.png"));
        Image b2 = b1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon b3 = new ImageIcon(b2);
        JLabel back = new JLabel(b3);
        back.setBounds(5, 20, 25, 25);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        msgHeaderPanel.add(back);

        ImageIcon u1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/user1.png"));
        Image u2 = u1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon u3 = new ImageIcon(u2);
        JLabel userProfile = new JLabel(u3);
        userProfile.setBounds(45, 10, 50, 50);
        msgHeaderPanel.add(userProfile);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/call.png"));
        Image c2 = c1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon c3 = new ImageIcon(c2);
        JLabel phoneCall = new JLabel(c3);
        phoneCall.setBounds(310, 20, 30, 30);
        msgHeaderPanel.add(phoneCall);

        ImageIcon v1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/video.png"));
        Image v2 = v1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon v3 = new ImageIcon(v2);
        JLabel videoCall = new JLabel(v3);
        videoCall.setBounds(355, 20, 30, 30);
        msgHeaderPanel.add(videoCall);

        ImageIcon m1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/more.png"));
        Image m2 = m1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon m3 = new ImageIcon(m2);
        JLabel moreOptions = new JLabel(m3);
        moreOptions.setBounds(400, 20, 30, 30);
        msgHeaderPanel.add(moreOptions);

        JLabel name = new JLabel("Alice");
        name.setBounds(114, 15, 100, 20);
        name.setFont(new Font("Roboto", Font.BOLD, 18));
        name.setForeground(Color.BLACK);
        msgHeaderPanel.add(name);

        JLabel status = new JLabel("● Active now");
        status.setBounds(114, 35, 100, 20);
        status.setFont(new Font("Roboto", Font.PLAIN, 14));
        status.setForeground(Color.GREEN);
        msgHeaderPanel.add(status);

        messagePanel = new JPanel();
        messagePanel.setBackground(new Color(236, 229, 221));
        messagePanel.setLayout(new BorderLayout());

        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setBounds(0, 70, 450, 530);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(scrollPane);
        vertical.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(240, 240, 240));
        inputPanel.setLayout(null);
        inputPanel.setBounds(0, 600, 450, 100);
        frame.add(inputPanel);

        text = new JTextField() {
            private String placeholder = "Type a message...";

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (getText().length() == 0) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));

                    Insets in = getInsets();
                    FontMetrics fm = g2.getFontMetrics();
                    int x = in.left + 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                    g2.drawString(placeholder, x, y);
                    g2.dispose();
                }
            }
        };
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        text.setBounds(5, 5, 300, 50);
        text.setFont(new Font("Roboto", Font.PLAIN, 16));
        inputPanel.add(text);

        ImageIcon sendIcon = new ImageIcon(ClassLoader.getSystemResource("chatting/application/assets/icons/send.png"));
        Image img = sendIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        sendIcon = new ImageIcon(img);

        JButton sendBtn = new JButton("Send", sendIcon);
        sendBtn.setBounds(310, 5, 120, 50);
        sendBtn.setFont(new Font("Roboto", Font.PLAIN, 18));
        sendBtn.setBackground(new Color(37, 211, 102));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setBorderPainted(false);
        sendBtn.setFocusPainted(false);
        sendBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendBtn.addActionListener(this);
        inputPanel.add(sendBtn);

        frame.setSize(450, 700);
        frame.setLocation(200, 50);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
    }

    private void autoScroll() {
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(
                scrollPane.getVerticalScrollBar().getMaximum()
            );
        });
    }

    private void sendMessage() {
        try {
            String msg = text.getText();
        
            if(msg.trim().length() == 0) {
                return;
            }
        
            JLabel msgLabel = new JLabel("<html><p style='width: 150px;'>" + msg + "</p></html>");
            msgLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
            msgLabel.setBackground(new Color(220, 248, 198));
            msgLabel.setOpaque(true);
            msgLabel.setBorder(new EmptyBorder(5, 5, 5, 7));
            msgLabel.setForeground(Color.BLACK);
        
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            JLabel time = new JLabel();
            time.setText(sdf.format(calendar.getTime()).toUpperCase());
            time.setFont(new Font("Roboto", Font.PLAIN, 12));
            time.setBackground(new Color(220, 248, 198));
            time.setOpaque(true);
            time.setBorder(new EmptyBorder(5, 5, 5, 7));
            time.setForeground(Color.BLACK);
        
            JPanel message = new JPanel();
            message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
            message.setBackground(new Color(220, 248, 198));
            message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
            message.add(msgLabel);
            message.add(time);
        
            JPanel rightAlign = new JPanel(new BorderLayout());
            rightAlign.add(message, BorderLayout.LINE_END);
            rightAlign.setBackground(new Color(236, 229, 221));
        
            vertical.add(rightAlign);
            vertical.add(Box.createVerticalStrut(15));
        
            messagePanel.setLayout(new BorderLayout());
            messagePanel.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(msg);

            text.setText("");
        
            messagePanel.revalidate();
            messagePanel.repaint();
            autoScroll();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage(String msg) {
        if(msg.trim().length() == 0) {
            return;
        }
    
        JLabel msgLabel = new JLabel("<html><p style='width: 150px;'>" + msg + "</p></html>");
        msgLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        msgLabel.setBackground(new Color(220, 248, 198));
        msgLabel.setOpaque(true);
        msgLabel.setBorder(new EmptyBorder(5, 5, 5, 7));
        msgLabel.setForeground(Color.BLACK);
    
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        JLabel time = new JLabel();
        time.setText(sdf.format(calendar.getTime()).toUpperCase());
        time.setFont(new Font("Roboto", Font.PLAIN, 12));
        time.setBackground(new Color(220, 248, 198));
        time.setOpaque(true);
        time.setBorder(new EmptyBorder(5, 5, 5, 7));
        time.setForeground(Color.BLACK);
    
        JPanel message = new JPanel();
        message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
        message.setBackground(new Color(220, 248, 198));
        message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        message.add(msgLabel);
        message.add(time);
    
        JPanel leftAlign = new JPanel(new BorderLayout());
        leftAlign.add(message, BorderLayout.LINE_START);
        leftAlign.setBackground(new Color(236, 229, 221));
    
        vertical.add(leftAlign);
        vertical.add(Box.createVerticalStrut(15));
    
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(vertical, BorderLayout.PAGE_START);
    
        messagePanel.revalidate();
        messagePanel.repaint();
        autoScroll();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        sendMessage();
        return;
    }

    public static void main(String args[]) {
        Server serverUI = new Server();

        try {
            ServerSocket skt = new ServerSocket(8080);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true) {
                    String msg = din.readUTF();
                    serverUI.receiveMessage(msg);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

