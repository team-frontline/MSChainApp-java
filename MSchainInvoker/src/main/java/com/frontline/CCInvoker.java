package com.frontline;


import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class CCInvoker {

    public void queryBlockchain(){}

    //should add suitable return type
    public String invokeChaincode(){}

    public Channel getChannel(HFClient hfClient, String channelName){}

    public HFClient getHFClient(){}

    public MSchainUser getMSchainUser(){}

    public MSchainUser getAdmin(){}

    public HFCAClient getHFCAClient(){}
}
