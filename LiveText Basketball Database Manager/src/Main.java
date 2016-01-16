/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

/**
 *
 * @author Joseph El-Khouri
 */

import info.savestate.*;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // If the user provides a database, use it.
        // Otherwise, use the default database.
        LiveTextBDBMJSON jsonDB;
        if (args.length > 0)
            jsonDB = new LiveTextBDBMJSON(args[0]);
        else 
            jsonDB = new LiveTextBDBMJSON();
        // pass this to the database manager
        LiveTextBDBM dbm = new LiveTextBDBM(jsonDB);
        
        // start the loop! 
        while(true) {
            
            System.out.println("LIVE: ");
            System.out.println(dbm.livePlayerAsString());
            System.out.println(" ----------------------- ");
            System.out.println("What would you like to do?");
            System.out.println(" [0] Quick Update Live Stats");
            System.out.println(" [1] Update Starting Lineup");
            System.out.println(" [2] Update Keys to the Game");
            System.out.println(" [3] Update Scoring Leaders");
            System.out.println(" [4] Update Upcoming Games");
            System.out.println(" [5] Add New Team");
            System.out.println(" [6] Add New Player");
            System.out.println(" [7] Edit Existing Team");
            System.out.println(" [8] Edit Existing Player");
            System.out.println(" [9] Update Savepath");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        }
        
    }
    
}
