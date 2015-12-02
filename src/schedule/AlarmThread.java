package schedule;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Donghwan on 12/1/2015.
 *
 * 알람을 검사하는 쓰레드
 */
public class AlarmThread extends Thread implements AutoCloseable{
    private PriorityBlockingQueue<Schedule.Alarm> alarmQueue;
    private MediaPlayer alarmSound;


    private boolean checkAlarm(){
        if(alarmQueue.isEmpty()) {
            return false;
        }else{
            if(!(alarmQueue.peek().isEnabled())){
                alarmQueue.poll();
                return false;
            }else if(alarmQueue.peek().getAlarmTime() <= System.currentTimeMillis()){
                Schedule.Alarm top = alarmQueue.poll();
                top.setDisabled();
                return true;
            }else{
                return false;
            }
        }
    }

    public AlarmThread(PriorityBlockingQueue<Schedule.Alarm> alarmQueue) throws FileNotFoundException{
        alarmSound = new MediaPlayer(new Media(
                new File("." + File.separator + "res" + File.separator + "DingDong.mp3").toURI().toString()));
        this.alarmQueue = alarmQueue;
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