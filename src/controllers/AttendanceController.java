package controllers;

import helpers.InputHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Ebenezer Graham
 */
public class AttendanceController {

    private Repository sessionRepository;
    private Repository roomRepository;
    private Repository swipeRepository;
    private InputHelper inputHelper;
    private final String TEXTEXT = ".txt";

    /**
     *
     */
    public AttendanceController() {
        this.inputHelper = new InputHelper();
        Boolean finished = false;
        do {
            char choice = inputHelper.readCharacter("Load Swipes from an existing File? Y / N", "YN");
            if (choice == 'N' || choice == 'n') {
                this.swipeRepository = new Repository();
                finished = true;
            } else if (choice == 'Y' || choice == 'y') {
                String filename;
                filename = inputHelper.readString("Enter Filename");
                this.swipeRepository = new Repository(filename + TEXTEXT);
                finished = true;
            }
        } while (!finished);
    }

    /**
     *
     */
    public void run() {
        boolean finished = false;

        do {
            char choice = displayAttendanceMenu();
            switch (choice) {
                case 'A':
                    addSwipe();
                    break;
                case 'B':
                    listSwipes();
                    break;
                case 'C':
                    listSwipesByCardIdOrderedByDateTime();
                    break;
                case 'D':
                    listSwipeStatistics();
                    break;
                case 'Q':
                    // Request a name to be used for the filename
                    String filename = inputHelper.readString("Enter filename");
                    // pass the filename to the store method in the repository
                    swipeRepository.store(filename + TEXTEXT);
                    finished = true;
            }
        } while (!finished);
    }

    private char displayAttendanceMenu() {
        InputHelper inputHelper = new InputHelper();
        System.out.print("\nA. Add Swipe");
        System.out.print("\tB. List Swipes");
        System.out.print("\tC. List Swipes In Date Time Order");
        System.out.print("\tD. List Swipes Which Match Card Id");
        System.out.print("\tQ. Quit\n");
        return inputHelper.readCharacter("Enter choice", "ABCDQ");
    }

    private void addSwipe() {
        System.out.format("\033[31m%s\033[0m%n", "Add Swipe");
        System.out.format("\033[31m%s\033[0m%n", "=========");

        // variable declarations for the implementation of swipes
        Swipe swipe;
        VisitorSwipe visitorSwipe;
        String cardId;
        String room = "General";
        Boolean done = false;
        char status;

        do {

            status = inputHelper.readCharacter("Enter Choice"
                    + "\n\n Enter S for Student/Staff with a card ID "
                    + "\t Enter X for Student/Staff without a card ID "
                    + "\n Enter V for a visitor with a card ID"
                    + "\t\t Enter Y for a visitor without a card ID "
                    + "\n Enter Q to stop adding swipes", "SVXYQ");

            switch (status) {
                case 'S':
                    cardId = inputHelper.readString("Enter Card ID");
                    swipe = new Swipe(cardId, room);
                    swipeRepository.add(swipe);
                    success();
                    break;
                case 'X':
                    swipe = new Swipe();
                    swipeRepository.add(swipe);
                    success();
                    break;
                case 'V':
                    cardId = inputHelper.readString("Enter Card ID");
                    String visitorName = inputHelper.readString("Enter Visitor Name");
                    String companyName = inputHelper.readString("Enter Company Name");
                    visitorSwipe = new VisitorSwipe(cardId, room, visitorName, companyName);
                    swipeRepository.add(visitorSwipe);
                    success();
                    break;
                case 'Y':
                    visitorSwipe = new VisitorSwipe();
                    swipeRepository.add(visitorSwipe);
                    success();
                    break;
                case 'Q':
                    done = true;
            }
        } while (!done);
    }

    private void listSwipes() {
        System.out.format("\033[31m%s\033[0m%n", "Swipes");
        System.out.format("\033[31m%s\033[0m%n", "======");
        // Created a local list item and sorted it to be in the proper order of id 
        List<Swipe> items = this.swipeRepository.getItems();
        Collections.sort(items);

        items.forEach((Swipe swipe) -> {
            System.out.println(swipe.toString());
        });
    }

    private void listSwipesByCardIdOrderedByDateTime() {
        System.out.format("\033[31m%s\033[0m%n", "Swipes By Card Id");
        System.out.format("\033[31m%s\033[0m%n", "=================");

        String cardId = inputHelper.readString("Enter Card Id");
        ListIterator<Swipe> itSwipes = this.swipeRepository.getItems().listIterator();
        List<Swipe> swipes = new ArrayList<>();

        while (itSwipes.hasNext()) {
            Swipe swipe = itSwipes.next();
            if (swipe.getCardId().equalsIgnoreCase(cardId)) {
                swipes.add(swipe);
            }
        }

        swipes.sort(Swipe.swipeDateTimeComparator.reversed());
        swipes.forEach((swipeItem) -> {
            System.out.println(swipeItem.toString());
        });
    }

    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "=========================");

        String room = inputHelper.readString("Enter Room Name");
        ListIterator<Swipe> swipesIt = this.swipeRepository.getItems().listIterator();
        List<Swipe> roomSwipes = new ArrayList<>();
        Swipe recentSwipe;

        while (swipesIt.hasNext()) {
            Swipe swipe = swipesIt.next();
            if (swipe.getRoom().equalsIgnoreCase(room)) {
                roomSwipes.add(swipe);
            }
        }

        try {
            recentSwipe = Collections.max(roomSwipes, Swipe.swipeDateTimeComparator);
            String date = Swipe.formatSwipeDateTime(recentSwipe.getSwipeDateTime());
            System.out.format("\nThe number of swiped cards for %s is %d and "
                    + "the last swiped card's date is %s\n",
                    room, roomSwipes.size(), date);
        } catch (NoSuchElementException exception) {
            System.out.format("\033[31m%s\033[0m%n", "\nThere are no swiped cards "
                    + "for the specified room");
        }
    }

    public void success() {
        System.out.println("Command Execution Successful! \n");

    }
}
