package org.examples.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;

@RestController()
@RequestMapping("/qrcode")
public class QRCodeController {
    @GetMapping()
    public ResponseEntity<StreamingResponseBody> generate() throws Exception {
        String secret = getUserSecret();
        String otpUri = generateOtpUri(secret);
        final BufferedImage originalImage = generateEAN13BarcodeImage(otpUri);
        StreamingResponseBody streamingResponseBody = (outputStream) -> {
            ImageIO.write(originalImage, MediaType.IMAGE_JPEG.getSubtype(), outputStream);
        };
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(streamingResponseBody);
    }

    private BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private String generateOtpUri(String secret) {
        //String.format("otpauth://totp/ACME%%20Co:john.doe@email.com?secret=%s&issuer=ACME%%20Co&algorithm=SHA1&digits=6&period=30", secret);
        return new Totp(secret).uri("accountName");
    }

    private String getUserSecret() {
        //load from db
        String secretKey = RandomStringUtils.randomAlphabetic(16).toUpperCase();
        System.out.printf("user secret key =>[%s]\n",secretKey);
        return secretKey;
    }
}
