package com.cseverson.cryptomals.common;

public interface Const {
    //Player Variable Naming Constants
    String PLAYER_ID = "id";
    String PLAYER_USERNAME = "userName";
    String PLAYER_HEART_COUNT = "heartCount";
    String PLAYER_NEXT_HEART_DATE = "nextHeartDate";
    String PLAYER_ADMIN_STATUS = "adminStatus";
    String PLAYER_START_DATE = "startDate";
    String PLAYER_TIME_PLAYED = "timePlayed";
    String PLAYER_ADS_VIEWED = "adsViewed";

    interface DB {
        //Player Column Naming Constants
        String PLAYER_USERNAME_COL = "user_name";
        String PLAYER_HEART_COUNT_COL = "heart_count";
        String PLAYER_NEXT_HEART_COL = "next_heart_time";
        String PLAYER_ADMIN_STATUS_COL = "admin_status";
        String PLAYER_START_DATE_COL = "start_date";
        String PLAYER_TIME_PLAYED_COL = "time_played";
        String PLAYER_ADS_VIEWED_COL = "ads_viewed";
    }

}
