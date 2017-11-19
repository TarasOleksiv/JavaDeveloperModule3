package ua.goit.java8.javadeveloper.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Taras on 11.11.2017.
 */

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Project> projects;

    public  Customer(){

    }

    public Customer(String name){
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Customer withId(Long id){
        this.id = id;
        return this;
    }

    public Customer withName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //"\n        projects=" + showProjects() +
                "}";
    }

    private String showProjects(){
        String result = "";
        for (Project project: projects){
            result += project + ",";
        }
        return result;
    }
}
