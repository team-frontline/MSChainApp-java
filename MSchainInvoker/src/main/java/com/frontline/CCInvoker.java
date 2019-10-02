package com.frontline;


import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

public class CCInvoker {

    public void queryBlockchain(){}

    //should add suitable return type
    public String invokeChaincode(){
        return null;
    }

    public Channel getChannel(HFClient hfClient, String channelName){
        return null;
    }

    public HFClient getHFClient() throws Exception{
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;

    }

    public MSchainUser getMSchainUser(HFCAClient caClient, MSchainUser registrar, String userId) throws Exception{
//        MSchainUser appUser = tryDeserialize(userId);
//        if (appUser == null) {
//            RegistrationRequest rr = new RegistrationRequest(userId, "org1");
//            String enrollmentSecret = caClient.register(rr, registrar);
//            Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
//            appUser = new AppUser(userId, "org1", "Org1MSP", enrollment);
//            //serialize(appUser);
//        }
//        return appUser;
        return null;
    }

    public MSchainUser getAdmin(){
        return null;
    }

    public HFCAClient getHFCAClient(){
        return null;
    }
}
