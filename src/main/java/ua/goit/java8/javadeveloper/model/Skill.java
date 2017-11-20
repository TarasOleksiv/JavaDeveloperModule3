package ua.goit.java8.javadeveloper.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Taras on 11.11.2017.
 */

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER) // зв'язок скіли <--> девелопери
    @JoinTable(name = "developer_skills",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers;

    public  Skill(){

    }

    public Skill(String name){
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
                //"\n        developers=" + showDevelopers() +
                "}";
    }

    private String showDevelopers(){
        String result = "";
        for (Developer developer: developers){
            result += developer + ",\n    ";
        }
        return result;
    }

    public String showSkillDevelopers(){
        return "Skill{" +
                "id=" +id + " " +
                "name=" + name + ";\n    " +
                "{Developers:\n    " +
                showDevelopers() +
                "}}";
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Skill obj2 = (Skill) obj;
        if((this.id == obj2.getId()) && (this.name.equals(obj2.getName()))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int tmp = 0;
        tmp = ( id + name).hashCode();
        return tmp;
    }
}
