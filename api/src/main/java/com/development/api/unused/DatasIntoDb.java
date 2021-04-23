package com.development.api.unused;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.development.api.model.city;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DatasIntoDb {

    public static void InsertAlldataintoDB(){
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<city> cities = Arrays.asList(mapper.readValue(Paths.get("/home/sivabalakrishnan/Downloads/city_list.json").toFile(), city[].class));
            cities.forEach((cit) ->{
                System.out.println(cit.toString());
            });
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public static void main(String args[]){
        InsertAlldataintoDB();
    }
}
