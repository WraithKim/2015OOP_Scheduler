package view.stageBuilder;

import javafx.scene.Parent;
import org.controlsfx.control.NotificationPane;

/**
 * Created by Donghwan on 12/8/2015.
 *
 * 기존 Pane에 NotificationPane을 씌워주는 클래스
 */
public class NotificationPaneUpgrader {
    private static final NotificationPaneUpgrader ourInstance = new NotificationPaneUpgrader();

    public static NotificationPaneUpgrader getInstance() {
        return ourInstance;
    }

    public NotificationPane upgrade(Parent root){
        NotificationPane notificationPane = new NotificationPane();
        notificationPane.setContent(root);
        notificationPane.setShowFromTop(true);
        notificationPane.setCloseButtonVisible(true);
        return notificationPane;
    }

    private NotificationPaneUpgrader() {
    }
}
