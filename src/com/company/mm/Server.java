package com.company.mm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.net.*;
import java.util.Date;

public class Server implements ActionListener {

    //
    JButton button_send;
    //
    JTextField text;
    //
    JPanel panel_area1;
    //
    static Box vertical = Box.createVerticalBox();
    //
    static JFrame frame = new JFrame();
    //
    static DataOutputStream dOut;

    //Constructor
    Server()
    {
        //
        frame.setLayout(null);

        //
        int frameWidth = 450;
        int frameHeight = 700;


        ///
        JPanel panel = new JPanel();
        panel.setBackground(new Color(7,94,84));
        panel.setBounds(0,0,frameWidth, 70);
        panel.setLayout(null);
        frame.add(panel);

        ///
        ImageIcon imageIcon_back = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
        Image i1 = imageIcon_back.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_backScaled = new ImageIcon(i1);
        JLabel image_back = new JLabel(imageIcon_backScaled);
        image_back.setBounds(5,20,25,25);
        image_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                super.mouseClicked(ae);
                //
                System.exit(0);
            }
        });
        panel.add(image_back);



        ///
        ImageIcon imageIcon_profile1 = new ImageIcon(ClassLoader.getSystemResource("icon/1.png"));
        Image i2 = imageIcon_profile1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_profile1Scaled = new ImageIcon(i2);
        JLabel image_profile1 = new JLabel(imageIcon_profile1Scaled);
        image_profile1.setBounds(40,10,50,50);
        panel.add(image_profile1);


        ///
        ImageIcon imageIcon_video = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));
        Image i3 = imageIcon_video.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_videoScaled = new ImageIcon(i3);
        JLabel image_video = new JLabel(imageIcon_videoScaled);
        image_video.setBounds(300,20,30,30);
        panel.add(image_video);


        ///
        ImageIcon imageIcon_phone = new ImageIcon(ClassLoader.getSystemResource("icon/phone.png"));
        Image i4 = imageIcon_phone.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_phoneScaled = new ImageIcon(i4);
        JLabel image_phone = new JLabel(imageIcon_phoneScaled);
        image_phone.setBounds(360,20,35,30);
        panel.add(image_phone);


        ///
        ImageIcon imageIcon_more = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
        Image i5 = imageIcon_more.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_moreScaled = new ImageIcon(i5);
        JLabel image_more = new JLabel(imageIcon_moreScaled);
        image_more.setBounds(410,20,10,25);
        panel.add(image_more);


        ///
        JLabel text_name = new JLabel("Gaitonde");
        text_name.setForeground(Color.WHITE);
        text_name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        text_name.setBounds(110, 15, 100, 18);
        panel.add(text_name);

        ///
        JLabel text_status = new JLabel("Active Now");
        text_status.setForeground(Color.WHITE);
        text_status.setFont(new Font("SAN SERIF", Font.BOLD, 12));
        text_status.setBounds(110, 35, 100, 18);
        panel.add(text_status);


        ///
        panel_area1 = new JPanel();
        panel_area1.setBounds(5, 75, 440, 570);
        frame.add(panel_area1);


        ///
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        frame.add(text);


        ///
        button_send = new JButton("Send");
        button_send.setBounds(320, 655, 123, 40);
        button_send.setBackground(new Color(7, 94,84));
        button_send.setForeground(Color.WHITE);
        button_send.addActionListener(this);
        button_send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        frame.add(button_send);


        //
        frame.setSize(frameWidth, frameHeight);
        //
        frame.setLocation(200, 50);
        //
        frame.setUndecorated(true);
        //
        frame.getContentPane().setBackground(Color.WHITE);
        //
        frame.setVisible(true);
    }

    //
    public void actionPerformed(ActionEvent ae)
    {
        //
        if (ae.getSource() == button_send)
        {
            try {
                String msg = text.getText();
                //
                JPanel panel2 = formatLabel(msg);

                panel_area1.setLayout(new BorderLayout());

                //
                JPanel panel_right = new JPanel(new BorderLayout());
                panel_right.add(panel2, BorderLayout.LINE_END);
                vertical.add(panel_right);
                vertical.add(Box.createVerticalStrut(15));

                //
                panel_area1.add(vertical, BorderLayout.PAGE_START);

                //
                dOut.writeUTF(msg);

                //
                text.setText("");

                //
                frame.repaint();
                frame.invalidate();
                frame.validate();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }

    ///
    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //
        JLabel output = new JLabel("<html><p style=\"width :150px \">"+out+"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        //Calendar cal =
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(new Date());
        //
        JLabel text_time = new JLabel();
        text_time.setText(time);
        panel.add(text_time);

        //
        return panel;
    }

    public static void main(String[] args) {
        new Server();

        //
        try
        {
            //
            ServerSocket skt = new ServerSocket(6001);
            //
            while (true)
            {
                //
                Socket s = skt.accept();

                //For receiving msg
                DataInputStream din = new DataInputStream(s.getInputStream());
                //for sending msg
                dOut = new DataOutputStream(s.getOutputStream());

                //
                while (true)
                {
                    //read msg
                    String msg = din.readUTF();

                    //
                    JPanel panel = formatLabel(msg);

                    //
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
