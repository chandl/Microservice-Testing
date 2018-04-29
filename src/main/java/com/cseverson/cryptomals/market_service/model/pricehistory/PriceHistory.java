package com.cseverson.cryptomals.market_service.model.pricehistory;

import com.cseverson.cryptomals.common.Const;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.logging.Logger;

@Entity
@Table(name = "T_PRICE_HISTORY")
public class PriceHistory implements Serializable {
    public static final Logger log = Logger.getLogger(PriceHistory.class.getName());
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long historyId;

    //foreign key
    @Column(name = Const.DB.HISTORY_COIN_COL)
    protected Long cryptoCoinId;


    @Column(name = Const.DB.HISTORY_TIME_COL)
    protected Timestamp historyTime;

    @Column(name = Const.DB.HISTORY_PRICE_COL)
    protected double historicPrice;

    /**
     * Default JPA Constructor
     */
    public PriceHistory() {

        cryptoCoinId = null;
        historyTime = new Timestamp(System.currentTimeMillis());
        historicPrice = 0.0;

    }

    public PriceHistory(Long cryptoCoinId, Timestamp historyTime, double historicPrice) {

        if(cryptoCoinId == null || cryptoCoinId < 0 ){
            log.warning("PriceHistory Failed: cryptoCoinId null or less than zero.");
            return;
        }

        //TODO check if cryptoCoinId is a valid cryptocoin

        if(historyTime == null){
            log.info("PriceHistory Failed: historyTime null.");
            return ;
        }

        if(historicPrice < 0){
            log.info("PriceHistory Failed: historicPrice less than zero");
            return ;
        }

        this.cryptoCoinId = cryptoCoinId;
        this.historyTime = historyTime;
        this.historicPrice = historicPrice;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public Long getCryptoCoinId() {
        return cryptoCoinId;
    }

    public void setCryptoCoinId(Long cryptoCoinId) {
        this.cryptoCoinId = cryptoCoinId;
    }

    public Timestamp getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(Timestamp historyTime) {
        this.historyTime = historyTime;
    }

    public double getHistoricPrice() {
        return historicPrice;
    }

    public void setHistoricPrice(double historicPrice) {
        this.historicPrice = historicPrice;
    }
}
