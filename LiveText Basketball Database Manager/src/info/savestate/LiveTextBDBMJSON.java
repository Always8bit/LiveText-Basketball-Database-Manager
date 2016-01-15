/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.json.*;

/**
 *
 * @author Joseph El-Khouri
 */
public class LiveTextBDBMJSON {
    
    private JSONObject json;
    private String dbPath;
    private static int INDENTFACTOR = 2;
    private static String DEFAULT_PATH = "LiveTextBDBM.json";
    
    public LiveTextBDBMJSON() { this(DEFAULT_PATH); }
    
    public LiveTextBDBMJSON(String dbPath) {
        
        this.dbPath = dbPath;
        try (BufferedReader br = new BufferedReader(new FileReader(dbPath))) {
            // attempt to read in an existing JSON database
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
               sb.append(line).append('\n');
            // take JSON database that was read and put it into our instance DB
            json = new JSONObject(new JSONTokener(sb.toString()));
        } catch (FileNotFoundException ex) {
            // if the database doesn't exist, lets make a new one 
            // from scratch!
            try (PrintWriter writer = new PrintWriter(dbPath, "UTF-8")) {
                json = blankDatabase();
                writer.print(json.toString(INDENTFACTOR));
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ex1) {
                System.out.println("Couldn't create new database! " + dbPath);
                System.exit(-2);
            }
        } catch (IOException ex) {
            System.out.println("Error opening database! " + dbPath);
            System.exit(-1);
        }
    }
    
    private void write() {
        try (PrintWriter writer = new PrintWriter(dbPath, "UTF-8")) {
            writer.print(json.toString(INDENTFACTOR));
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex1) {
            long unixTime = System.currentTimeMillis() / 1000L;
            System.out.println("Couldn't write to database! " + dbPath);
            System.out.println("Making backup to " + unixTime + ".json");
            try (PrintWriter writer = new PrintWriter(unixTime + ".json", "UTF-8")) {
                writer.print(json.toString(INDENTFACTOR));
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ex2) {
                System.out.println("Couldn't write to backup database X:");
                System.out.println("A serious error has occured! But hopefully");
                System.out.println("this is a one time thing. If it persists, ");
                System.out.println("let Joseph El-Khouri know!");
                System.out.println(" --> joseph.elkhouri@gmail.com");
            }
        }
    }
    
    public void newTeam() {
        json.getJSONArray("teams").put(blankTeam());
    }
    
    public void newPlayer(JSONObject team) {
        team.getJSONArray("players").put(blankPlayer());
    }
    
    public void newUpcomingGame(JSONObject statsList) {
        statsList.getJSONArray("upcominggames").put(blankUpcomingGame());
    }
    
    public void newKeysToTheGame(JSONObject statsList) {
        statsList.getJSONArray("keystothegame").put("key to the game");
    }

    private JSONObject blankDatabase() {
        JSONObject blank = new JSONObject();
        // savepath
        blank.put("savepath", "");
        // teams - a json object full of teams
        blank.put("teams", new JSONArray());
        // stats
        blank.put("stats", new JSONObject());
        blank.getJSONObject("stats").put("home", blankStatsList());
        blank.getJSONObject("stats").put("away", blankStatsList());
        return blank;
    }
    
    private JSONObject blankStatsList() {
        JSONObject blank = new JSONObject();
        blank.put("fieldgoals", "");
        blank.put("3ptfieldgoals", "");
        blank.put("freethrows", "");
        blank.put("rebounds", "");
        blank.put("turnovers", "");
        blank.put("startinglineup", new JSONArray());
        blank.put("scoringleaders", new JSONArray());
        blank.put("upcominggames",  new JSONArray());
        blank.put("keystothegame",  new JSONArray());
        return blank;
    }
    
    private JSONObject blankUpcomingGame() {
        JSONObject blank = new JSONObject();
        blank.put("date", "");
        blank.put("vs", "");
        blank.put("time", "");
        return blank;
    }
    
    private JSONObject blankPlayer() {
        JSONObject blank = new JSONObject();
        blank.put("number", "");
        blank.put("position", "");
        blank.put("firstname", "");
        blank.put("lastname", "");
        blank.put("height", "");
        blank.put("weight", "");
        blank.put("year", "");
        blank.put("hometown", "");
        blank.put("points", "");
        blank.put("rebounds", "");
        blank.put("freethrows", "");
        blank.put("freethrowattempts", "");
        return blank;
    }
    
    private JSONObject blankTeam() {
        JSONObject blank = new JSONObject();
        blank.put("teamname", "");
        blank.put("mascot", "");
        blank.put("players", new JSONArray());
        blank.put("coach", "");
        return blank;
    }
    
    
    
}
