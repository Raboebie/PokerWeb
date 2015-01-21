package Cards;



import java.util.*;
import java.util.stream.Collectors;

public class Evaluator {


    public static boolean isFourOfAKindFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());


        if(counts.size() == 2 && counts.get(1) == 4L)
            return true;

        return false;

    }

    public static boolean isThreeOfAKindFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());



        if(counts.size() == 3 && counts.get(counts.size() - 1) == 3L)
            return true;

        return false;
    }


    public static boolean isTwoPairFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());

        if(counts.size() == 3 && counts.get(counts.size()-1) == 2L && counts.get(counts.size()-2) == 2L)
            return true;

        return false;
    }

    public static boolean isOnePairFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());

        if(counts.size() == 4 && counts.get(counts.size()-1) == 2L)
            return true;

        return false;
    }

    public static boolean isFullHouseFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());

        if(counts.size() == 2 && counts.get(counts.size()-1) == 3L)
            return true;
        return false;
    }

    public static boolean isFlushFunctional(Hand hand)
    {
        List<Long> counts = hand.getCards().stream().collect(Collectors.groupingBy(x -> x.getSuit().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());

        if(counts.size() == 1)
            return true;

        return false;
    }


    public static boolean isStraightFlush(Hand hand) {
        Collections.sort(hand.getCards(), new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getRank().compareTo(o2.getRank());
            }
        });

        Rank previousRank = null;
        Suit previousSuit = null;
        for (Card card : hand.getCards()) {
            if (previousRank != null && card.getRank().ordinal() != previousRank.ordinal() + 1) {
                return false;
            }

            if (previousSuit != null && card.getSuit() != previousSuit) {
                return false;
            }

            previousRank = card.getRank();
            previousSuit = card.getSuit();
        }

        return true;
    }

    /*List<Rank> ranks = hand.getCards().stream().map(c-> c.getRank()).collect(collectors.toList());
    return hand.getCards().stream().allMatch(c-> c.getSuit() == hand.getCards().get(0).getSuit())
   && (ranks.stream().mapToInt(r-> r.ordinal()).min().getAsInt() - ranks.stream().mapToInt(r -> r.ordinal()).min()).getAsInt == 4)
   && ranks.*/

    public static boolean isFourOfAKind(Hand hand) {

        int count = 0;

        Rank previousRank = null;
        Suit previousSuit = null;

        for(Card card : hand.getCards()) {
            previousRank = card.getRank();
            count = 0;
            for (Card card2 : hand.getCards()) {
                if(previousRank == card2.getRank()) {
                    count++;
                    if(count == 4)
                        return true;
                }

            }
        }
        return false;

        /*
            List<Long> counted = hand.getCards().stream()
                                                        .collect(Collectors.groupingBy(o -> o.getRank().toString, Collectors.counting())).values()

        */

        // Map<Rank, Integer> rankMap =  new HashMap<>();

    }

    public static boolean isFullHouse(Hand hand) {

        int count = 0;

        Rank previousRank = null;
        Suit previousSuit = null;

        boolean three = false;
        boolean two = false;

        List<Card> cards = hand.getCards();
        List<Card> temp = new LinkedList<Card>();

        for(int k = 0 ; k < cards.size(); k++){
            Card sort = cards.get(k);
            for(int j = k; j < cards.size() - 1; j++){
                if(cards.get(k).getRank().compareTo(cards.get(j).getRank()) > 0)
                {
                    sort = cards.get(k);
                    Card sort2 = cards.get(j);
                    cards.set(j, sort);
                    cards.set(k,sort2);
                }

            }




        }


        for(Card card : cards) {
            previousRank = card.getRank();
            count = 0;
            for (Card card2 : cards) {
                if(previousRank == card2.getRank()) {
                    count++;

                    if(!three)
                        if(count == 3)
                            three = true;
                        else if(three){
                            if (count == 2)
                                two = true;
                        }


                }

            }
        }
        if(three && two)
            return true;

        return false;
    }

    public static boolean isFlush(Hand hand) {

        int count = 0;
        Card temp = hand.getCards().get(0);


        for(Card card : hand.getCards())
        {
            if(temp.getSuit() == card.getSuit())
                count++;
            else
                break;
        }
        if(count == 5)
            return true;

        return false;
    }
    static boolean  flag = true;
    public static boolean isStraight(Hand hand) {



        Collections.sort(hand.getCards(), new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                if (o1.getRank().compareTo(o2.getRank()) != 1)
                {
                    flag = false;
                }
                return o1.getRank().compareTo(o2.getRank());
            }
        });

        if(!flag)
            return false;

        return true;
    }

    public static boolean isThreeOfAKind(Hand hand) {

        int count = 0;
        int different = 0;

        Rank previousRank = null;
        Suit previousSuit = null;

        List<Card> cards = hand.getCards();
        List<Card> temp = new LinkedList<Card>();

        for(int k = 0 ; k < cards.size(); k++){
            Card sort = cards.get(k);
            for(int j = k; j < cards.size() - 1; j++){
                if(cards.get(k).getRank().compareTo(cards.get(j).getRank()) > 0)
                {
                    sort = cards.get(k);
                    Card sort2 = cards.get(j);
                    cards.set(j, sort);
                    cards.set(k,sort2);
                }

            }




        }

        for(Card card : cards) {
            previousRank = card.getRank();
            count = 0;
            different = 0;
            for (Card card2 : hand.getCards()) {
                if(previousRank == card2.getRank()) {
                    count++;
                    if (count == 3) {
                        if(different > 1)
                            return true;
                    }
                    else
                        different++;
                }

            }
        }
        return false;


    }

    public static boolean isTwoPair(Hand hand) {
        return false;
    }

    public static boolean isOnePair(Hand hand) {

        int count = 0;

        Card firstCard = hand.getCards().get(0);


        List<Card> cards = hand.getCards();
        List<Card> temp = new LinkedList<Card>();

        for (int k = 0; k < cards.size(); k++) {
            Card sort = cards.get(k);
            for (int j = k; j < cards.size() - 1; j++) {
                if (cards.get(k).getRank().compareTo(cards.get(j).getRank()) > 0) {
                    sort = cards.get(k);
                    Card sort2 = cards.get(j);
                    cards.set(j, sort);
                    cards.set(k, sort2);
                }

            }
        }


        for (Card card : cards) {
            if (card.getRank().compareTo(firstCard.getRank()) == 0) {
                count++;

            }
        }

        if (count == 1)
            return true;

        return false;
    }
}


