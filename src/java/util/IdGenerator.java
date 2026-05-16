package util;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    public static long generateId() {

        long timePart = new Date().getTime(); // 13 digits

        int randomPart = ThreadLocalRandom.current().nextInt(100, 999);

        // combine safely
        String finalIdStr = String.valueOf(timePart) + randomPart;

        return Long.parseLong(finalIdStr);
    }

    public static long generateAthleteId() {
        return generateId();
    }

    public static long generateGuardianId() {
        return generateId();
    }

    public static long generateAddressId() {
        return generateId();
    }

    public static long generateSportsProfileId() {
        return generateId();
    }

    public static long generateDocumentId() {
        return generateId();
    }
}