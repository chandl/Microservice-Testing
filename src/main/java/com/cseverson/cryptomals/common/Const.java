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

    String COIN_NAME = "coinName";
    String COIN_OUTSTANDING = "coinOutsatnding";
    String COIN_VALUE = "coinValue";

    String HISTORY_COIN_FK = "cryptoCoinId";
    String HISTORY_TIME = "historyTime";
    String HISTORY_PRICE = "historicPrice";
    interface DB {
        //Player Column Naming Constants
        String PLAYER_USERNAME_COL = "user_name";
        String PLAYER_HEART_COUNT_COL = "heart_count";
        String PLAYER_NEXT_HEART_COL = "next_heart_time";
        String PLAYER_ADMIN_STATUS_COL = "admin_status";
        String PLAYER_START_DATE_COL = "start_date";
        String PLAYER_TIME_PLAYED_COL = "time_played";
        String PLAYER_ADS_VIEWED_COL = "ads_viewed";


        String COIN_NAME_COL = "coin_name";
        String COIN_OUTSTANDING_COL = "coin_outstanding";
        String COIN_VALUE_COL = "coin_value";

        String HISTORY_COIN_FK_COL = "crypto_coin_id";
        String HISTORY_TIME_COL = "history_time";
        String HISTORY_PRICE_COL = "historic_price";
    }

}
