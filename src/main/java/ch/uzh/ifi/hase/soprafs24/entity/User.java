package ch.uzh.ifi.hase.soprafs24.entity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Column(nullable = false, unique = true)
    private String username;

    // Used for email verificaiton
    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(nullable = false)
    private Boolean verified=false;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    // TODO: needs to be changed to type Date according to class diagram
    // Then the DTOs must also be changed
    private String creationdate;

    // TODO: needs to be changed to type tuple according to class diagram
    // then the DTOs must also be changed
   
    @Column
    private float score;

    @Transient // Marking this as transient to avoid persistence issues, adjust according to your JPA implementation details
    private List<User> friends;

    @ElementCollection
    private List<String> friendrequests;

    @Column
    private float currentpoints;

    @Column
    private Boolean featured_in_rankings = true;

    private String generateToken() {
      return Jwts.builder()
        .setSubject(this.username)
        .signWith(SignatureAlgorithm.HS512, "secrete_key")
        .compact();
    }

    public String getVerificationToken() {
        return verificationCode;
    }

    public void setVerificationToken(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getFeatured_in_rankings() {
        return this.featured_in_rankings;
    }

    public void setFeatured_in_rankings(Boolean featured_in_rankings) {
        this.featured_in_rankings = featured_in_rankings;
    }

    public String getToken() {
        return this.generateToken();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
      this.password = encoder.encode(password);
    }
  
    public boolean checkPassword(String pw) {
        return encoder.matches(pw, this.password);
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }


    

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<String> getFriendrequests() {
        return this.friendrequests;
    }

    public void setFriendrequests(List<String> friendrequests) {
        this.friendrequests = friendrequests;
    }
    public Boolean getVerified(){
        return this.verified;
    }
    
    public void setVerified(Boolean state){
        this.verified = state;
    }

    public float getCurrentpoints() {
        return currentpoints;
    }

    public void setCurrentpoints(float currentpoints) {
        this.currentpoints = currentpoints;
    }

    public void update(User user_with_new_data){
        if (user_with_new_data.getPassword() != null && !user_with_new_data.getPassword().isEmpty()) {
            this.setPassword(user_with_new_data.getPassword());
        }

        if (user_with_new_data.getUsername() != null && !user_with_new_data.getUsername().isEmpty()) {
            this.setUsername(user_with_new_data.getUsername());
        }

        if (user_with_new_data.getStatus() != "UNDEF" && !user_with_new_data.getStatus().isEmpty()) {
            this.setStatus(user_with_new_data.getStatus());
        }

        if (user_with_new_data.getUserEmail() != null && !user_with_new_data.getUserEmail().isEmpty()) {
            this.setUserEmail(user_with_new_data.getUserEmail());
        }

        if (user_with_new_data.getFeatured_in_rankings() != null && user_with_new_data.getFeatured_in_rankings() != null) {
            this.setFeatured_in_rankings(user_with_new_data.getFeatured_in_rankings());
        }
    }

    
}

