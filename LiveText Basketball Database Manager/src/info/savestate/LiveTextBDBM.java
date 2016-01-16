/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate;

import java.util.Scanner;
import org.json.*;

/**
 *
 * @author Joseph El-Khouri
 */
public class LiveTextBDBM {
    
    private final LiveTextBDBMJSON jsonDB;
    
    public LiveTextBDBM(LiveTextBDBMJSON jsonDB) {
        this.jsonDB = jsonDB;
    }
    
    public LiveTextBDBMJSON getJsonDB() {
        return jsonDB;
    }
    
    public void addNewTeam() {
        JSONObject newTeam = LiveTextBDBMJSON.blankTeam();
        editTeam(newTeam, false);
        jsonDB.addTeam(newTeam);
        jsonDB.write();
    }
    
    public void editTeam(JSONObject team, boolean quick) {
        System.out.println("Enter a blank line to not change a current value");
        Scanner sc = new Scanner(System.in);
        
        String line;
        
        System.out.print("TEAM NAME (" + team.getString("teamname") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            team.put("teamname", line);
        
        if (!quick) {
            System.out.println("Add players to your team!");

            boolean keepAdding = true;
            while(keepAdding) {
                
                addNewPlayer(team);
                
                System.out.print("Add another? Y/N -> ");
                line = sc.nextLine();
                if (!line.isEmpty()) {
                    if (line.toLowerCase().charAt(0) != 'y') {
                        keepAdding = false;
                    }
                } else {
                    keepAdding = false;
                }
            }
        }
        jsonDB.write();
    }
    
    public void addNewPlayer(JSONObject team) {
        System.out.println("Add player to team: " + team.getString("teamname"));
        JSONObject newPlayer = LiveTextBDBMJSON.blankPlayer();
        editPlayer(newPlayer, false);
        JSONArray teamPlayers = team.getJSONArray("players");
        // make sure the number doesn't clash! 
        for (int i=0; i<teamPlayers.length(); i++) {
            if (teamPlayers.getJSONObject(i).getString("number").equals(newPlayer.getString("number"))) {
                System.out.println("Warning! There is already a player #" 
                        + newPlayer.getString("number") 
                        + " in team " 
                        + team.getString("teamname"));
                System.out.print("Overwrite? Y/N -> ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if (!line.isEmpty()) {
                    if (line.toLowerCase().charAt(0) == 'y') {
                        teamPlayers.put(i, newPlayer);
                        System.out.println("New player added!");
                        jsonDB.write();
                        return;
                    } else {
                        System.out.println("Player addition aborted");
                        return;
                    }
                } else {
                    System.out.println("Player addition aborted");
                    return;
                }
            }
        }
        teamPlayers.put(newPlayer);
        System.out.println("New player added!");
        jsonDB.write();
    }
    
    public void editPlayer(JSONObject player, boolean quick) {
        System.out.println("Enter a blank line to not change a current value");
        Scanner sc = new Scanner(System.in);
        
        String line;
        
        if(!quick) {
        
            System.out.print("NUMBER (" + player.getString("number") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("number", line);

            System.out.print("FIRSTNAME (" + player.getString("firstname") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("firstname", line);

            System.out.print("LASTNAME (" + player.getString("lastname") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("lastname", line);

            System.out.print("POSITION (" + player.getString("position") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("position", line);

            System.out.print("YEAR (" + player.getString("year") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("year", line);

            System.out.print("HOMETOWN (" + player.getString("hometown") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("hometown", line);

            System.out.print("WEIGHT (" + player.getString("weight") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("weight", line);

            System.out.print("HEIGHT (" + player.getString("height") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("height", line);
        
        }

        System.out.print("POINTS (" + player.getString("points") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            player.put("points", line);

        System.out.print("FREETHROWS (" + player.getString("freethrows") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            player.put("freethrows", line);

        System.out.print("FREETHROW ATTEMPTS (" + player.getString("freethrowattempts") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            player.put("freethrowattempts", line);

        System.out.print("REBOUNDS (" + player.getString("rebounds") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            player.put("rebounds", line);

        jsonDB.write();
        
        System.out.println("Player update complete.");
    } 
    
}
