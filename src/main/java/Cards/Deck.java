package Cards;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by dihan on 15/01/12.
 */
public class Deck {

    /*CLUBS       ('♣'),
    DIAMONDS    ('♦'),
    HEARTS      ('♥'),
    SPADES      ('♠');*/

    public List<Card> cleanDeck = new LinkedList<>();
    List<Card> shuffledDeck = new LinkedList();


    public List<Card> generateDeck(Hand hand)
    {
        return null;
    }

    public List<Card> setDeck()
    {
        //Set blank deck without special cards
        cleanDeck = new LinkedList<>();

        for(int k = 1 ; k < 10 ; k++)
        {
            for(int j = 0 ; j < 4 ; j++)
            {
                if(j == 0)
                    cleanDeck.add(new Card(Integer.toString(k+1)+ "♣"));
                else if(j == 1)
                    cleanDeck.add(new Card(Integer.toString(k+1)+ "♦"));
                else if(j == 2)
                    cleanDeck.add(new Card(Integer.toString(k+1)+ "♥"));
                else if(j == 3)
                    cleanDeck.add(new Card(Integer.toString(k+1)+ "♠"));
            }
        }

        //Set special cards

        for(int k= 0 ; k < 4 ; k++)
    {
        if(k == 0)
            cleanDeck.add(new Card("J♣"));
        else if (k == 1)
            cleanDeck.add(new Card("K♣"));
        else if(k == 2)
            cleanDeck.add(new Card("Q♣"));
        else
            cleanDeck.add(new Card("A♣"));
    }


        for(int k= 0 ; k < 4 ; k++)
        {
            if(k == 0)
                cleanDeck.add(new Card("J♦"));
            else if (k == 1)
                cleanDeck.add(new Card("K♦"));
            else if(k == 2)
                cleanDeck.add(new Card("Q♦"));
            else
                cleanDeck.add(new Card("A♦"));
        }

        for(int k= 0 ; k < 4 ; k++)
        {
            if(k == 0)
                cleanDeck.add(new Card("J♥"));
            else if (k == 1)
                cleanDeck.add(new Card("K♥"));
            else if(k == 2)
                cleanDeck.add(new Card("Q♥"));
            else
                cleanDeck.add(new Card("A♥"));
        }

        for(int k= 0 ; k < 4 ; k++)
        {
            if(k == 0)
                cleanDeck.add(new Card("J♠"));
            else if (k == 1)
                cleanDeck.add(new Card("K♠"));
            else if(k == 2)
                cleanDeck.add(new Card("Q♠"));
            else if(k == 3)
                cleanDeck.add(new Card("A♠"));
        }


        Collections.shuffle(cleanDeck);




       return cleanDeck;

    }

    public void resetDeck()
    {
        setDeck();
    }


    public List<Card> drawHand()
    {


        List<Card> hand = new LinkedList<>();
        for(int k = 0 ; k < 5 ; k++)
        {
            hand.add(cleanDeck.get(0));
            cleanDeck.remove(0);
        }

        setDeck();

        return hand;
    }




}
