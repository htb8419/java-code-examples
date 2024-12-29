package org.examples.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Urgency;
import org.apache.http.HttpResponse;
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

    public void sendNotification(Subscription subscription, String payload) throws Exception {
        NotificationPayload notificationPayload=new NotificationPayload("newOrder => 1",payload,"test");
        Notification notification = new Notification(subscription,payload, Urgency.HIGH);

        HttpResponse httpResponse =pushService.send(notification);
        System.out.println(httpResponse.getStatusLine().getStatusCode());
    }

    public record NotificationPayload(String title,String body,String icon){

    }
}
