package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;

import java.util.List;

public interface IStrategie {
    public Decision decider(List<Decision> opponent);
}
