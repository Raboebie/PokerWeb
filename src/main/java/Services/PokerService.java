package Services;

import Cards.Card;
import Cards.Deck;
import Cards.Evaluator;
import Cards.Hand;
import com.google.inject.Singleton;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dihan on 15/01/12.
 */

public class PokerService implements IPokerService {

    public String getName()
    {
        return "Dihan";
    }

    List<Card> Carddeck = new LinkedList<>();
    Deck deck = null;
    Hand hand = null;

    public void createDeck()
    {
        deck = new Deck();
        Carddeck = new LinkedList<>();
        Carddeck = deck.setDeck();
    }

    public Hand dealHand()
    {
        List<Card> listHand = deck.drawHand();
        String[] createHand = new String[5];

        for(int k = 0; k < 5 ; k++)
        {
            createHand[k] = listHand.get(k).toString();
        }

        Hand cards = new Hand(createHand);

        return cards;
    }

    public String evaluateDeck(Hand hand)
    {

        Evaluator eval = new Evaluator();

        if(eval.isFourOfAKindFunctional(hand))
            return "Four of a kind!";
        else if(eval.isFlushFunctional(hand))
            return "Flush!";
        else if(eval.isFullHouseFunctional(hand))
            return "Full house!";
        else if(eval.isThreeOfAKindFunctional(hand))
            return "Three of a kind";
        else if(eval.isTwoPairFunctional(hand))
            return "Two Pair";
        else if(eval.isStraightFlush(hand))
            return "Straight flush!";
        else if(eval.isOnePairFunctional(hand))
            return "One Pair!";

        else
        {
            return "High card.";
        }
    }

    //Hand dealHand()
    //String enavlueateHand
}
