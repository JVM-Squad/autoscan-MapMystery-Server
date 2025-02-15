/**
 * author: david 
 */

package ch.uzh.ifi.hase.soprafs24.entity.LobbyTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.util.Map;
import java.util.HashMap;
import ch.uzh.ifi.hase.constants.lobbyStates;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.constants.GameModes;
import org.springframework.lang.Nullable;

@Entity
 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 @Table(name = "LOBBY")
 public class Lobby implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long lobbyId;

  @OneToMany(cascade = CascadeType.ALL)
  public List<User> players = new ArrayList<User>();

  @Column
  @Nullable
  private String authKey;

  @Column
  private Boolean public_lobby = true;

  @Column
  protected int playerLimit;

  @Column
  protected GameModes gamemode;

  @Column
  protected int rounds;

  @Column    
  private lobbyStates state = lobbyStates.OPEN;
   
  @ElementCollection
  @CollectionTable(name = "game_rounds", joinColumns = @JoinColumn(name = "game_id"))
  @MapKeyColumn(name = "player_id")
  @Column(name = "current_round")
  public Map<Long, Integer> currRound = new HashMap<>();  
 
  @ElementCollection
  @CollectionTable(name = "lobby_points", joinColumns = @JoinColumn(name = "lobby_id"))
  @MapKeyColumn(name = "player_id")
  @Column(name = "points")
  protected Map<Long, Float> points = new HashMap<>();
 
  public Long getId(){
    return lobbyId;
  }
 
  public List<User> getPlayers(){
   return players;
  }

  public void setGamemode(GameModes gamemode){
    this.gamemode = gamemode;
  }

  public GameModes getGamemode(){
    return this.gamemode;
  }

  public int getRounds(){
    return rounds;
  }

  public void setRounds(int rounds){
    this.rounds = rounds;
  }

  public Map<Long, Float> getPoints(){
   return points;
  }

  public Map<Long,Integer> getCurrRound(){
   return currRound;
  }
   
  public int getPlayerLimit(){
   return playerLimit;
  }

  public void setPlayerLimit(int limit){
    playerLimit = limit;
  }

  public void setPrivate(){this.public_lobby = false;}

     public void setPublic(){this.public_lobby = true;}

  public Boolean isPublic(){return this.public_lobby; }

  public lobbyStates getState(){
    return state;
  }   

  public void setLobbyState(lobbyStates lobbystate){
    this.state = lobbystate;
  }

  public void setState(lobbyStates state ){
    this.state = state;
  }

  public Map<Long, Integer> getCurrRounds(){
    return currRound;
  }

  public String getAuthKey(){
      return authKey;
  }

  public void setAuthKey(String authKey){this.authKey = authKey;}
 
  /**
   * note that player is in this game since he knows the lobbyId
   * @param score
   * @param playerId
  */
  public void setPoints(float points,Long playerId){
    if(points < 0.0){
      this.points.put(playerId, 0.0f);
    }
    this.points.put(playerId, points);
  }


}
 