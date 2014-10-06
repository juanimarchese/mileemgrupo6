package com.mileem.mileem.utils;

import com.mileem.mileem.models.IdName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan-Asus on 05/10/2014.
 */
public  class DefinitionsUtils {
    public static List<String> convertToStringList(List<IdName> list){
        List<String> stringList = new ArrayList<String>();
        for (IdName idName : list){
            stringList.add(idName.getName());
        }
        return stringList;
    }

    public static int findIdByName(List<IdName> list,String name){
        for (IdName idName : list){
            if(idName.getName().equals(name)) return idName.getId();
        }
        return -1;
    }
}
