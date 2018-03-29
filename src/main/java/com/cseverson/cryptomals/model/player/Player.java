package com.cseverson.cryptomals.model.player;

import com.cseverson.cryptomals.helper.JSONBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.logging.Logger;

/**
 * Persistent player account entity with JPA markup.
 *
 * @author Chandler Severson
 */
@Entity
@Table(name = "T_PLAYER")
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player implements Serializable{

    private static final Logger log = Logger.getLogger(Player.class.getName());
    private static final long serialVersionUID = 1L;

    public static Long nextId = 0L;
    public static boolean nextIdInit = false;

    @Id
    @GeneratedValue
    protected Long id;

    @Column(name = "heart_count")
    protected int heartCount;

    @Column(name = "next_heart_time")
    protected Date nextHeartTime;

    @Column(name = "admin_status")
    protected boolean adminStatus;

    @Column(name = "start_date")
    protected Date startDate;

    @Column(name = "time_played")
    protected Long timePlayed;

    @Column(name = "ads_viewed")
    protected int adsViewed;

    @Column(name = "user_name")
    protected String userName;


    /*
        Default JPA Constructor
     */
    protected Player(){
        heartCount = 0;
        nextHeartTime = null;
        adminStatus = false;
        startDate = null;
        timePlayed = 0L;
        adsViewed = 0;
    }

    public Player(String userName){
        if(userName == null || userName.length() == 0){
            log.warning("Player(String userName) FAILED - Blank or Null Username Supplied.");
            return ;
        }
        this.userName = userName;
        heartCount = 5; //TODO make the heartCount variable declared in a config file
        nextHeartTime = new Date(new java.util.Date().getTime());
        adminStatus = false;
        startDate = new Date(new java.util.Date().getTime());
        timePlayed = 0L;
        adsViewed = 0;
    }

    //TODO Create Builder Method for a Player

    /*
    protected static Long getNextId() {
        synchronized (nextId) {
            return nextId++;
        }
    }*/

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public Date getNextHeartTime() {
        return nextHeartTime;
    }

    public void setNextHeartTime(Date nextHeartTime) {
        this.nextHeartTime = nextHeartTime;
    }

    public boolean isAdmin() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    protected void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getAdsViewed() {
        return adsViewed;
    }

    public void setAdsViewed(int adsViewed) {
        this.adsViewed = adsViewed;
    }

    public String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    public ObjectNode getJSONString(){
        ObjectNode out = JSONBuilder.builder().getObjectNode();
        out.put("id", id)
                .put("userName", userName)
                .put("heartCount", heartCount)
                .put("nextHeartDate", nextHeartTime.toString())
                .put("adminStatus", adminStatus)
                .put("startDate", startDate.toString())
                .put("timePlayed", timePlayed);

       return out;
    }

    @Override
    public String toString() {

        String nextHeartTime = (getNextHeartTime() == null)? "NULL":getNextHeartTime().toString();
        String startDate = (getStartDate() == null) ? "NULL" : getStartDate().toString();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode out = mapper.createObjectNode();
        out.put("id", id)
                .put("userName", userName)
                .put("heartCount", heartCount)
                .put("nextHeartDate", nextHeartTime)
                .put("adminStatus", adminStatus)
                .put("startDate", startDate)
                .put("timePlayed", timePlayed);
        try {
            return mapper.writeValueAsString(out);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
