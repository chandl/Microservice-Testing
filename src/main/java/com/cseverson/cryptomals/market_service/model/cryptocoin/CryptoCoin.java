package com.cseverson.cryptomals.market_service.model.cryptocoin;

import com.cseverson.cryptomals.common.Const;

import javax.persistence.*;
import java.io.Serializable;
import java.util.logging.Logger;

@Entity
@Table(name = "T_CRYPTO_COIN")
public class CryptoCoin implements Serializable {

    private static final Logger log = Logger.getLogger(CryptoCoin.class.getName());
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long coinId;

    @Column(name= Const.DB.COIN_NAME_COL)
    protected String coinName;

    @Column(name = Const.DB.COIN_VALUE_COL)
    protected double coinValue;

    @Column(name = Const.DB.COIN_OUTSTANDING_COL)
    protected Long coinOutsatnding;

    /**
     * Default JPA Constructor
     */
    protected CryptoCoin() {
        coinName = "";
        coinOutsatnding = 0L;
        coinValue = 0.0;
    }

    public CryptoCoin(String coinName, double coinValue, Long coinOutsatnding) {

        if(coinName == null || coinName.length() == 0){
            log.warning("CryptoCoin(String coinName) Failed. Blank or Null coinName supplied.");
            return ;
        }

        if(coinValue <= 0) {
            log.warning("CryptoCoin(double coinValue) Failed. Value is less than or equal to zero.");
            return ;
        }

        if(coinOutsatnding == null || coinOutsatnding < 0){
            log.warning("CryptoCoin(Long coinOutstanding) Failed. Outstanding coins null or less than zero.");
            return ;
        }

        this.coinName = coinName;
        this.coinValue = coinValue;
        this.coinOutsatnding = coinOutsatnding;


    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getCoinOutsatnding() {
        return coinOutsatnding;
    }

    public void setCoinOutsatnding(Long coinOutsatnding) {
        this.coinOutsatnding = coinOutsatnding;
    }

    public double getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(double coinValue) {
        this.coinValue = coinValue;
    }
}
