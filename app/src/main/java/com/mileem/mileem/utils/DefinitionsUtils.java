package com.mileem.mileem.utils;

import com.mileem.mileem.models.IdName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan-Asus on 05/10/2014.
 */
public  class DefinitionsUtils {
    public static List<String> convertToStringList(List<IdName> list,String defaultValue){
        List<String> stringList = new ArrayList<String>();
        if(defaultValue != null && !defaultValue.isEmpty()) stringList.add(defaultValue);
        for (IdName idName : list){
            stringList.add(idName.getName());
        }
        return stringList;
    }

    public static List<String> convertToStringList(List<IdName> list){
        return convertToStringList(list,null);
    }


    public static int findIdByName(List<IdName> list,String name){
        if(name != null) {
            for (IdName idName : list){
                if(idName.getName().equals(name)) return idName.getId();
            }
        }
        return -1;
    }

    public static IdName findIdNameByName(List<IdName> list,String name){
        if(name != null) {
            for (IdName idName : list){
                if(idName.getName().equals(name)) return idName;
            }
        }
        return null;
    }

    public static IdName findIdNameById(List<IdName> list,String id){
        if(id != null) {
            for (IdName idName : list){
                if(idName.getId() == Integer.parseInt(id)) return idName;
            }
        }
        return null;
    }
}
