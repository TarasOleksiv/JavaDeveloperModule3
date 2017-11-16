package ua.goit.java8.javadeveloper.model;

/**
 * Created by t.oleksiv on 09/11/2017.
 */
import java.math.BigDecimal;
import java.util.List;

public class Developer {
    private Long id;
    private String firstName;
    private String lastName;
    private Long company_id;
    private BigDecimal salary;
    private List<Skill> skills;

    public Developer() {
    }

    public Developer(Long id, String firstName, String lastName, Long company_id, BigDecimal salary, List<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company_id = company_id;
        this.salary = salary;
        this.skills = skills;
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

    public Long getCompany_id(){
        return company_id;
    }

    public void setCompany_id(Long company_id){
        this.company_id = company_id;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
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

    public Developer withCompany_id(Long company_id){
        this.company_id = company_id;
        return this;
    }

    public Developer withSalary(BigDecimal salary){
        this.salary = salary;
        return this;
    }

    public Developer withSkills(List<Skill> skills){
        this.skills = skills;
        return this;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company_id='" + company_id + '\'' +
                ", salary=" + salary +
                '}';
    }

    private String showSkills(){
        String result = "";
        for (Skill skill: skills){
            result += skill.getName() + ",";
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
}