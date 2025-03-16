package com.memorycards;

import java.util.Random;

public class Shuffle {
    public static void shuffleCards(Card[][] cards, String randomImage) {
        Random random = new Random();

        // between 0 - 3; bound is 4
        int randomRow1 = random.nextInt(4);
        int randomColumn1 = random.nextInt(4);
        // let's check if cell is already taken and find another empty cell
        while (cards[randomRow1][randomColumn1] != null) {
            randomRow1 = random.nextInt(4);
            randomColumn1 = random.nextInt(4);
        }

        // between 0 - 3; bound is 4
        int randomRow2 = random.nextInt(4);
        int randomColumn2 = random.nextInt(4);
            /* let's check if cell is already taken or cell is not
             the same as first cell and find another empty cell */
        while ((randomRow1 == randomRow2 && randomColumn1 == randomColumn2)
                || cards[randomRow2][randomColumn2] != null) {
            randomRow2 = random.nextInt(4);
            randomColumn2 = random.nextInt(4);
        }

        // Assigning one random image to two cells/cards
        cards[randomRow1][randomColumn1] = new Card(randomImage, randomRow1, randomColumn1);
        cards[randomRow2][randomColumn2] = new Card(randomImage, randomRow2, randomColumn2);
    }
}
