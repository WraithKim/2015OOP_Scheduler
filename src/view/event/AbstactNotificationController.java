package view.event;

import org.controlsfx.control.NotificationPane;

/**
 * Created by Donghwan on 12/8/2015.
 *
 * 알림창을 띄우는 추상 컨트롤러
 */
public abstract class AbstactNotificationController {
    private NotificationPane notificationPane;

    public void setNotificationPane(NotificationPane notificationPane){
        this.notificationPane = notificationPane;
    }

    public void printNotificationPane(String string){
        if(notificationPane.isShowing()) {
            notificationPane.hide();
        }
        notificationPane.setText(string);
        notificationPane.show();
    }
}
