package com.cseverson.cryptomals;

import com.cseverson.cryptomals.market_service.service.MarketService;
import com.cseverson.cryptomals.player_service.service.PlayerService;
import com.cseverson.cryptomals.web_player_service.service.WebPlayerServer;
import com.cseverson.cryptomals.registration_service.service.ServiceRegistrationServer;

class Main {

    public static void main(String[] args){
        String serverName = "NO-VALUE";

        switch(args.length){
            case 2:
                System.setProperty("server.port", args[1]);
            case 1:
                serverName = args[0].toLowerCase();
                break;
            default:
                usage();
                return;
        }

        if(serverName.equals("registration") || serverName.equals("reg")){
            ServiceRegistrationServer.main(args);
        }else if(serverName.equals("player") || serverName.equals("plr")){
            PlayerService.main(args);
        }else if(serverName.equals("wplayer") || serverName.equals("wplr")){
            WebPlayerServer.main(args);
        }else if(serverName.equals("market")){
            MarketService.main(args);
        }else {
            System.out.println("Unknown server type: " + serverName);
            usage();
        }

    }

    private static void usage() {
        System.out.println("Usage: java -jar ... <server-name> [server-port]");
        System.out.println(
                "     where server-name is 'reg', 'registration', " + "'player' or 'plr' and server-port > 1024");
    }
}
