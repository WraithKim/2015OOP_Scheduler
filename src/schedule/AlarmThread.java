package schedule;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.AlarmQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Donghwan on 12/1/2015.
 *
 * 알람을 검사하는 쓰레드
 */
public class AlarmThread extends Thread implements AutoCloseable{
    private MediaPlayer alarmSound;

    private static AlarmThread ourInstance = new AlarmThread();

    public static AlarmThread getInstance(){
        return ourInstance;
    }

    private boolean checkAlarm(){
        if(AlarmQueue.getInstance().isEmpty()) {
            return false;
        }else{
            if(AlarmQueue.getInstance().peek().getAlarmTime() <= System.currentTimeMillis()){
                Schedule top = AlarmQueue.getInstance().poll();
                return true;
            }else{
                return false;
            }
        }
    }

    private AlarmThread(){
        alarmSound = new MediaPlayer(new Media(
                new File("." + File.separator + "res" + File.separator + "DingDong.mp3").toURI().toString()));
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                if(checkAlarm()){
                    // 알람이 울리면 해야 될 일 정의
                    alarmSound.play();
                    alarmSound.seek(alarmSound.getStartTime());
                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if(!(this.isInterrupted())) this.interrupt();
        if(alarmSound != null) {
            alarmSound.stop();
        }

    }
}