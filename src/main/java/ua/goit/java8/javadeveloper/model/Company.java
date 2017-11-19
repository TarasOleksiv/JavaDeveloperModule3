package ua.goit.java8.javadeveloper.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Taras on 11.11.2017.
 */

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Developer> developers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Project> projects;

    public  Company(){

    }

    public Company(String name){
        this.name = name;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


    public Company withId(Long id){
        this.id = id;
        return this;
    }

    public Company withName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //"\n        developers=" + showDevelopers() +
                //"\n        projects=" + showProjects() +
                "}";
    }

    private String showDevelopers(){
        String result = "";
        for (Developer developer: developers){
            result += developer + ",";
        }
        return result;
    }

    private String showProjects(){
        String result = "";
        for (Project project: projects){
            result += project + ",";
        }
        return result;
    }
}
