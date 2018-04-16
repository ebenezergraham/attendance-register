/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Ebenezer Graham
 */
public class DAOImpl implements DAOInterface {

    static final char DELIMITER = ',';
    //The details of a tyical swipe is 4 hence splitting a line into an array 
    final int SWIPELENGTH = 4;
    final int VISITORSWIPELENGTH = 6;
    final String TEXTEXT = ".txt";
    final String EOLN = "\n";

    @Override
    public Repository load(String filename) {
        Repository repository = new Repository();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Swipe swipe;
        Date date;
        VisitorSwipe visitorSwipe;
        // Used a bufferedreader to read the text file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] details;
            
            // The intent is to read a line from the file and split it into a 
            // string array
            while ((line = reader.readLine()) != null) {
                details = line.split(Character.toString(DELIMITER));
                Calendar cardDate = Calendar.getInstance();           
                try {
                    date = dateFormat.parse(removeQuotes(details[3]));
                    cardDate.setTime(date);
                } catch (ParseException ex) {
                    Logger.getLogger(DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                // The size of the array is used to determine if its a normal 
                // swipe of a visitor siwpe give that a normal swipe has four 
                // details per line and a visitor swipe has 6 details per line
                if (details.length == this.SWIPELENGTH) {
                    swipe = new Swipe(Integer.parseInt(details[0]),
                            removeQuotes(details[1]),
                            removeQuotes(details[2]),
                            cardDate);
                    repository.add(swipe);
                } else if (details.length == this.VISITORSWIPELENGTH) {
                    visitorSwipe = new VisitorSwipe(Integer.parseInt(details[0]),
                            removeQuotes(details[1]),
                            removeQuotes(details[2]),
                            cardDate,
                            removeQuotes(details[4]),
                            removeQuotes(details[5]));
                    repository.add(visitorSwipe);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return repository;
    }

    @Override
    public void store(String filename, Repository repository) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(filename))) {
            output.write(repository.toString(DELIMITER));
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // this method is used to remove the quotes around the details
    private String removeQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }
}
