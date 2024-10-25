package hwa3;

import java.util.Arrays;

public class ColorSort {

    public enum Color {red, green, blue};

    public static void main (String[] param) {
        Color[] unsortedBalls = new Color[]{Color.red, Color.blue, Color.green, Color.red, Color.blue, Color.green, Color.red, Color.blue, Color.green};
        Color[] sortedBalls = new Color[]{Color.red, Color.red, Color.red, Color.green, Color.green, Color.green, Color.blue, Color.blue, Color.blue};
        reorder(unsortedBalls);
        assert Arrays.equals(unsortedBalls, sortedBalls);
    }

    public static void reorder (Color[] balls) {
        int[] count = new int[3];            // Creates a new array with length equal to number of colors in enum

        for (Color value : balls) {          // Counts occurrences of each color and adds it to an int in a count array corresponding to its ordinal value
        count[value.ordinal()]++;            // red.ordinal is [0],   green.ordinal is [1],   blue.ordinal is [2]
    }

        int redLastIndex = count[Color.red.ordinal()];                      // Finds an index of the last red ball
        int greenLastIndex = redLastIndex + count[Color.green.ordinal()];   // Finds an index of the last green ball
        int blueLastIndex = greenLastIndex + count[Color.blue.ordinal()];   // Finds an index of the last blue ball

        for (int i = 0; i < redLastIndex; i++) {    // Replaces original array with a sorted one
            balls[i] = Color.red;
        }
        for (int i = redLastIndex; i < greenLastIndex; i++) {
            balls[i] = Color.green;
        }
        for (int i = greenLastIndex; i < blueLastIndex; i++) {
            balls[i] = Color.blue;
        }
    }
}
