package com.cseverson.cryptomals.player_service.model;

import com.cseverson.cryptomals.common.Const;
import com.cseverson.cryptomals.helper.JSONBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * Persistent player account entity with JPA markup.
 *
 * @author Chandler Severson
 */
@SuppressWarnings("WeakerAccess")
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

    @Column(name = Const.DB.PLAYER_HEART_COUNT_COL)
    protected int heartCount;

    @Column(name = Const.DB.PLAYER_NEXT_HEART_COL)
    protected Timestamp nextHeartTime;

    @Column(name = Const.DB.PLAYER_ADMIN_STATUS_COL)
    protected boolean adminStatus;

    @Column(name = Const.DB.PLAYER_START_DATE_COL)
    protected Timestamp startDate;

    @Column(name = Const.DB.PLAYER_TIME_PLAYED_COL)
    protected Long timePlayed;

    @Column(name = Const.DB.PLAYER_ADS_VIEWED_COL)
    protected int adsViewed;

    @Column(name = Const.DB.PLAYER_USERNAME_COL)
    protected String userName;


    /*
        Default JPA Constructor
     */
    protected Player(){
        heartCount = 0;
        nextHeartTime = new Timestamp(System.currentTimeMillis());
        adminStatus = false;
        startDate = new Timestamp(System.currentTimeMillis());;
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
        long time = System.currentTimeMillis();
        nextHeartTime = new Timestamp(time);
        adminStatus = false;
        startDate = new Timestamp(time);
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

    public Timestamp getNextHeartTime() {
        return nextHeartTime;
    }

    public void setNextHeartTime(Timestamp nextHeartTime) {
        this.nextHeartTime = nextHeartTime;
    }

    public boolean isAdmin() {
        return adminStatus;
    }

    protected void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    protected void setStartDate(Timestamp startDate) {
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

    public ObjectNode toObjectNode(){
        String nextHeartTime = (getNextHeartTime() == null)? "NULL":getNextHeartTime().toString();
        String startDate = (getStartDate() == null) ? "NULL" : getStartDate().toString();

        ObjectNode out = JSONBuilder.builder().getObjectNode();
        out.put(Const.PLAYER_ID, id)
                .put(Const.PLAYER_USERNAME, userName)
                .put(Const.PLAYER_HEART_COUNT, heartCount)
                .put(Const.PLAYER_NEXT_HEART_DATE, nextHeartTime)
                .put(Const.PLAYER_ADMIN_STATUS, adminStatus)
                .put(Const.PLAYER_START_DATE, startDate)
                .put(Const.PLAYER_TIME_PLAYED, timePlayed)
                .put(Const.PLAYER_ADS_VIEWED, adsViewed);

       return out;
    }

    @Override
    public String toString() {

        String nextHeartTime = (getNextHeartTime() == null)? "NULL":getNextHeartTime().toString();
        String startDate = (getStartDate() == null) ? "NULL" : getStartDate().toString();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode out = mapper.createObjectNode();
        out.put(Const.PLAYER_ID, id)
                .put(Const.PLAYER_USERNAME, userName)
                .put(Const.PLAYER_HEART_COUNT, heartCount)
                .put(Const.PLAYER_NEXT_HEART_DATE, nextHeartTime)
                .put(Const.PLAYER_ADMIN_STATUS, adminStatus)
                .put(Const.PLAYER_START_DATE, startDate)
                .put(Const.PLAYER_TIME_PLAYED, timePlayed)
                .put(Const.PLAYER_ADS_VIEWED, adsViewed);
        try {
            return mapper.writeValueAsString(out);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
