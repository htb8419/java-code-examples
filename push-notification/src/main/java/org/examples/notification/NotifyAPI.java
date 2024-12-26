package org.examples.notification;

import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/push")
public class NotifyAPI {
    private final PushNotificationService pushService;
    Set<Subscription> subscriptions = new HashSet<>();

    public NotifyAPI(PushNotificationService pushService) {
        this.pushService = pushService;
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription) {
        // Store the subscription in your database
        System.out.println("Subscribed: " + subscription);
        subscriptions.add(subscription);
    }

    @PostMapping("/notify")
    public void notifySubscribers(@RequestParam String payload) throws Exception {
        // Retrieve subscriptions from the database
        subscriptions.forEach(subscription -> {
            try {
                pushService.sendNotification(subscription, payload);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
