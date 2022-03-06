package ru.mariamaximova.bl1.application.auth.model;

import java.util.ArrayList;
import java.util.List;

public class UserXmlListDto {
    private final List<UserXmlDto> list;

    public UserXmlListDto(){
        list = new ArrayList<>();
    }

    public void add(UserXmlDto p){
        list.add(p);
    }

    public List<UserXmlDto> get(){
        return list;
    }
}

