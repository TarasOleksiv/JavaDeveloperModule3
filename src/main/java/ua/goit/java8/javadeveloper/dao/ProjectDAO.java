package ua.goit.java8.javadeveloper.dao;

import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.model.Project;

/**
 * Created by Taras on 11.11.2017.
 */
public interface ProjectDAO extends GenericDAO<Project, Long> {

    void addDeveloper(Project project, Developer developer);
    void deleteDeveloper(Project project, Developer developer);

}
