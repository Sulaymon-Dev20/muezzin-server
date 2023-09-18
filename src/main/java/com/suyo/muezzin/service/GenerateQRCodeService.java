package com.suyo.muezzin.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

@Service
public class GenerateQRCodeService {
    public BufferedImage generateQRCode(String data, int h, int w) throws WriterException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), BarcodeFormat.QR_CODE, w, h));
    }
}
