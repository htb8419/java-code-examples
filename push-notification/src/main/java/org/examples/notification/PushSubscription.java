package org.examples.notification;

public record PushSubscription(String endpoint,
                               String auth,
                               String p256dh
                               ) {
}
