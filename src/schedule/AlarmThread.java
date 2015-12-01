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

    public AlarmThread() throws FileNotFoundException, JavaLayerException{
        alarmSound = new Player(new FileInputStream("." + File.separator + "res" + File.separator + "DingDong.mp3"));
    }

    public void setAlarmQueue(PriorityQueue<Schedule> alarmQueue) {
        this.alarmQueue = alarmQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {

                this.sleep(1000);

            }
        }catch(InterruptedException ie){

        }
    }

    @Override
    public void close() throws Exception {
        if(this.isAlive()) this.interrupt();
        if(alarmSound != null) {
            alarmSound.close();
            alarmSound = null;
        }

    }
}
