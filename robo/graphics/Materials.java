package robo.graphics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Matthias on 24.10.2015.
 */
public enum Materials {
    WEAPON,
    ARMOUR,
    WHEELS;


    private static final List<Materials> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Materials randomMaterial()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
