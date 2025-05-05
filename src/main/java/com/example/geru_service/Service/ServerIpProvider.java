package com.example.geru_service.Service;

import org.springframework.stereotype.Component;

import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServerIpProvider {

    /** Returns the host address tied to the machine’s primary hostname */
    public String getPrimaryIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /** (Optional) Returns all non-loopback IPv4 addresses on every interface */
    public List<String> getAllLanIps() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .flatMap(iface ->
                        Collections.list(iface.getInetAddresses()).stream())
                .filter(addr -> !addr.isLoopbackAddress())
                .filter(addr -> addr instanceof Inet4Address)
                .map(InetAddress::getHostAddress)
                .collect(Collectors.toList());
    }
}
