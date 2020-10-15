package Utils;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("can not instantiate Class ColorUtils");
    }

    public static Color getRandomColor(){
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        return new Color(rand.nextInt(0, 255 + 1), rand.nextInt(0, 255 + 1), rand.nextInt(0, 255 + 1));
    }
}
