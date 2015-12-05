package schedule;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.AlarmQueue;
import util.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

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



    private AlarmThread(){
        alarmSound = new MediaPlayer(new Media(
                new File("res" + File.separator + "DingDong.mp3").toURI().toString()));
        alarmSound.setOnEndOfMedia(()->alarmSound.seek(alarmSound.getStartTime()));
    }

    @Override
    public void run() {
        // 퍼포먼스를 위해 함수 호출을 줄였습니다.
        Calendar nextDay = GregorianCalendar.getInstance();
        nextDay.set(Calendar.HOUR_OF_DAY, 0);
        nextDay.set(Calendar.MINUTE, 0);
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        try {
            while (!this.isInterrupted()) {
                if(!(alarmQueue.isEmpty()) &&
                        alarmQueue.peek().getAlarmTime() <= System.currentTimeMillis()
                        ){
                    // 알람이 울리면 해야 될 일 정의
                    Schedule top = alarmQueue.poll();
                    alarmSound.play();
                }
                if(nextDay.getTimeInMillis() <= System.currentTimeMillis()){
                    nextDay.add(Calendar.DAY_OF_MONTH, 1);
                    Calendar oneWeekLater = new GregorianCalendar(nextDay.get(Calendar.YEAR), nextDay.get(Calendar.MONTH), nextDay.get(Calendar.DAY_OF_MONTH));
                    oneWeekLater.add(Calendar.DAY_OF_MONTH, 7);
                    try{
                        alarmQueue.addAll(FileManager.getInstance().readScheduleFile(oneWeekLater.get(Calendar.YEAR), oneWeekLater.get(Calendar.MONTH) + 1, oneWeekLater.get(Calendar.DAY_OF_MONTH) + 1));
                    }catch(IOException ioe){
                        // nothing to do
                    }catch(ClassNotFoundException cnfe){
                        System.err.println("Data has corrupted in Data directory");
                        System.exit(1);
                        return;
                    }
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