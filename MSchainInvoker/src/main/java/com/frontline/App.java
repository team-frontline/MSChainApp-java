package com.frontline;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        CCInvoker ccInvoker = new CCInvoker();
       try {
           // create fabric-ca client
           String caTLSCACertPath = "/home/supimi/hyperledger/fabric-samples/first-network/crypto-config/peerOrganizations/org1.example.com/tlsca/tlsca.org1.example.com-cert.pem";

           Properties caProperties = new Properties();
           byte[] keyBytes = Files.readAllBytes(Paths.get(caTLSCACertPath));
           caProperties.put("pemBytes", keyBytes);


           HFCAClient caClient = ccInvoker.getHFCAClient("https://localhost:7054", caProperties);

           // enroll or load admin
           MSchainUser admin = ccInvoker.getAdmin(caClient,"admin","adminpw","org1","Org1MSP");

           // register and enroll new user
           MSchainUser appUser = ccInvoker.getMSchainUser(caClient, admin, "hfuser","org1","Org1MSP");
           //log.info(appUser);

           // get HFC client instance
           HFClient client = ccInvoker.getHFClient();
           // set user context
           client.setUserContext(admin);

           // get HFC channel using the client
           Channel channel = ccInvoker.getChannel(client,"mychannel");
          // log.info("Channel: " + channel.getName());

           // call query blockchain example
          // queryBlockChain(client);

       }catch (Exception e){
           e.printStackTrace();
           System.out.println(e);
       }
    }
}
