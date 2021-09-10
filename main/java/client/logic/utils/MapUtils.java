package client.logic.utils;

import client.logic.Cell.CellColor;
import client.logic.tetromino.Tetromino.TetrominoType;

import java.util.HashMap;

import static client.logic.Cell.CellColor.*;
import static client.logic.tetromino.Tetromino.TetrominoType.*;

public class MapUtils {
    public static HashMap<Integer, Integer> createSpeedLevelMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 720);
        map.put(1, 640);
        map.put(2, 580);
        map.put(3, 500);
        map.put(4, 440);
        map.put(5, 360);
        map.put(6, 300);
        map.put(7, 220);
        map.put(8, 140);
        map.put(9, 100);
        map.put(10, 80);
        map.put(11, 80);
        map.put(12, 80);
        map.put(13, 60);
        map.put(14, 60);
        map.put(15, 60);
        map.put(16, 40);
        map.put(17, 40);
        map.put(18, 40);
        map.put(19, 20);
        map.put(20, 20);
        map.put(21, 20);
        map.put(22, 20);
        map.put(23, 20);
        map.put(24, 20);
        map.put(25, 20);
        map.put(26, 20);
        map.put(27, 20);
        map.put(28, 20);
        map.put(29, 20);
        return map;
    }

    public static HashMap<Integer, Integer[]> createLinePointsMap(){
        HashMap<Integer, Integer[]> map = new HashMap<>();
        map.put(0, new Integer[]{40, 100, 300, 1200});
        map.put(1, new Integer[]{80, 200, 600, 2400});
        map.put(2, new Integer[]{120, 300, 900, 3600});
        map.put(3, new Integer[]{160, 400, 1200, 4800});
        map.put(4, new Integer[]{200, 500, 1500, 6000});
        map.put(5, new Integer[]{240, 600, 1800, 7200});
        map.put(6, new Integer[]{280, 700, 2100, 8400});
        map.put(7, new Integer[]{320, 800, 2400, 9600});
        map.put(8, new Integer[]{360, 900, 2700, 10800});
        map.put(9, new Integer[]{400, 1000, 3000, 12000});
        map.put(10, new Integer[]{440, 1100, 3300, 13200});
        map.put(11, new Integer[]{480, 1200, 3600, 14400});
        map.put(12, new Integer[]{520, 1300, 3900, 15600});
        map.put(13, new Integer[]{560, 1400, 4200, 16800});
        map.put(14, new Integer[]{600, 1500, 4500, 18000});
        map.put(15, new Integer[]{640, 1600, 4800, 19200});
        map.put(16, new Integer[]{680, 1700, 5100, 20400});
        map.put(17, new Integer[]{720, 1800, 5400, 21600});
        map.put(18, new Integer[]{760, 1900, 5700, 22800});
        map.put(19, new Integer[]{800, 2000, 6000, 24000});
        map.put(20, new Integer[]{840, 2100, 6300, 25200});
        map.put(21, new Integer[]{880, 2200, 6600, 26400});
        map.put(22, new Integer[]{920, 2300, 6900, 27600});
        map.put(23, new Integer[]{960, 2400, 7200, 28800});
        map.put(24, new Integer[]{1000, 2500, 7500, 30000});
        map.put(25, new Integer[]{1040, 2600, 7800, 31200});
        map.put(26, new Integer[]{1080, 2700, 8100, 32400});
        map.put(27, new Integer[]{1120, 2800, 8400, 33600});
        map.put(28, new Integer[]{1160, 2900, 8700, 34800});
        map.put(29, new Integer[]{1200, 3000, 9000, 36000});
        return map;
    }

    public static HashMap<TetrominoType, CellColor> createColorMap(){
        HashMap<TetrominoType, CellColor> map = new HashMap<>();
        map.put(BLOCK_I, CYAN);
        map.put(BLOCK_J, BLUE);
        map.put(BLOCK_L, ORANGE);
        map.put(BLOCK_O, YELLOW);
        map.put(BLOCK_S, GREEN);
        map.put(BLOCK_T, FUCHSIA);
        map.put(BLOCK_Z, RED);
        return map;
    }
}
