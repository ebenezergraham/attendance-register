package model;

import java.util.Calendar;

/**
 *
 * @author Ebenezer Graham
 */
public class VisitorSwipe extends Swipe {

    /**
     *
     */
    protected String visitorName;

    /**
     *
     */
    protected String visitorCompany;

    /**
     *
     */
    private static final char EOLN = '\n';
    private static final char QUOTE = '"';

    public VisitorSwipe() {
        super();
        this.visitorName = "Unknown";
        this.visitorCompany = "Unknown";
    }

    /**
     *
     * @param cardId
     * @param room
     * @param visitorName
     * @param visitorCompany
     */
    public VisitorSwipe(String cardId, String room, String visitorName, String visitorCompany) {
        super(cardId, room);
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
    }

    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     * @param visitorName
     * @param visitorCompany
     */
    public VisitorSwipe(int swipeId, String cardId, String room, Calendar swipeDateTime, String visitorName, String visitorCompany) {
        super(swipeId, cardId, room, swipeDateTime);
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
    }

    // Methods required: getters, setters  
    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: " + this.visitorName + " - Company: " + this.visitorCompany;
    }

    @Override
    public String toString(char delimiter) {
        return super.toString(delimiter) + delimiter
                + QUOTE + this.visitorName + QUOTE
                + delimiter
                + QUOTE + this.visitorCompany + QUOTE;
    }
}