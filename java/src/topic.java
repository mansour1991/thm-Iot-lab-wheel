
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mahmoud Mansour
 */
public class topic {
    static final int QOS_ZERO = 0 ;
    static final int QOS_ONE = 1 ;
    static final int QOS_TWO = 2;
    static final int SUBSCRIBE = 0;
    static final int PUBLISH = 1;
    
    private double message_number ;
    private Timestamp message_time_stamp  ;
    private int QOS ;
    private String topic ;
    private int topic_type;
    private byte[] message = {0b0};
    topic (String topic, int QOS,int topic_type)
    {
        this.QOS=QOS;
        this.topic=topic;
        this.topic_type=topic_type;
        message_number = 0;
        message_time_stamp = new Timestamp (System.currentTimeMillis());
        
    }
    
    public void set_topic (String topic)
    {
        this.topic=topic;
    }
    public String get_topic ()
    {
        return topic;
    }
    public void set_QOS (int QOS)
    {
        switch(QOS){
            case QOS_ZERO :
                this.QOS=QOS_ZERO;
                break;
            case QOS_ONE :
                this.QOS=QOS_ONE;
                break;
            case QOS_TWO:
                this.QOS=QOS_TWO;
                break;
            default :
                this.QOS=QOS_TWO;
                    
                    }
    }
    public int get_QOS ()
    {
        return QOS;
    }
    public int get_topic_type()
    {
        return topic_type;
    }
    public void set_topic_type(int topic_type)
    {
        switch (topic_type)
        {
            case SUBSCRIBE :
                this.topic_type=SUBSCRIBE;
                break;
            case PUBLISH:
                this.topic_type=PUBLISH;
                break;
            default:
                this.topic_type=SUBSCRIBE;
        }
    }
    
    public byte[] get_message ()
    {
        return this.message;
    }
    
    
    
    public void set_message (byte[] message)
    {
        this.message= message;
    }
    
    public void set_message_number (double message_number)
    {
       this.message_number = message_number;
    }
    
    public double get_message_number ()
    {
        return this.message_number;
    }
    
    public void set_message_time_stamp (long time)
    {
        this.message_time_stamp.setTime(time);
    }
    public Timestamp get_message_time_stamp ()
            {
                return this.message_time_stamp;
            }
    
//    @Override
//    public String toString ()
//    {
//        return ("Topic: "+this.get_topic()+" , QOS: "+this.get_QOS()+" // "+this.get_topic_type() );
//    }
   
    
//    @Override
//    public boolean equals (Object topic)
//    {
//        topic t = (topic) topic;
//        if (this.get_topic().equals(t.get_topic()))
//                return true;
//        else
//            return false;
//    }
}
