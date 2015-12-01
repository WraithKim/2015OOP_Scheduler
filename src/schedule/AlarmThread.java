package schedule;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;

/**
 * Created by Donghwan on 12/1/2015.
 *
 * 알람을 검사하는 쓰레드
 */
public class AlarmThread extends Thread implements AutoCloseable{
    private PriorityQueue<Schedule> alarmQueue;
    private Player alarmSound;


    private boolean checkAlarm(){
        return true;
    }

    public AlarmThread() throws FileNotFoundException, JavaLayerException{
        alarmSound = new Player(new FileInputStream("." + File.separator + "res" + File.separator + "DingDong.mp3"));
    }

    public void setAlarmQueue(PriorityQueue<Schedule> alarmQueue) {
        this.alarmQueue = alarmQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if(checkAlarm()){
                    // 알람이 울리면 해야 될 일 정의
                    alarmSound.play();

                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException ie) {

        }catch(JavaLayerException jle){

        }
    }

    @Override
    public void close() throws Exception {
        if(!(this.isInterrupted())) this.interrupt();
        if(alarmSound != null) {
            alarmSound.close();
            alarmSound = null;
        }

    }
}