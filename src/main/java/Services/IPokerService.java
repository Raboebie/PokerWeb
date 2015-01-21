package Services;

import Cards.Hand;

/**
 * Created by dihan on 15/01/12.
 */
public interface IPokerService {
    String getName();
    public void createDeck();
    public String evaluateDeck(Hand hand);
    public Hand dealHand();

}
