  %This is the Startup Script
  %It starts the GUI to connect to the Server.
  %javaaddpath must point to the java file, for the GUI connection.
  %Select the Server and Configuration. Press the Connect button.
  %Don't close the GUI, it close Matlab, too
  %After Connection, go forward to the Subscribe Script

   clear
   clear java;
   javaaddpath('C:\IoT\wheel_code_V1.4\java\dist\testmatlab5_1_1_2.jar');
   wheelGUI = Matlab_MQTT_Client_Main_Class;
   javaMethod('main',wheelGUI,'');