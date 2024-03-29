package util;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.ExceptionDialog;
import schedule.Schedule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Donghwan on 12/1/2015.
 *
 * 알람을 검사하는 쓰레드
 */
public class AlarmThread extends Thread{
    private final MediaPlayer alarmSound;
    private Calendar nextDay;

    private static final AlarmThread ourInstance = new AlarmThread();

    public static AlarmThread getInstance(){
        return ourInstance;
    }

    private AlarmThread(){
        alarmSound = new MediaPlayer(new Media(new File("res" + File.separator + "DingDong.mp3").toURI().toString()));
        alarmSound.setOnEndOfMedia(()->{
            alarmSound.seek(alarmSound.getStartTime());
            alarmSound.stop();
        });
    }

    @Override
    public void run() {
        // 퍼포먼스를 위해 함수 호출을 줄였습니다.
        nextDay = GregorianCalendar.getInstance();
        nextDay.set(Calendar.HOUR_OF_DAY, 0);
        nextDay.set(Calendar.MINUTE, 0);
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");

        try {
            while (!this.isInterrupted()) {
                if(!(AlarmQueue.alarmQueue.isEmpty())){
                    if(AlarmQueue.alarmQueue.peek().getAlarmTime() <= System.currentTimeMillis()) {
                        Schedule top = AlarmQueue.alarmQueue.poll();
                        Platform.runLater(()->{
                            Notifications n = Notifications.create()
                            		.title(top.getName())
                            		.text("Priority: "+top.getPriority().toString()+"\n"+
                                            "Due Date: "+ simpleDateFormat.format(top.getDueDate().getTime()) +"\n"+
                                            "Description: "+top.getDescription());
                            if(Constant.OS.contains("mac") ||
                                    Constant.OS.contains("nix") ||
                                    Constant.OS.contains("nux") ||
                                    Constant.OS.contains("aix")) n.position(Pos.TOP_RIGHT);
                            n.show();

                        });
                        alarmSound.play();
                    }
                }
                if(nextDay.getTimeInMillis() <= System.currentTimeMillis()){
                    // 다음날로 넘어갔을 때, 해야할 일
                    nextDay.add(Calendar.DAY_OF_MONTH, 1);
                    Calendar oneWeekLater = new GregorianCalendar(nextDay.get(Calendar.YEAR), nextDay.get(Calendar.MONTH), nextDay.get(Calendar.DAY_OF_MONTH));
                    oneWeekLater.add(Calendar.DAY_OF_MONTH, 7);
                    try{
                        AlarmQueue.alarmQueue.addAll(FileManager.readScheduleFile(oneWeekLater));
                    }catch(IOException ioe){
                        // nothing to do
                    }catch(ClassNotFoundException cnfe){
                        ExceptionDialog exceptionDialog = new ExceptionDialog(cnfe);
                        exceptionDialog.setOnCloseRequest(event->{
                            System.exit(1);
                        });
                        exceptionDialog.show();
                    }
                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException ie){
            System.err.println("Thread suddenly interrupted");
        }
    }

    public Calendar getNextDay() {
        return nextDay;
    }
}