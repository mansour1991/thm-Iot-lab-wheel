
xvec=[];
yvec=[];
zvec=[];
figure('Name','Sensor Data','NumberTitle','off');
line_wid              = 2;
FontSize              = 15;
marker_size           = 10;
buffer_length         = 100;
x = 0;
y = 0;
z = 0;
x2 = 0;
y2 = 0;
z2 = 0;
%%%% the follwing examples demonstrats all the possible Methods and Fields
%%%% in java code that you could use in matlab:
%%% t.get_message_(argument) // recieving a MQTT message .the argument is your
%%% MQTT topic.


while true


    coordmat       = zeros(buffer_length,3);
    coordmat2       = zeros(buffer_length,3);
    for buff_index = 1:buffer_length
        coord_vec      = [];
        coord_vec2      = [];
        pause (0.05);

        if wheelGUI.get_message_(subscribes(2,:))~=0
           
            m = wheelGUI.get_message_(subscribes(2,:));
            e = reshape(m,1,[]);
            a = native2unicode(e);
            c = strsplit(a,' ');
            ts=strrep(c(2),';','');
            ts=str2double(x);
            if ts > 2
                x=ts/5-7.5;
                x=x*-1;
                y=strrep(c(4),';','');
                y=str2double(y)/5-5;
                z=strrep(c(6),';','');
                z=str2double(z)/5-7.5;
            end
        else
            
            x=0;
            y=0;
            z=0;
        end
        x              = round(x,1, 'decimals');
        y              = round(y,1, 'decimals');
        z              = round(z,1, 'decimals');
       
        coord_vec      = [x,y,z];  
        [theta,rho,z]  = cart2pol(x,y,z);

        %%%% coordmat is a matrix representing the buffer where the data is stored
        coordmat(buff_index,1:3) = coord_vec;   
        subplot(2,2,1)
        %plot3(coordmat(:,1),coordmat(:,2),coordmat(:,3),'r','markersize',marker_size,'marker','x','linewidth',line_wid);   
        plot3(coordmat(:,1),coordmat(:,2),coordmat(:,3),'r','linewidth',line_wid);
        grid on;
        xlim([-15 15]);ylim([-15 15]);zlim([-15 15]);
        Xlab= xlabel('x'); Ylab=ylabel('y');Zlab=zlabel('z');
        set(Xlab, 'FontSize', FontSize);
        set(Ylab, 'FontSize', FontSize);
        set(Zlab, 'FontSize', FontSize);
        title("sensortag")
        %%%%%
        subplot(2,2,3)
        plot(coordmat(:,1),coordmat(:,2),'b','markersize',marker_size,'marker','o','linewidth',line_wid);
        grid on;    
        xlim([-15 15]);ylim([-15 15]);
        Xlab= xlabel('x'); Ylab=ylabel('y');
        set(Xlab, 'FontSize', FontSize);
        set(Ylab, 'FontSize', FontSize);
        
        %disp(' ');
        disp(['(x,y,z) = (',num2str(x),' ,',num2str(y),' ,',num2str(z),' )','(theta,rho) = (',num2str(radtodeg(theta)),' ,',num2str(rho),' )']);        
        %disp('....................................................................................................................');
        %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            
        %%for sensehat
        if wheelGUI.get_message_(subscribes(1,:))~=0
            n= wheelGUI.get_message_(subscribes(1,:));
        
            e2 = reshape(n,1,[]);
            v2 = native2unicode(e2);
            r2=strrep(v2,':','');
            c2 = strsplit(r2,'"');
            if strcmp(c2(2),'acceleration')
                x2=str2double(c2(5))*10;
                y2=str2double(c2(7))*10;
                z2=c2(9);
                z2 = strrep(z2,'},','');
                z2=str2double(z2)*10;
            else
                x=0;
                y=0;
                z=0;
            end
        end
        x2           = round(x2,1, 'decimals');
        y2           = round(y2,1, 'decimals');
        z2           = round(z2,1, 'decimals');    
        coord_vec2      = [x2,y2,z2];  
        [theta2,rho2,z2]  = cart2pol(x2,y2,z2);       
        coordmat2(buff_index,1:3) = coord_vec2;
        
        subplot(2,2,2)
        plot3(coordmat2(:,1),coordmat2(:,2),coordmat2(:,3),'r','linewidth',line_wid);
        grid on;
        xlim([-15 15]);ylim([-15 15]);zlim([-15 15]);
        Xlab= xlabel('x'); Ylab=ylabel('y');Zlab=zlabel('z');
        set(Xlab, 'FontSize', FontSize);
        set(Ylab, 'FontSize', FontSize);
        set(Zlab, 'FontSize', FontSize);    
        title("sensehat")
        subplot(2,2,4)
        plot(coordmat2(:,1),coordmat2(:,2),'b','markersize',marker_size,'marker','o','linewidth',line_wid);
        grid on;    
        xlim([-15 15]);ylim([-15 15]);
        Xlab= xlabel('x'); Ylab=ylabel('y');
        set(Xlab, 'FontSize', FontSize);
        set(Ylab, 'FontSize', FontSize);
        %disp(' ');
        %disp(['(x2,y2,z2) = (',num2str(x2),' ,',num2str(y2),' ,',num2str(z2),' )','(theta,rho) = (',num2str(radtodeg(theta2)),' ,',num2str(rho2),' )']);        
        %disp('....................................................................................................................');
        
    end        
end
%end

