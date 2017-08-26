
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mahmoud Mansour
 */
public class Matlab_MQTT_Client_Main_Class {

    public static MqttClient client;
    static String client_id;
    static int Mqtt_version = 4;
    static JFrame frame;
    static JMenuBar MenuBar;
    static JMenu MenuFile;
    static JMenuItem Item1, Item2;
    static JTabbedPane mainpanel;
    static JPanel tab1, tab2;
    static JPanel panel_up;
    static JPanel panel1;
    static JLabel label1_1;
    static JTextField textfield1_1;
    static JLabel label1_2;
    static JTextField textfield1_2;
    static JPanel panel2;
    static JLabel label2;
    static JTextField textfield2;
    static JPanel panel3;
    static JLabel label3;
    static JTextField textfield3;
    static JPanel panel4;
    static JLabel label4;
    static JTextField textfield4;
    static JButton button4;
    static JPanel panel5;
    static JButton button5;
    static JComboBox list5;
    static JLabel label5;
    static JPanel panel6;
    static JLabel label6;
    static JComboBox list6;
    static JTextArea textarea;
    static JScrollPane scroller2;
    static JPanel panel7;
    static JScrollPane scroller;
    static JTextArea textarea_down;
    static ArrayList<topic> list = new ArrayList<topic>();
    static String[] QOS = {Integer.toString(topic.QOS_ZERO), Integer.toString(topic.QOS_ONE), Integer.toString(topic.QOS_TWO)};

    public static void main(String args[]) {
        // trying to get the System look and Feel. depends on the operating system. 
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        //***************************

        // create a frame with size 830 * 500 pixels.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(830, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Matlab MQTT Client V1.0");

        // create a menu Ban and Menus.
        MenuBar = new JMenuBar();
        MenuFile = new JMenu("Menu1");
        Item1 = new JMenuItem("Item1");
        Item2 = new JMenuItem("Item2");
        //Item2.addActionListener(new AboutListener());
        MenuFile.add(Item1);
        MenuFile.add(Item2);
        MenuBar.add(MenuFile);
        frame.setJMenuBar(MenuBar);

        // adding all the components to the main panel
        mainpanel = new JTabbedPane();

        // tab1:
        tab1 = new JPanel();
        tab1.setName("Main");
        tab1.setLayout(new GridLayout(2, 1, 4, 4));
        tab1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel_up = new JPanel();
        panel_up.setLayout(new GridLayout(5, 2, 10, 10));

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        label1_1 = new JLabel("Broker Address: ");
        textfield1_1 = new JTextField("iot.eclipse.org");
        label1_2 = new JLabel("Port Number:    ");
        textfield1_2 = new JTextField("1883");
        panel1.add(label1_1);
        panel1.add(textfield1_1);
        panel1.add(label1_2);
        panel1.add(textfield1_2);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        label2 = new JLabel("Username: ");
        textfield2 = new JTextField("username");
        panel2.add(label2);
        panel2.add(textfield2);

        panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        label3 = new JLabel("Password:  ");
        textfield3 = new JTextField("password");
        panel3.add(label3);
        panel3.add(textfield3);

        panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        label4 = new JLabel("User ID:  ");
        textfield4 = new JTextField(5);
        button4 = new JButton("Generate");
        button4.addActionListener(new generate_id_button_listener());
        panel4.add(label4);
        panel4.add(textfield4);
        panel4.add(button4);

        panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
        button5 = new JButton("Connect");
        button5.addActionListener(new connection_button_listerner());

        list5 = new JComboBox();
        list5.addItem("MQTT_VERSION_3_1_1");
        list5.addItem("MQTT_VERSION_3_1");
        list5.addItem("MQTT_VERSION_DEFAULT");
        list5.addItemListener(new version_listener());
        label5 = new JLabel("Mqtt version: ");
        panel5.add(label5);
        panel5.add(list5);
        panel5.add(button5);

        panel_up.add(panel1);
        panel_up.add(panel2);
        panel_up.add(panel3);
        panel_up.add(panel4);
        panel_up.add(panel5);

        tab1.add(panel_up);
        ///////////////////////////////////////////////////
        textarea_down = new JTextArea();
        textarea_down.setEditable(false);
        textarea_down.setBackground(new Color(240, 240, 240));
        scroller = new JScrollPane(textarea_down);

        tab1.add(scroller);

        //tab2:
        tab2 = new JPanel();
        tab2.setName("Log");
        tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
        tab2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel6 = new JPanel();
        panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));
        label6 = new JLabel("Topics:   ");
        list6 = new JComboBox();
        //list6.addItemListener(new topic_listener());
        panel6.add(label6);
        panel6.add(list6);

        panel7 = new JPanel();
        textarea = new JTextArea(20, 50);
        scroller2 = new JScrollPane(textarea);

        tab2.add(panel6);
        tab2.add(scroller2);

        mainpanel.add(tab1);
        mainpanel.add(tab2);
        frame.setContentPane(mainpanel);
        frame.revalidate();
        

    }

    //this inner class to react when the user press Connect/disconnect button.
    //create an MQTT client and have a connection with the specified broker by the user 
    static class connection_button_listerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // In case the user wants to Connect:
            if (button5.getText().equals("Connect")) {
                client_id = textfield4.getText();
                if (client_id == null) {
                    client_id = MqttClient.generateClientId();
                }
                try {
                    client = new MqttClient("tcp://" + textfield1_1.getText() + ":" + textfield1_2.getText(), client_id, new MemoryPersistence());
                } catch (MqttException ex) {
                    Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
                }
                MqttConnectOptions options = new MqttConnectOptions();
                options.setUserName(textfield2.getText());
                options.setPassword(textfield3.getText().toCharArray());
                options.setMqttVersion(Mqtt_version);
                try {
                    client.connect(options);

                    if (client.isConnected()) {
                        System.out.println("connected");
                        textarea_down.setText("Client_ID:\t" + client_id + "\thas successfuly connected to:\n"
                                + "broker:\t" + "tcp://" + textfield1_1.getText() + ":" + textfield1_2.getText() + "\n"
                                + "with credentials:\tUsername:\t" + options.getUserName() + "\tPassword:\t" + Arrays.toString(options.getPassword()) + "\n"
                                + "Mqtt version:\t" + list5.getSelectedItem());
                    }
                    client.setCallback(new callback());
                } catch (MqttException ex) {
                    Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
                    
                }

                if (client.isConnected()) {
                    button5.setText("Disconnect");
                }

            } // In case the user wants to disconnect:
            else {
                try {
                    client.disconnect();
                    System.out.println("Disconnected");
                    textarea_down.setText("Client_ID:\t" + client_id + "\thas successfuly disconnected from:\n"
                            + "broker:\t" + "tcp://" + textfield1_1.getText() + ":" + textfield1_2.getText() + "\n");
                } catch (MqttException ex) {
                    Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
                }
                button5.setText("Connect");
            }
        }

    }
/////////// when a message has been arrived:
    static class callback implements MqttCallback {

        @Override
        public void connectionLost(Throwable thrwbl) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.ss
        }

        @Override
        public void messageArrived(String string, MqttMessage mm) throws Exception {
            //System.out.println(Arrays.toString(mm.getPayload()));
            double message_number = 0;
            String message = new String();
            Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get_topic().equals(string)) {
                list.get(i).set_message(mm.getPayload());
                time_stamp.setTime(System.currentTimeMillis());
                list.get(i).set_message_time_stamp(System.currentTimeMillis());
                message_number = (list.get(i).get_message_number()) + 1;
                list.get(i).set_message_number(message_number);
                if ((list.get(i).get_topic()).equals(list6.getSelectedItem())) {
                    message = new String( mm.getPayload());
                textarea.append("message Number:\t" + message_number + "time stamp:\t"
                        + list.get(i).get_message_time_stamp() + "\n"
                        + message + "\n");
                }
               

            }

        }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken imdt) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
/////////////if the user pressed ("Generat-ID) button, to get an ID for his Client
    static class generate_id_button_listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String a = MqttClient.generateClientId();
            textfield4.setText(a);
            client_id = a;
        }

    }
    
    //// when the user selects a verion for MQTT Connection

    static class version_listener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem().equals("MQTT_VERSION_3_1_1")) {
                Mqtt_version = 4;
            } else if (e.getItem().equals("MQTT_VERSION_3_1")) {
                Mqtt_version = 3;
            } else {
                Mqtt_version = 0;
            }
        }

    }
///////when the user select different topic to display in the second tab
    static class topic_listener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            textarea.setText("");

        }

    }
//// this Method Used inside Matlab to subscribe for a topic:
    
    /// it works as follow:
    // In Matlab: 
    //1-add the *.jar file to the javapath: 
    //  ex:  javaaddpath('C:\Users\Mahmoud Mansour\Desktop\090720147\testmatlab5_1_1_2.jar');
    //2- Create an array for your topic: u = {'/iot-lab/wheel/sensehat','2'};
    //3- creat a 'Matlab_MQTT_CLient_Class':  t = Matlab_MQTT_Client_Main_Class;
    //4- call the main Method: javaMethod('main',t,'')
    //5- Now: a Client with GUI should appears.
    //5-1 enter all the required fields
    // ex: broker add: iot.eclipse.org , port number: 1883
    //     Usernaem: "username", Password: "password"
    //     User ID: insert one or press generate to generate a uniqe ID.
    //     select the MQTT version.
    //     
    public boolean Subscribe(String[] args) {
        if (client.isConnected()) {
            int qos;
            switch (args[1]) {
                case ("" + 0):
                    qos = 0;
                    break;
                case ("" + 1):
                    qos = 1;
                    break;
                case ("" + 2):
                    qos = 2;
                    break;
                default:
                    qos = 2;
            }
            topic topic = new topic(args[0], qos, 0);

            try {
                client.subscribe(args[0], qos);
                list.add(topic);
                list6.addItem(topic.get_topic());
                for (topic i : list) {
                    System.out.println("you are subscribing to the following topics:\n");
                    System.out.println(i);
                }
            } catch (MqttException ex) {
                Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        } else {
            return false;
        }

    }

    public boolean unsubscribe(String args[]) {
        if (client.isConnected()) {
            if (list.isEmpty()) {
                return false;
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get_topic().equals(args[0]) && list.get(i).get_QOS() == Integer.parseInt(args[1])) {
                        list6.removeItem(list.get(i));
                        list.remove(i);
                        try {
                            client.unsubscribe(args[0]);
                        } catch (MqttException ex) {
                            Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }
        } else {
            return false;
        }
        return true;
    }

    public byte[] get_message_(String args[]) {

        if (list.isEmpty()) {
            System.out.println("you have not subscribed to any topic!! zero byte array has been returned");
            return (new byte[]{0b0});
        } else {
            for (int i = 0; i < list.size(); i++) {
                if ((list.get(i).get_topic().equals(args[0])) && (list.get(i).get_QOS() == Integer.parseInt(args[1]))) {
                    System.out.println(Arrays.toString(list.get(i).get_message()));
                    return list.get(i).get_message();

                }

            }

            System.out.println("else ended here");
            return (new byte[]{0b0});
        }

    }

    //i am returning the time not the time stamp. take care here 
    public long get_message_time_(String args[]) {
        if (list.isEmpty()) {
            return 0;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if ((list.get(i).get_topic().equals(args[0])) && (list.get(i).get_QOS() == Integer.parseInt(args[1]))) {
                    return list.get(i).get_message_time_stamp().getTime();

                }

            }
        }
        return 0;

    }

    public double get_message_number_(String args[]) {
        if (list.isEmpty()) {
            return 0;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if ((list.get(i).get_topic().equals(args[0])) && (list.get(i).get_QOS() == Integer.parseInt(args[1]))) {
                    return list.get(i).get_message_number();

                }

            }
        }
        return 0;

    }

    public boolean publish(String args[]) {
        if (client.isConnected()) {
            int qos;
            switch (args[2]) {
                case ("" + 0):
                    qos = 0;
                    break;
                case ("" + 1):
                    qos = 1;
                    break;
                case ("" + 2):
                    qos = 2;
                    break;
                default:
                    qos = 2;
            }
            //topic topic = new topic(args[0], qos, 1);
            //list.add(topic);
            //list6.addItem(topic);
            try {
                client.publish(args[0], args[1].getBytes(), qos, false);
            } catch (MqttException ex) {
                Logger.getLogger(Matlab_MQTT_Client_Main_Class.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        } else {
            return false;
        }
    }

}
