package ua.goit.java8.javadeveloper.model;

/**
 * Created by Taras on 11.11.2017.
 */
public class Skill {
    private Long id;
    private String name;

    public  Skill(){

    }

    public Skill(Long id, String name){
        this.id = id;
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

    public Skill withId(Long id){
        this.id = id;
        return this;
    }

    public Skill withName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
