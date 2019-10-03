package com.frontline;


import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class CCInvoker {

    public void queryBlockchain(){}

    //should add suitable return type
    public String invokeChaincode(){
        return null;
    }

    public Channel getChannel(HFClient hfClient, String channelName)throws InvalidArgumentException, TransactionException {
        // initialize channel
        Peer peer = hfClient.newPeer("peer0.org1.example.com", "grpc://localhost:7051");
        EventHub eventHub = hfClient.newEventHub("eventhub01", "grpc://localhost:7053");
        Orderer orderer = hfClient.newOrderer("orderer.example.com", "grpc://localhost:7050");
        Channel channel = hfClient.newChannel(channelName);
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
        return channel;
    }

    public HFClient getHFClient() throws Exception{
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;

    }

    public MSchainUser getMSchainUser(HFCAClient caClient, MSchainUser registrar, String userId,String affiliation,String mspId) throws Exception{
        MSchainUser msChainUser = tryDeserialize(userId);
        if (msChainUser == null) {
            RegistrationRequest registrationRequest = new RegistrationRequest(userId, affiliation);
            String enrollmentSecret = caClient.register(registrationRequest, registrar);
            Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
            msChainUser = new MSchainUser(userId, affiliation,  enrollment,mspId);
            serialize(msChainUser);
        }
        return msChainUser;
    }

    public MSchainUser getAdmin(HFCAClient caClient,String userId, String secret, String affiliation, String mspId) throws Exception{
        MSchainUser admin = tryDeserialize(userId);
       // String n = caClient.info().getCAName();
       // System.out.println(n);
        if (admin == null) {
            Enrollment adminEnrollment = caClient.enroll(userId, secret);
            admin = new MSchainUser(userId, affiliation,  adminEnrollment,mspId);
            serialize(admin);
        }
        return admin;
    }

    public HFCAClient getHFCAClient(String caUrl, Properties caClientProperties) throws Exception{
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        System.out.println(cryptoSuite);
        HFCAClient caClient = HFCAClient.createNewInstance("ca-org1",caUrl, caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    static MSchainUser tryDeserialize(String name) throws Exception {
        if (Files.exists(Paths.get(name + ".jso"))) {
            return deserialize(name);
        }
        return null;
    }

    static MSchainUser deserialize(String name) throws Exception {
        try (ObjectInputStream decoder = new ObjectInputStream(
                Files.newInputStream(Paths.get("",name + ".jso")))) {
            return (MSchainUser) decoder.readObject();
        }
    }


    static void serialize(MSchainUser msChainUser) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(
                Paths.get(msChainUser.getName() + ".jso")))) {
            oos.writeObject(msChainUser);
        }
    }
}
