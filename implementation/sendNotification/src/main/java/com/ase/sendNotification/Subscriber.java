package com.ase.sendNotification;

import com.ase.common.sendNotification.NotificationContent;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class defining callback methods, which will be called when a RabbitMQ message arrives. Can be
 * seen as a parallel to {@link org.springframework.web.bind.annotation.RestController}
 *
 * TODO: THIS CLASS IS NOT FUNCTIONAL ATM. NEED TO CHANGE LOGIC TO QUERY FOR A USER GIVEN AN ID
 */
@Component
public class Subscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    public final NotificationSendService notificationSendService;

    @Autowired
    public Subscriber(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    /**
     * Extracts the relevant inforamtion from {@link NotificationContent} and forwards in order to
     * send a notification.
     *
     * @param notificationContent {@link NotificationContent} content of the to be sent
     *                            notification
     */
    public void sendNotification(NotificationContent notificationContent) {
        LOGGER.info(String.format("Sending a message to %s", notificationContent.userId()));
        notificationSendService.sendNotification(notificationContent.userId(),
                notificationContent.message());
    }
}
