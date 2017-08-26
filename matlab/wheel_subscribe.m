
% In this Tops
%Change the numer of strings you would connect to. Add or Remove
%connections if you need different one
%You can see now the subscribed Topics in the GUI by selecting the Log tab
%You can select the Topics and see what data you get from the sensors

subscribes = ["/iot-lab/wheel/sensehat","2";
              "iot_lab/wheel/sensortag","2"];
wheelGUI.Subscribe(subscribes(1,:));
wheelGUI.Subscribe(subscribes(2,:));
numberOfSubscribes=numel(subscribes)/2;
