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
    
    public void updateStats() {
        JSONObject player = pickOutPlayer();
        if (player == null) {
            System.out.println("Stats not updated!");
            return;
        }
        editPlayer(player, true);
        jsonDB.setLive(player);
    }
    
    public void updateBreaktimeStats(boolean homeAway) {
        System.out.println("Enter a blank line to not change a current value");
        Scanner sc = new Scanner(System.in);
        String line;
        
        JSONObject stats;
        if (homeAway) stats = jsonDB.getHomeStats();
        else stats = jsonDB.getAwayStats();
        
        
        System.out.print("freethrows (" + stats.getString("freethrows") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            stats.put("freethrows", line);

        System.out.print("rebounds (" + stats.getString("rebounds") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            stats.put("rebounds", line);

        System.out.print("fieldgoals (" + stats.getString("fieldgoals") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            stats.put("fieldgoals", line);

        System.out.print("turnovers (" + stats.getString("turnovers") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            stats.put("turnovers", line);

        System.out.print("3ptfieldgoals (" + stats.getString("3ptfieldgoals") + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            stats.put("3ptfieldgoals", line);
        
        jsonDB.write();
    }

    public void pickOutPlayerAndRemove() {
        System.out.println("Browse for player to delete...");
        JSONObject team = pickOutTeam();
        if (team == null) return;
        JSONArray players = team.getJSONArray("players");
        for (int i=0; i<players.length(); i++) {
            JSONObject player = players.getJSONObject(i);
            System.out.println(" [" + player.getString("number") + "] "
                                    + player.getString("firstname") + " "
                                    + player.getString("lastname"));
        }
        System.out.print("Player # --> ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if (line.isEmpty()) return;
        for (int i=0; i<players.length(); i++) {
            if (players.getJSONObject(i).getString("number").equals(line)) {
                players.remove(i);
                System.out.println("Removed!");
                return;
            }
        }
        System.out.println("Player not found!");
        jsonDB.write();
    }
    
    public JSONObject pickOutPlayer() {
        System.out.println("Browse for player...");
        JSONObject team = pickOutTeam();
        if (team == null) return null;
        JSONArray players = team.getJSONArray("players");
        for (int i=0; i<players.length(); i++) {
            JSONObject player = players.getJSONObject(i);
            System.out.println(" [" + player.getString("number") + "] "
                                    + player.getString("firstname") + " "
                                    + player.getString("lastname"));
        }
        System.out.print("Player # --> ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if (line.isEmpty()) return null;
        for (int i=0; i<players.length(); i++) {
            if (players.getJSONObject(i).getString("number").equals(line)) {
                return players.getJSONObject(i);
            }
        }
        System.out.println("Player not found!");
        return null;
    }
    
    public JSONObject pickOutTeam() {
        System.out.println("Browse for team...");
        JSONArray teams = jsonDB.getTeams();
        for (int i=0; i<teams.length(); i++) {
            System.out.println(" [" + i + "] " + teams.getJSONObject(i).getString("teamname"));
        }
        System.out.print("Pick a team -> ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if (line.isEmpty()) {
            return null;
        }
        try {
            int index = Integer.parseInt(line);
            JSONObject returnTeam = teams.getJSONObject(index);
            return returnTeam;
        } catch (NumberFormatException | JSONException e) {
            return pickOutTeam();
        }
    }
    
    public void updateSavePath() {
        System.out.println("Enter a blank line to not change a current value");
        Scanner sc = new Scanner(System.in);
        
        String line;
        
        System.out.print("LTDB Savepath (" + jsonDB.getSavePath() + ") --> ");
        line = sc.nextLine();
        if (!line.isEmpty())
            jsonDB.setSavePath(line);
    }
    
    public void updateStartingLineup(boolean homeAway) {
        
        JSONArray startingLineup = new JSONArray();
        for (int i=0; i<5; i++) {
            System.out.println((i+1) + "/5 starting lineup players ...");
            JSONObject player = pickOutPlayer();
            if (player == null) return;
            startingLineup.put(player);
        }
        
        if (homeAway) jsonDB.getHomeStats().put("startinglineup", startingLineup);
        else jsonDB.getAwayStats().put("startinglineup", startingLineup);
        
        jsonDB.write();
    }
    
    
    public void updateKeysToTheGame(boolean homeAway) {
        JSONArray keys = new JSONArray();
        for (int i=0; i<3; i++) {
            System.out.print("Key " + (i+1) + "/3 --> ");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            if (!line.isEmpty())
                keys.put(line);
        }

        if (homeAway) jsonDB.getHomeStats().put("keystothegame", keys);
        else jsonDB.getAwayStats().put("keystothegame", keys);
        
        jsonDB.write();
    }
    
    public void updateScoringLeaders(boolean homeAway) {
        
        JSONArray scoringLeaders = new JSONArray();
        for (int i=0; i<4; i++) {
            System.out.println((i+1) + "/4 scoring leaders ...");
            JSONObject player = pickOutPlayer();
            if (player == null) return;
            editPlayer(player, true);
            scoringLeaders.put(player);
        }

        if (homeAway) jsonDB.getHomeStats().put("scoringleaders", scoringLeaders);
        else jsonDB.getAwayStats().put("scoringleaders", scoringLeaders);

        jsonDB.write();
    } 
    
    public void updateUpcomingGames(boolean homeAway) {
        
        JSONArray games = new JSONArray();
        for (int i=0; i<5; i++) {
            System.out.println((i+1) + "/5 games ...");
            Scanner sc = new Scanner(System.in);
            String line;

            JSONObject game = LiveTextBDBMJSON.blankUpcomingGame();
            
            System.out.print("DATE --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                game.put("date", line);

            System.out.print("VS --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                game.put("vs", line);

            System.out.print("TIME --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                game.put("time", line);
            
            games.put(game);
        }

        if (homeAway) jsonDB.getHomeStats().put("upcominggames", games);
        else jsonDB.getAwayStats().put("upcominggames", games);

        jsonDB.write();
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
            
            System.out.print("Add players? Y/N -> ");
            line = sc.nextLine();
            if (!line.isEmpty()) {
                if (line.toLowerCase().charAt(0) != 'y') {
                    keepAdding = false;
                }
            } else {
                keepAdding = false;
            }
            
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
            
            System.out.print("NUMBER (" + player.getString("number") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("number", line);

            System.out.print("HEIGHT (" + player.getString("height") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("height", line);

            System.out.print("WEIGHT (" + player.getString("weight") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("weight", line);

            System.out.print("YEAR (" + player.getString("year") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("year", line);

            System.out.print("HOMETOWN (" + player.getString("hometown") + ") --> ");
            line = sc.nextLine();
            if (!line.isEmpty())
                player.put("hometown", line);
        } else {

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
            
        }

        jsonDB.write();
        
        System.out.println("Player update complete.");
    } 
    
    public String livePlayerAsString() {
        StringBuilder sb = new StringBuilder();
        // # P [First Last] xxxLBS | x' x" | XX. | City, ST
        // PTS: XX - FT: XX - FTA: XX - RB: XX
        JSONObject player = jsonDB.getLive();
        sb.append("#").append(player.getString("number")).append(" ")
                .append(player.getString("position"))
                .append(" [")
                    .append(player.getString("firstname")).append(" ").append(player.getString("lastname"))
                .append("] ")
                .append(player.getString("weight")).append("LBS | ")
                .append(player.getString("height")).append(" | ")
                .append(player.getString("year")).append(" | ")
                .append(player.getString("hometown")).append("\n");

        sb.append("PTS: ").append(player.getString("points"))
                .append(" - FT: ").append(player.getString("freethrows"))
                .append(" - FTA: ").append(player.getString("freethrowattempts"))
                .append(" - RB: ").append(player.getString("rebounds"));
        return sb.toString();
    }
    
}
