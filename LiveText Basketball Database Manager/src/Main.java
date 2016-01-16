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
import org.json.*;

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
            String line = sc.nextLine();
            if (line.isEmpty()) continue;
            switch(line.toLowerCase().charAt(0)) {
                case '0':
                    dbm.updateStats();
                    break;
                case '1':
                    dbm.updateStartingLineup(askHomeAway());
                    break;
                case '2':
                    dbm.updateKeysToTheGame(askHomeAway());
                    break;
                case '3':
                    dbm.updateScoringLeaders(askHomeAway());
                    break;
                case '4':
                    dbm.updateUpcomingGames(askHomeAway());
                    break;
                case '5':
                    dbm.addNewTeam();
                    break;
                case '6':
                    dbm.addNewPlayer(dbm.pickOutTeam());
                    break;
                case '7':
                    editExistingTeam(dbm);
                    break;
                case '8':
                    editExistingPlayer(dbm);
                    break;
                case '9':
                    dbm.updateSavePath();
                    break;
            }
        }
        
    }
    
    public static void editExistingPlayer(LiveTextBDBM dbm) {
        JSONObject player = dbm.pickOutPlayer();
        if (player == null) return;
        dbm.editPlayer(player, false);
    }
    
    public static void editExistingTeam(LiveTextBDBM dbm) {
        JSONObject team = dbm.pickOutTeam();
        if (team == null) return;
        dbm.editTeam(team, false);
    }
    
    public static boolean askHomeAway() {
        System.out.println(" [0] Home Team");
        System.out.println(" [1] Away Team");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if (line.isEmpty()) return askHomeAway();
        if (line.charAt(0) == '0') return true;
        if (line.charAt(0) == '1') return false;
        return askHomeAway();
    }
    
}
