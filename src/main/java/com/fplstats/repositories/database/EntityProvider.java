package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


public class EntityProvider {

    public Result<List<Integer>> getMatchIds(String league, int year){
        throw new NotImplementedException();
    }

}
