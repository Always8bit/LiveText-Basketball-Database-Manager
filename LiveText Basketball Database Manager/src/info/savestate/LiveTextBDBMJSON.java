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
        write();
    }
    
    public void newPlayer(JSONObject team) {
        team.getJSONArray("players").put(blankPlayer());
        write();
    }
    
    public void newUpcomingGame(JSONObject statsList) {
        statsList.getJSONArray("upcominggames").put(blankUpcomingGame());
        write();
    }
    
    public void newKeysToTheGame(JSONObject statsList) {
        statsList.getJSONArray("keystothegame").put("key to the game");
        write();
    }
    
    public void setSavePath(String savePath) {
        json.put("savepath", savePath);
        write();
    }

    public void setLive(JSONObject player) {
        json.put("live", player);
        write();
    }
    
    public JSONArray getTeams() {
        return json.getJSONArray("teams");
    }
    
    public JSONObject getAwayStats() {
        return json.getJSONObject("stats").getJSONObject("away");
    }

    public JSONObject getHomeStats() {
        return json.getJSONObject("stats").getJSONObject("home");
    }
    
    public JSONObject getLive() {
        return json.getJSONObject("live");
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
        blank.put("live", blankPlayer());
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

    public String getKeyValues() {
        StringBuilder sb = new StringBuilder();
        String n = System.lineSeparator();
        // AWAY STATS
        JSONObject stats = json.getJSONObject("stats").getJSONObject("away");
        sb.append("LTDB_AWAY_STATS_FG = ").append(stats.getString("fieldgoals")).append(n);
        sb.append("LTDB_AWAY_STATS_3PTFG = ").append(stats.getString("3ptfieldgoals")).append(n);
        sb.append("LTDB_AWAY_STATS_FT = ").append(stats.getString("freethrows")).append(n);
        sb.append("LTDB_AWAY_STATS_RB = ").append(stats.getString("rebounds")).append(n);
        sb.append("LTDB_AWAY_STATS_TO = ").append(stats.getString("turnovers")).append(n);
        
        sb.append(n);
        
        for (int i=0; i<stats.getJSONArray("scoringleaders").length(); i++) {
            JSONArray array = stats.getJSONArray("scoringleaders");
            sb.append("LTDB_AWAY_SCL_").append(i).append("_FN = ")
                    .append(array.getJSONObject(i).getString("firstname")).append(n);
            sb.append("LTDB_AWAY_SCL_").append(i).append("_LN = ")
                    .append(array.getJSONObject(i).getString("lastname")).append(n);
            sb.append("LTDB_AWAY_SCL_").append(i).append("_N = ")
                    .append(array.getJSONObject(i).getString("number")).append(n);
            sb.append("LTDB_AWAY_SCL_").append(i).append("_PTS = ")
                    .append(array.getJSONObject(i).getString("points")).append(n);
        }
        for (int i=0; i<stats.getJSONArray("startinglineup").length(); i++) {
            JSONArray array = stats.getJSONArray("startinglineup");
            sb.append("LTDB_AWAY_STL_").append(i).append("_FN = ")
                    .append(array.getJSONObject(i).getString("firstname")).append(n);
            sb.append("LTDB_AWAY_STL_").append(i).append("_LN = ")
                    .append(array.getJSONObject(i).getString("lastname")).append(n);
            sb.append("LTDB_AWAY_STL_").append(i).append("_N = ")
                    .append(array.getJSONObject(i).getString("number")).append(n);
            sb.append("LTDB_AWAY_STL_").append(i).append("_POS = ")
                    .append(array.getJSONObject(i).getString("position")).append(n);
        }
        for (int i=0; i<stats.getJSONArray("keystothegame").length(); i++) {
            JSONArray array = stats.getJSONArray("keystothegame");
            sb.append("LTDB_AWAY_KTG_").append(i).append(" = ")
                    .append(array.getString(i)).append(n);
        }
        for (int i=0; i<stats.getJSONArray("upcominggames").length(); i++) {
            JSONArray array = stats.getJSONArray("upcominggames");
            sb.append("LTDB_AWAY_UP_").append(i).append("_D = ")
                    .append(array.getJSONObject(i).getString("date")).append(n);
            sb.append("LTDB_AWAY_UP_").append(i).append("_VS = ")
                    .append(array.getJSONObject(i).getString("vs")).append(n);
            sb.append("LTDB_AWAY_UP_").append(i).append("_T = ")
                    .append(array.getJSONObject(i).getString("time")).append(n);
        }
        
        sb.append(n);
        
        // HOMEE STATS
        stats = json.getJSONObject("stats").getJSONObject("home");
        sb.append("LTDB_HOME_STATS_FG = ").append(stats.getString("fieldgoals")).append(n);
        sb.append("LTDB_HOME_STATS_3PTFG = ").append(stats.getString("3ptfieldgoals")).append(n);
        sb.append("LTDB_HOME_STATS_FT = ").append(stats.getString("freethrows")).append(n);
        sb.append("LTDB_HOME_STATS_RB = ").append(stats.getString("rebounds")).append(n);
        sb.append("LTDB_HOME_STATS_TO = ").append(stats.getString("turnovers")).append(n);
        
        sb.append(n);
        
        for (int i=0; i<stats.getJSONArray("scoringleaders").length(); i++) {
            JSONArray array = stats.getJSONArray("scoringleaders");
            sb.append("LTDB_HOME_SCL_").append(i).append("_FN = ")
                    .append(array.getJSONObject(i).getString("firstname")).append(n);
            sb.append("LTDB_HOME_SCL_").append(i).append("_LN = ")
                    .append(array.getJSONObject(i).getString("lastname")).append(n);
            sb.append("LTDB_HOME_SCL_").append(i).append("_N = ")
                    .append(array.getJSONObject(i).getString("number")).append(n);
            sb.append("LTDB_HOME_SCL_").append(i).append("_PTS = ")
                    .append(array.getJSONObject(i).getString("points")).append(n);
        }
        for (int i=0; i<stats.getJSONArray("startinglineup").length(); i++) {
            JSONArray array = stats.getJSONArray("startinglineup");
            sb.append("LTDB_HOME_STL_").append(i).append("_FN = ")
                    .append(array.getJSONObject(i).getString("firstname")).append(n);
            sb.append("LTDB_HOME_STL_").append(i).append("_LN = ")
                    .append(array.getJSONObject(i).getString("lastname")).append(n);
            sb.append("LTDB_HOME_STL_").append(i).append("_N = ")
                    .append(array.getJSONObject(i).getString("number")).append(n);
            sb.append("LTDB_HOME_STL_").append(i).append("_POS = ")
                    .append(array.getJSONObject(i).getString("position")).append(n);
        }
        for (int i=0; i<stats.getJSONArray("keystothegame").length(); i++) {
            JSONArray array = stats.getJSONArray("keystothegame");
            sb.append("LTDB_HOME_KTG_").append(i).append(" = ")
                    .append(array.getString(i)).append(n);
        }
        for (int i=0; i<stats.getJSONArray("upcominggames").length(); i++) {
            JSONArray array = stats.getJSONArray("upcominggames");
            sb.append("LTDB_HOME_UP_").append(i).append("_D = ")
                    .append(array.getJSONObject(i).getString("date")).append(n);
            sb.append("LTDB_HOME_UP_").append(i).append("_VS = ")
                    .append(array.getJSONObject(i).getString("vs")).append(n);
            sb.append("LTDB_HOME_UP_").append(i).append("_T = ")
                    .append(array.getJSONObject(i).getString("time")).append(n);
        }
        
        sb.append(n);
        
        // Player Stats
        
        stats = json.getJSONObject("live");
        
        sb.append("LTDB_LIVE_FT  = ").append(stats.getString("freethrows")).append(n);
        sb.append("LTDB_LIVE_N   = ").append(stats.getString("number")).append(n);
        sb.append("LTDB_LIVE_RB  = ").append(stats.getString("rebounds")).append(n);
        sb.append("LTDB_LIVE_FN  = ").append(stats.getString("firstname")).append(n);
        sb.append("LTDB_LIVE_HT  = ").append(stats.getString("hometown")).append(n);
        sb.append("LTDB_LIVE_Y   = ").append(stats.getString("year")).append(n);
        sb.append("LTDB_LIVE_FTA = ").append(stats.getString("freethrowattempts")).append(n);
        sb.append("LTDB_LIVE_W   = ").append(stats.getString("weight")).append(n);
        sb.append("LTDB_LIVE_P   = ").append(stats.getString("position")).append(n);
        sb.append("LTDB_LIVE_LN  = ").append(stats.getString("lastname")).append(n);
        sb.append("LTDB_LIVE_H   = ").append(stats.getString("height")).append(n);
        sb.append("LTDB_LIVE_PTS = ").append(stats.getString("points")).append(n);
        
        sb.append(n);
        
        return sb.toString();
    }
    
    
}
