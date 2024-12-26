package org.examples.notification;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;


public class PushNotificationService {
    private final PushService pushService;

    public PushNotificationService(PrivateKey privateKey, PublicKey publicKey, String subject) {
        this.pushService = new PushService();
        /*this.pushService.setPublicKey(publicKey);
        this.pushService.setPrivateKey(privateKey);*/
        try {
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            pushService.setPublicKey("BBYCxwATP2vVgw7mMPHJfT6bZrJP2iUV7OP_oxHzEcNFenrX66D8G34CdEmVULNg4WJXfjkeyT0AT9LwavpN8M4=");
            pushService.setPrivateKey("AKYLHgp-aV3kOys9Oy6QgxNI6OGIlOB3G6kjGvhl57j_");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        this.pushService.setSubject(subject);
    }

    public void sendNotification(PushSubscription subscription, String payload) throws Exception {
    /*    Notification notification = new Notification(
                subscription.endpoint(),
                Base64.getDecoder().decode(subscription.p256dh()),
                Base64.getDecoder().decode(subscription.auth()),
                payload
        );
        pushService.send(notification);*/
    }
}
