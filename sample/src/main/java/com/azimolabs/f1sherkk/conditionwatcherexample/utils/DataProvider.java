package com.azimolabs.f1sherkk.conditionwatcherexample.utils;

import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by F1sherKK on 13/04/16.
 */
public class DataProvider {

    public static List<Server> generateServerList() {
        List<Server> serverList = new ArrayList<>();

        Server server0 = new Server("Example Server 1", "192.168.0.1", "8000");
        Server server1 = new Server("Example Server 2", "192.168.0.2", "8000");
        Server server2 = new Server("Example Server 3", "192.168.0.3", "8000");
        Server server3 = new Server("Example Server 4", "192.168.0.4", "8000");
        Server server4 = new Server("Example Server 5", "192.168.0.5", "8000");

        serverList.add(server0);
        serverList.add(server1);
        serverList.add(server2);
        serverList.add(server3);
        serverList.add(server4);

        return serverList;
    }
}
