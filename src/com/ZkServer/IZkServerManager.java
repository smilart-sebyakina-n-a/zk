package com.ZkServer;

import java.util.ArrayList;

import com.ZkServer.ZkServerConfiguration;

public interface IZkServerManager {
       
    String addZkSrv(ZkServerConfiguration zkSrv) throws Exception;
    
    void updateZkSrv(String node, ZkServerConfiguration configuration, int version) throws Exception;
    
    void removeZkSrv(String pid) throws Exception;
    
    void printZkSrv () throws Exception;
    
    ArrayList<ZkSrvEnumerationEntry> enumerate() throws Exception;
}
