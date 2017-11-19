package ua.goit.java8.javadeveloper.dao;

import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.model.Skill;

/**
 * Created by t.oleksiv on 09/11/2017.
 */
public interface DeveloperDAO extends GenericDAO<Developer, Long> {

    void addSkill(Developer developer,Skill skill);
    void deleteSkill(Developer developer,Skill skill);

}