package org.examples.notification;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/push")
public class NotifyAPI {
    private final PushNotificationService pushService;

    public NotifyAPI(PushNotificationService pushService) {
        this.pushService = pushService;
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody PushSubscription subscription) {
        // Store the subscription in your database
        System.out.println("Subscribed: " + subscription);
    }

    @PostMapping("/notify")
    public void notifySubscribers(@RequestBody String payload) throws Exception {
        // Retrieve subscriptions from the database
        /*PushSubscription subscription = new PushSubscription();
        // Mock subscription; replace with real data
        subscription.setEndpoint("...");
        subscription.setP256dh("...");
        subscription.setAuth("...");

        pushService.sendNotification(subscription, payload);*/
    }
}
