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
        
    }
    
}
