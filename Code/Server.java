import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.*;
import java.net.*;

public class Server implements ActionListener {

    JTextField EText;
    JPanel t1;
    static Box vertical= Box.createVerticalBox();
    static JFrame f= new JFrame();
    static DataOutputStream dout;
    Server(){
        JPanel p= new JPanel();
        p.setBackground(new Color(7, 94,84));
        p.setBounds(0,0,450,70);
        p.setLayout(null);
        f.add(p);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/3.png"));
        Image i2= i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel arrowImage= new JLabel(i3);
        arrowImage.setBounds(5,20,25,25);
        p.add(arrowImage);

        arrowImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.setVisible(false);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/1.png"));
        Image i5= i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel profilePic= new JLabel(i6);
        profilePic.setBounds(40,10,50,50);
        p.add(profilePic);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/video.png"));
        Image i8= i7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel videoImage= new JLabel(i9);
        videoImage.setBounds(300,20,30,30);
        p.add(videoImage);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/phone.png"));
        Image i11= i10.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel phoneImage= new JLabel(i12);
        phoneImage.setBounds(360,20,35,30);
        p.add(phoneImage);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/3icon.png"));
        Image i14= i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        JLabel menuIcon= new JLabel(i15);
        menuIcon.setBounds(420,20,10,25);
        p.add(menuIcon);

        JLabel name= new JLabel("Rafay");
        name.setBounds(110,15,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("TIMES_NEW_ROMAN",Font.BOLD,20));
        p.add(name);

        JLabel ActiveStatus= new JLabel("Active Now");
        ActiveStatus.setBounds(110,35,100,24);
        ActiveStatus.setForeground(Color.WHITE);
        ActiveStatus.setFont(new Font("CALIBRI",Font.BOLD,14));
        p.add(ActiveStatus);

        t1= new JPanel();
        t1.setBounds(5,75,440,570);
        f.add(t1);

        EText= new JTextField();
        EText.setBounds(5,655,310,40);
        EText.setFont(new Font("CALIBRI",Font.PLAIN,16));
        f.add(EText);

        JButton EnterBtn= new JButton("Send");
        EnterBtn.setBounds(320,655,123,40);
        EnterBtn.setBackground(new Color(7,94,84));
        EnterBtn.setForeground(Color.WHITE);
        EnterBtn.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        EnterBtn.addActionListener(this);
        f.add(EnterBtn);



        f.setSize(450, 700);
        f.setUndecorated(true);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);


        f.setLayout(null);
        f.setVisible(true);


    }
    public void actionPerformed(ActionEvent ae)
    {
        try {
            String msgOutput = EText.getText();
            t1.setLayout(new BorderLayout());

            JLabel output = new JLabel(msgOutput);

            JPanel p1 = formatLabel(msgOutput);

            JPanel rightSide = new JPanel(new BorderLayout());
            rightSide.add(p1, BorderLayout.LINE_END);
            vertical.add(rightSide);
            vertical.add(Box.createVerticalStrut(15));

            t1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(msgOutput);

            EText.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String msgOutput){
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output= new JLabel(msgOutput);
        output.setFont(new Font("TAHOMA", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");

        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;

    }

    public static void main(String[] args) {

        Server s = new Server();

        try {
            ServerSocket skt= new ServerSocket(6001);
            while(true){
               Socket S= skt.accept();
               DataInputStream din= new DataInputStream(S.getInputStream());
               dout= new DataOutputStream(S.getOutputStream());

                while(true){
                    String msg= din.readUTF();
                    JPanel panel= formatLabel(msg);

                    JPanel left= new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();


                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
