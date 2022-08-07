package P2G13;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
//import java.lang.constant.Constable;
import java.awt.GridBagLayout;
import java.awt.*;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
/*
Class for creating GUI 
*/

public class SimulatorGUI extends JFrame{
    //Swing elements
    private JButton btnStart;
    private JPanel curvePanel;
    private JTextArea txtTimer;
    private JTextArea txtLatitude;
    private JTextArea txtLongitude;
    private JTextArea txtYawrate;
    private JTextArea txtVehicleSpeed;
    private JTextArea txtWheelAngle;
    private JTextArea txtLongitudinalAcceleration;
    private JTextArea txtLatitudinalAcceleration;
    private JLabel lblTimer;
    private JLabel lblLatitudeJLabel;
    private JLabel lblLongitude;
    private JLabel lblYawRate;
    private JLabel lblVehicleSpeed;
    private JLabel lblWheelAngle;
    private JLabel  lblLongitudinalAceleration;
    private JLabel lblLatitudinalAcceleration;
    private JButton btnStop;
    private JTextField txtSegmentType;
    private JLabel lblAverageVelocity;
    private JTextField txtAverageVelocity;
    private JLabel lblSegmentType;
    SensorDataReceiver receiver;
    private JScrollBar scroll;
    private JLabel lblGPSStart;
    private JLabel lblGPSEnd;
    private JLabel lblMaxVehicleSpeed;
    private JLabel lblMinVehicleSpeed;
    private JLabel lblCurveDirection;
    private JLabel lblLengthOfSegment;
    private JLabel lblMaxLongitudinalAcceleration;
    private JLabel lblMinLongitudinallAcceleration;
    private JLabel lblMaxLateralAcceleration;
    private JLabel lblMinLateralAcceleration;

    private JTextField txtStartLatitude;
    private JTextField txtStartLongitude;
    private JTextField txtMaxVehicleSpeed;
    private JTextField txtMinVehicleSpeed;
    private JTextField txtCurveDirection;
    private JTextField txtEndLatitude;
    private JTextField txtEndLongitude;
    private JTextField txtLengthOfSegment;
    private JTextField txtMaxLongitudinalAcceleration;
    private JTextField txtMinLongitudinallAcceleration;
    private JTextField txtMaxLateralAcceleration;
    private JTextField txtMinLateralAcceleration;
    private JTextArea txtSegmentData;
    private JTextArea txtCurveDetails;

    private JScrollPane scrollSegmentArea;




    //Constructor
    public SimulatorGUI()
    {
        txtSegmentData = new JTextArea();
        txtSegmentData.setBounds(350, 270, 500, 300);
        // add(txtSegmentData);
        scrollSegmentArea = new JScrollPane(txtSegmentData);
        scrollSegmentArea.setBounds(350, 270, 500, 300);
        getContentPane().add(scrollSegmentArea);
        // add(scrollSegmentArea);

        txtCurveDetails = new JTextArea();
        txtCurveDetails.setBounds(640, 50, 200, 50);
        add(txtCurveDetails);


        scroll = new JScrollBar();
        curvePanel = new JPanel(new GridBagLayout());
        txtSegmentType = new JTextField(20);
        lblSegmentType = new JLabel("Segment Type");

        lblAverageVelocity = new JLabel("Average velocity");
        txtAverageVelocity = new JTextField(20);

        lblGPSStart = new JLabel("GPS Start");
        lblGPSEnd = new JLabel("GPS End");
        txtStartLatitude = new JTextField(20);
        txtStartLongitude = new JTextField(20);

        txtEndLatitude = new JTextField(20);
        txtEndLongitude = new JTextField(20);

        lblLengthOfSegment = new JLabel("Length of segment");
        txtLengthOfSegment = new JTextField(20);

        lblMaxLongitudinalAcceleration = new JLabel("Max long acc.");
        txtMaxLongitudinalAcceleration = new JTextField(20);

        lblMaxLateralAcceleration = new JLabel("max lateral acc.");
        txtMaxLateralAcceleration = new JTextField(20);





        lblMaxVehicleSpeed = new JLabel("Max vehicle speed");
        txtMaxVehicleSpeed = new JTextField(20);

        lblMinVehicleSpeed = new JLabel("Min vehicle speed");
        txtMinVehicleSpeed = new JTextField(20);

        lblCurveDirection = new JLabel("Curve direction");
        txtCurveDirection = new JTextField(20);

        
        receiver = new SensorDataReceiver(this);
        txtLatitude = new JTextArea();
        txtLongitude = new JTextArea();
        txtVehicleSpeed = new JTextArea();
        txtWheelAngle = new JTextArea();
        txtYawrate = new JTextArea();
        txtTimer = new JTextArea();
        txtLatitudinalAcceleration = new JTextArea();
        txtLongitudinalAcceleration = new JTextArea();
        lblLatitudeJLabel = new JLabel();
        lblLatitudeJLabel.setText("Latitude");
        lblLatitudinalAcceleration = new JLabel();
        lblLatitudinalAcceleration.setText("Latitudinal acceleration");
        lblLongitude = new JLabel();
        lblLongitude.setText("Longitude");
        lblTimer = new JLabel();
        lblTimer.setText("Timer(ms)");
        lblVehicleSpeed = new JLabel();
        lblVehicleSpeed.setText("Vehicle Speed");
        lblWheelAngle = new JLabel();
        lblWheelAngle.setText("Wheel angle");
        lblYawRate = new JLabel();
        lblYawRate.setText("Yaw Rate");
        lblLongitudinalAceleration = new JLabel();
        lblLongitudinalAceleration.setText("Longitudinal acceleration");
        
        btnStart = new JButton("Start");
        btnStart.setBounds(50,50,100, 40);
        btnStop = new JButton("Stop");
        btnStop.setBounds(530,50,100, 40);
        lblLatitudeJLabel.setBounds(150, 370, 100, 40);
        txtLatitude.setBounds(150, 400, 190, 30);
        lblLongitude.setBounds(150, 300, 100, 40);
        txtLongitude.setBounds(150, 330, 190, 30);
        lblVehicleSpeed.setBounds(50, 170, 100, 40);
        txtVehicleSpeed.setBounds(150, 175, 190, 30);
        lblYawRate.setBounds(50, 250, 130, 40);
        txtYawrate.setBounds(150, 255, 190, 30);
        lblLatitudinalAcceleration.setBounds(350, 170, 160, 40);
        txtLatitudinalAcceleration.setBounds(510, 175, 190, 30);
        lblLongitudinalAceleration.setBounds(350, 210, 160, 40);
        txtLongitudinalAcceleration.setBounds(510, 215, 190, 30);
        curvePanel.setBounds(350, 270, 500, 300);
        
        txtTimer.setBounds(300, 115, 165, 30);
        lblTimer.setBounds(150, 110, 100, 40);

        txtWheelAngle.setBounds(150, 215, 190, 30);
        lblWheelAngle.setBounds(50, 210, 100, 40);
        add(lblLatitudeJLabel);
        add(lblLongitude);
        add(scroll);
        add(btnStart);
        add(btnStop);
        add(lblTimer);
        add(txtTimer);
        add(txtLatitude);
        add(txtLongitude);
        add(txtVehicleSpeed);
        add(txtYawrate);
        add(lblVehicleSpeed);
        add(lblLatitudeJLabel);
        add(lblLatitudinalAcceleration);
        add(lblLongitudinalAceleration);
        add(txtLatitudinalAcceleration);
        add(txtLongitudinalAcceleration);
        add(lblYawRate);
        add(txtWheelAngle);
        add(lblWheelAngle);
        
        // curvePanel.add(lblSegmentType, constraints);
        // curvePanel.add(txtSegmentType, constraints1);

        //border for jpanel
        Border black = BorderFactory.createLineBorder(Color.black);
        curvePanel.setBorder(black);
        // add(curvePanel);

        txtTimer.setEditable(false);
        txtLatitude.setEditable(false);
        txtLongitude.setEditable(false);
        txtLatitudinalAcceleration.setEditable(false);
        txtLongitudinalAcceleration.setEditable(false);
        txtVehicleSpeed.setEditable(false);
        txtWheelAngle.setEditable(false);
        txtYawrate.setEditable(false);

        setSize(1000, 1000);
        setLayout(null);
        setVisible(true);
        // txtSegmentData.setEditable(false);
        startListeners();
        startListener1();
    }

    public void setSegmentData(String s)
    {
        txtSegmentData.update(txtSegmentData.getGraphics());
        txtSegmentData.append(s);
        txtSegmentData.append("\n");
    }

    public void resetSegmentData(String s)
    {
        txtSegmentData.update(txtSegmentData.getGraphics());
        txtSegmentData.setText(s);
    }

    public void setCurveDetails(String s)
    {
        txtCurveDetails.update(txtCurveDetails.getGraphics());
        txtCurveDetails.setText(s);
    }

    //Reset all fields
    public void resetFields()
    {
        SwingWorker sw3 = new SwingWorker() 
        {
            @Override
            protected String doInBackground() throws Exception 
            {
                String s = "-";
                try {
                    setTime(s);
                    setLateralAcceleration(s);
                    setLatitude(s);
                    setLongitude(s);
                    setLongitudinalAcceleration(s);
                    setVehicleSpeed(s);
                    setYawRate(s);
                    setWheelAngle(s);
                    resetSegmentData("");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
               return null;
            }
        };
        sw3.execute(); 
    }

    //Event listeners for stop button 
    protected void startListener1() {
        Thread p = new Thread()
        {
            public void run()
            {
                btnStop.addActionListener(new Action() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                                startWorker2();
                                resetFields();
                    }
        
                    @Override
                    public Object getValue(String key) {
                        
                        return null;
                    }
        
                    @Override
                    public void putValue(String key, Object value) {
                        
                        
                    }
        
                    @Override
                    public void setEnabled(boolean b) {
                        
                        
                    }
        
                    @Override
                    public boolean isEnabled() {
                        
                        return false;
                    }
        
                    @Override
                    public void addPropertyChangeListener(PropertyChangeListener listener) {
                        
                    }
        
                    @Override
                    public void removePropertyChangeListener(PropertyChangeListener listener) {
                        
                    }
                    
                });
            }
        };
        p.start();
        
    }

    //All the below methods set values of textfields 
    public void setTime(String s) throws InterruptedException
    {
        txtTimer.update(txtTimer.getGraphics());
        txtTimer.setText(s);
        
    }
    public void setYawRate(String s) throws InterruptedException
    {
        txtYawrate.update(txtYawrate.getGraphics());
        txtYawrate.setText(s);
    }

    public void setVehicleSpeed(String s) throws InterruptedException
    {
        txtVehicleSpeed.update(txtVehicleSpeed.getGraphics());
        txtVehicleSpeed.setText(s);
        
    }

    public void setLatitude(String s) throws InterruptedException
    {
        txtLatitude.update(txtLatitude.getGraphics());
        txtLatitude.setText(s);
    }

    public void setLongitude(String s) throws InterruptedException
    {
        txtLongitude.update(txtLongitude.getGraphics());
        txtLongitude.setText(s);
    }
    public void setLateralAcceleration(String s) throws InterruptedException
    {
        txtLatitudinalAcceleration.update(txtLatitudinalAcceleration.getGraphics());
        txtLatitudinalAcceleration.setText(s);
    }

    public void setLongitudinalAcceleration(String s) throws InterruptedException
    {
        txtLongitudinalAcceleration.update(txtLongitudinalAcceleration.getGraphics());
        txtLongitudinalAcceleration.setText(s);
    }

    public void setWheelAngle(String s) throws InterruptedException
    {
        txtWheelAngle.update(txtWheelAngle.getGraphics());
        txtWheelAngle.setText(s);
    }

    public void setSegmentType(String s)
    {
        txtSegmentType.update(txtSegmentType.getGraphics());
        txtSegmentType.setText(s);
    }

    //Listener for start button
    public void startListeners()
    {
        SimulatorGUI ref = this;
        btnStart.addActionListener(new Action() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startWorker();
            }

            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {
            }

            @Override
            public void setEnabled(boolean b) {
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {
            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {
            }
        });
    }

    //method for starting Swing worker object. Swing worker creates a new thread for execution of tasks
    public void startWorker()
    {
        SwingWorker sw1 = new SwingWorker() 
        {
            @Override
            protected String doInBackground() throws Exception 
            {
                try 
                {
                    
                    receiver.startSimulationButtonPressed();
                    
                    
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
               return null;
            }
        };
        sw1.execute(); 
    }

    //worker object for stop simulation. SwingWorker objects 
    // are used to create seperate threads for execution 
    public void startWorker2()
    {
        SwingWorker sw2 = new SwingWorker() 
        {
            @Override
            protected String doInBackground() throws Exception 
            {
                try {
                    receiver.stopSimulation();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
               return null;
            }
        };
        sw2.execute(); 
    }
}
