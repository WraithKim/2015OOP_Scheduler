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
public class AlarmThread extends Thread{
    private final MediaPlayer alarmSound;

    private static final AlarmThread ourInstance = new AlarmThread();

    public static AlarmThread getInstance(){
        return ourInstance;
    }

    private AlarmThread(){
        alarmSound = new MediaPlayer(new Media(
                new File("res" + File.separator + "DingDong.mp3").toURI().toString()));
        alarmSound.setOnEndOfMedia(()->{
            alarmSound.seek(alarmSound.getStartTime());
            alarmSound.stop();
        });
    }

    @Override
    public void run() {
        // 퍼포먼스를 위해 함수 호출을 줄였습니다.
        AlarmQueue alarmQueue = AlarmQueue.getInstance();
        FileManager fileManager = FileManager.getInstance();
        Calendar nextDay = GregorianCalendar.getInstance();
        nextDay.set(Calendar.HOUR_OF_DAY, 0);
        nextDay.set(Calendar.MINUTE, 0);
        nextDay.add(Calendar.DAY_OF_MONTH, 1);

        //SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        //System.out.println("next day: "+ dateFormat.format(nextDay.getTime()));


        try {
            while (!this.isInterrupted()) {
                //System.out.println("system time: " + System.currentTimeMillis());

                if(!(alarmQueue.isEmpty())){
                    //System.out.println("current top: "+ alarmQueue.peek().getAlarmTime());
                    if(alarmQueue.peek().getAlarmTime() <= System.currentTimeMillis()) {
                        // TODO 알람이 울리면 해야 될 일 정의
                        /*
                        Schedule top = alarmQueue.poll();
                        System.out.println("alarm ring!!!");
                        System.out.println(top.getName());
                        System.out.println(top.getAlarmTime());
                        System.out.println("============================");
                        */
                        alarmSound.play();
                    }
                }
                if(nextDay.getTimeInMillis() <= System.currentTimeMillis()){
                    // 다음날로 넘어갔을 때, 해야할 일
                    //System.out.println("next day update: "+System.currentTimeMillis());
                    nextDay.add(Calendar.DAY_OF_MONTH, 1);
                    Calendar oneWeekLater = new GregorianCalendar(nextDay.get(Calendar.YEAR), nextDay.get(Calendar.MONTH), nextDay.get(Calendar.DAY_OF_MONTH));
                    oneWeekLater.add(Calendar.DAY_OF_MONTH, 7);
                    try{
                        alarmQueue.addAll(fileManager.readScheduleFile(oneWeekLater));
                    }catch(IOException ioe){
                        // nothing to do
                    }catch(ClassNotFoundException cnfe){
                        System.err.println("Data has corrupted in Data directory\n" +
                                "Maybe your Scheduler version doesn't match with Schedule files.");
                        System.exit(1);
                        return;
                    }
                    //System.out.println("updated: "+System.currentTimeMillis());
                    //System.out.println("next day: "+ dateFormat.format(nextDay.getTime()));
                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException ie){
            System.err.println("Thread suddenly interrupted");
        }
    }
}