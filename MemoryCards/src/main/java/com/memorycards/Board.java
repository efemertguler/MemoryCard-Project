package com.memorycards;

public class Board {
   private Card[][] cards;

   public Board() {
       cards =  new Card[4][4]; // 4x4 Table of Cards
   }
    public void populateCards() {
        int imageIndex = 0;
        while (!isBoardFull()) {
            String[] images = {"joker", "lotus", "money", "present", "woodpecker",
                    "vain", "santa-clause", "cormorant"};

            // let's find an image from the array
            String randomImage = images[imageIndex];
            imageIndex++;
            // let's shuffle the cards places each time we start the game (run the program)
            Shuffle.shuffleCards(cards, randomImage);
        }
    }

    public boolean areAllCardsFlipped() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (!cards[i][j].isFlipped())
                    return false;
        return true;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (cards[i][j] == null) return false;
        return true;
    }

    public Card[][] getCards() { return cards; }
}
