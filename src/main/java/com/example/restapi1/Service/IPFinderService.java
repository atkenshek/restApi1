package com.example.restapi1.Service;

import com.example.restapi1.Entity.IPFinder;
import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service
public class IPFinderService {
    public static IPFinder getPublicIP()  {
        String ipAddress = getUrlContents();
        IPFinder ipFinder = new IPFinder();
        IPinfo ipInfo = new IPinfo.Builder()
                .setToken("TOKEN").setCountryFile(new File("src/main/resources/en_US.json")).build(); //Token skryl radi bezopasnostio
        try {
            IPResponse response = ipInfo.lookupIP(ipAddress);
            ipFinder.setIp(response.getIp());
            ipFinder.setCity(response.getCity());
            ipFinder.setRegion(response.getRegion());
            ipFinder.setCountryCode(response.getCountryCode());
            ipFinder.setTimezone(response.getTimezone());
        } catch (
                RateLimitedException ex) {
            System.err.println("Error");
        }
        return ipFinder;
    }
    private static String getUrlContents()
    {
        StringBuilder content = new StringBuilder();
        try
        {
            URL url = new URL("https://ifconfig.me/ip");
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
