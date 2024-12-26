package org.examples.notification;

public record PushSubscription(String endpoint,
                               String p256dh,
                               String auth) {
}
