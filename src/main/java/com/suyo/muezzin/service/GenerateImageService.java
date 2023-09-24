package com.suyo.muezzin.service;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.suyo.muezzin.model.GeoIP;
import com.suyo.muezzin.model.PrayTimes;
import com.suyo.muezzin.model.PrayTimesMain;
import com.suyo.muezzin.model.TimeType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.suyo.muezzin.service.PrayTimeService.getTime;

@Service
@RequiredArgsConstructor
public class GenerateImageService {

    private final GenerateQRCodeService generateQRCodeService;
    private final MessageSource source;

    private String getMessage(Locale locale, String key, Object... args) {
        return source.getMessage(key, args, key, locale);
    }

    @SneakyThrows
    public byte[] createImage(GeoIP userInfo, LocalDate date, String qrMessage, CalculationMethod calculationMethod, String imageId, Madhab madhab, TimeType timeType, Locale locale) {
        BufferedImage image = checkURL(imageId) ? ImageIO.read(new URL(imageId).openStream()) : ImageIO.read(new ClassPathResource(getImage(imageId, date)).getInputStream());
        image = resize(image, 710, 1280);
        final BufferedImage qrcode = generateQRCodeService.generateQRCode(qrMessage, 300, 300);
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(70f));
        int x = (image.getWidth() - g.getFontMetrics().stringWidth(getMessage(locale, "prayer_times"))) / 2;
        g.drawString(getMessage(locale, "prayer_times"), x, (int) (image.getHeight() * 0.10));
        g.setFont(g.getFont().deriveFont(40f));
        x = 40;
        g.drawString(getDateString(date, false, locale), x, (int) (image.getHeight() * 0.17));
        g.drawString(getDateString(date, true, locale), x, (int) (image.getHeight() * 0.23));
        g.setFont(g.getFont().deriveFont(60f));
        g.drawString(getMessage(locale, "fajr"), x, (int) (image.getHeight() * 0.30));
        g.drawString(getMessage(locale, "sunrise"), x, (int) (image.getHeight() * 0.35));
        g.drawString(getMessage(locale, "dhuhr"), x, (int) (image.getHeight() * 0.40));
        g.drawString(getMessage(locale, "afternoon_prayer"), x, (int) (image.getHeight() * 0.45));
        g.drawString(getMessage(locale, "maghrib"), x, (int) (image.getHeight() * 0.50));
        g.drawString(getMessage(locale, "isha"), x, (int) (image.getHeight() * 0.55));

        final CalculationParameters parameters = calculationMethod.getParameters();
        parameters.madhab = madhab;
        final PrayerTimes prayerTimes = new PrayerTimes(userInfo.getLocation(), new DateComponents(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), parameters);//fixme should to check -1 month

        final boolean is24Hour = timeType == TimeType.T24;
        int x2 = (image.getWidth() - g.getFontMetrics().stringWidth(is24Hour ? "15:15" : "03:15 PM")) - 40;
        g.drawString(getTime(prayerTimes.fajr, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.30));
        g.drawString(getTime(prayerTimes.sunrise, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.35));
        g.drawString(getTime(prayerTimes.dhuhr, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.40));
        g.drawString(getTime(prayerTimes.asr, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.45));
        g.drawString(getTime(prayerTimes.maghrib, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.50));
        g.drawString(getTime(prayerTimes.isha, is24Hour, userInfo.getTimeZone()), x2, (int) (image.getHeight() * 0.55));

        g.drawImage(qrcode, (image.getWidth() - qrcode.getWidth()) / 2, (int) (image.getHeight() * 0.65), null);
        int x3 = (image.getWidth() - g.getFontMetrics().stringWidth(userInfo.getFullLocation())) / 2;
        g.drawString(userInfo.getFullLocation(), x3, (int) (image.getHeight() * 0.95));
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    public PrayTimesMain createDate(GeoIP userInfo, LocalDate date, CalculationMethod method, Madhab madhab, TimeType timeType) {
        final CalculationParameters parameters = method.getParameters();
        parameters.madhab = madhab;
        final PrayerTimes prayerTimes = new PrayerTimes(userInfo.getLocation(), new DateComponents(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), parameters);//fixme should to check -1 month
        return new PrayTimesMain(getDateString(date, false, Locale.US), getDateString(date, true, Locale.US), madhab, method, new PrayTimes(prayerTimes, timeType == TimeType.T24, userInfo.getTimeZone()), userInfo);
    }

    public BufferedImage resize(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    private String getDateString(LocalDate date, boolean isHijri, Locale locale) {
        if (isHijri) {
            final LocalDate muslimDate = toMuslim(date);
            return muslimDate.getYear() + "-" + getMessage(locale, "year", "year") + " " + muslimDate.getDayOfMonth() + "-" + getMessage(locale, arabicMonth[muslimDate.getMonthValue() - 1]);
        } else {
            return date.getYear() + "-" + getMessage(locale, "year", "year") + " " + date.getDayOfMonth() + "-" + getMessage(locale, englishMonth[date.getMonthValue() - 1]);
        }
    }

    private LocalDate toMuslim(LocalDate gregorianDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final String hijrahDateString = HijrahChronology.INSTANCE.date(gregorianDate).format(formatter);
        return LocalDate.parse(hijrahDateString, formatter);
    }

    private String getImage(String image, LocalDate date) {
        return image != null ? switch (image) {
            case "1", "Monday" -> "image/imageMonday.jpeg";
            case "2", "Tuesday" -> "image/imageTuesday.jpeg";
            case "3", "Wednesday" -> "image/imageWednesday.jpeg";
            case "4", "Thursday" -> "image/imageThursday.jpeg";
            case "5", "Friday" -> "image/imageFriday.jpeg";
            case "6", "Saturday" -> "image/imageSaturday.jpeg";
            case "7", "Sunday" -> "image/imageSunday.jpeg";
            default -> "image/error.jpeg";
        } : "image/image" + toSentence(date.getDayOfWeek().toString()) + ".jpeg";
    }

    private String toSentence(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    private boolean checkURL(String imageUrl) {
        return imageUrl != null && (imageUrl.startsWith("http://") || imageUrl.startsWith("https://"));
    }

    private final String[] arabicMonth = {
        "muharram",
        "safar",
        "rabiAlAwwal",
        "rabiAlThani",
        "jumadaAlUla",
        "jumadaAlThani",
        "rajab",
        "shaban",
        "ramadan",
        "shawwal",
        "dhuAlQadah",
        "dhuAlHijjah",
    };

    private final String[] englishMonth = {
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december",
    };
}
