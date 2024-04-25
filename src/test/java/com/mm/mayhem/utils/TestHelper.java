package com.mm.mayhem.utils;

import com.mm.mayhem.model.db.geo.StateRegion;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {
    public static StateRegion createStateRegion(Long id, String name) {
        StateRegion stateRegion = new StateRegion();
        stateRegion.setId(id);
        stateRegion.setName(name);

        return stateRegion;
    }

    public static List<StateRegion> createStateRegionList(int count, String namePrefix) {
        List<StateRegion> stateRegionList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            StateRegion stateRegion = createStateRegion((long) i, namePrefix);
            stateRegionList.add(stateRegion);
        }
        return stateRegionList;
    }
}
