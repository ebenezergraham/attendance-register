package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Ebenezer Graham
 */
public class Swipe implements Comparable<Swipe> {

    /**
     *
     */
    protected final int id;

    /**
     *
     */
    protected String cardId;

    /**
     *
     */
    protected String room;

    /**
     *
     */
    protected final Calendar swipeDateTime;

    private static int lastSwipeIdUsed = 0;
    static final char EOLN = '\n';
    static final String QUOTE = "\"";

    /**
     *
     */
    public static Comparator<Swipe> swipeDateTimeComparator = new Comparator<Swipe>() {
        @Override
        public int compare(Swipe swipe1, Swipe swipe2) {
            return swipe1.getSwipeDateTime().compareTo(swipe2.getSwipeDateTime());
        }
    };

    /**
     *
     */
    public Swipe() {
        this.id = ++lastSwipeIdUsed;
        this.cardId = "Unknown";
        this.room = "Unknown";
        this.swipeDateTime = getNow();
    }

    /**
     *
     * @param cardId
     * @param room
     */
    public Swipe(String cardId, String room) {
        this.id = ++lastSwipeIdUsed;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = getNow();
    }

    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     */
    public Swipe(int swipeId, String cardId, String room, Calendar swipeDateTime) {
        this.id = swipeId;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = swipeDateTime;
        if (swipeId > Swipe.lastSwipeIdUsed) {
            Swipe.lastSwipeIdUsed = swipeId;
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    // Methods required: getters, setters, hashCode, equals, compareTo, comparator
    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Calendar getSwipeDateTime() {
        return this.swipeDateTime;
    }

    private Calendar getNow() {
        Calendar now = Calendar.getInstance();
        return now;
    }

    /**
     *
     * @param calendar
     * @return
     */
    public static String formatSwipeDateTime(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nSwipe Id: " + this.id + " - Card Id: " + this.cardId
                + " - Room: " + this.room + " - Swiped: " + formatSwipeDateTime(this.swipeDateTime);
    }

    public String toString(char delimiter) {
        return Integer.toString(this.id) + delimiter
                + QUOTE + this.cardId + QUOTE + delimiter
                + QUOTE + this.room + QUOTE + delimiter
                + QUOTE + formatSwipeDateTime(this.swipeDateTime) + QUOTE;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 69 * hash + this.id;
        hash = 69 * hash + Objects.hashCode(this.cardId);
        hash = 69 * hash + Objects.hashCode(this.room);
        hash = 69 * hash + Objects.hashCode(this.swipeDateTime);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Swipe swipe = (Swipe) o;
        if (this.id != swipe.id) {
            return false;
        }
        if (!Objects.equals(this.cardId, swipe.cardId)) {
            return false;
        }
        if (!Objects.equals(this.room, swipe.room)) {
            return false;
        }
        if (!Objects.equals(this.swipeDateTime, swipe.swipeDateTime)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param compareSwipe
     * @return
     */
    @Override
    public int compareTo(Swipe compareSwipe) {
        int newSwipeId = compareSwipe.getId();
        return this.getId() - newSwipeId;
    }
}
