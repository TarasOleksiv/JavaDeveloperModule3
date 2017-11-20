package ua.goit.java8.javadeveloper.model;

/**
 * Created by t.oleksiv on 09/11/2017.
 */
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "salary")
    private BigDecimal salary;

    @ManyToOne  // девелопер може працювати лише в одній компанії
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER) // зв'язок девелопери <--> скіли
    @JoinTable(name = "developer_skills",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;

    @ManyToMany(fetch = FetchType.EAGER) // зв'язок девелопери <--> проекти
    @JoinTable(name = "developer_projects",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    public Developer() {
    }

    public Developer(String firstName, String lastName, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Developer withId(Long id){
        this.id = id;
        return this;
    }

    public Developer withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public Developer withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public Developer withSalary(BigDecimal salary){
        this.salary = salary;
        return this;
    }

    public Developer withCompany(Company company){
        this.company = company;
        return this;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", company='" + company.getName() + '\'' +
                //"\n        skills=" + showSkills() +
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

    public String showDeveloperProjects(){
        return "Developer{" +
                firstName + " " +
                lastName + "; " +
                "{Projects: " +
                showProjects() +
                "}}";
    }

    private String showSkills(){
        String result = "";
        for (Skill skill: skills){
            result += skill + ",";
        }
        return result;
    }

    public String showDeveloperSkills(){
        return "Developer{" +
                firstName + " " +
                lastName + "; " +
                "{Skills: " +
                showSkills() +
                "}}";
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Developer obj2 = (Developer) obj;
        if((this.id == obj2.getId()) && (this.firstName.equals(obj2.getFirstName())) && (this.lastName.equals(obj2.getLastName()))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int tmp = 0;
        tmp = ( id + firstName + lastName).hashCode();
        return tmp;
    }

}