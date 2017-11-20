package ua.goit.java8.javadeveloper.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Taras on 11.11.2017.
 */

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne  // проект може замовити лише один замовник
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne  // проект може розроблятись лише в одній компанії
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(name = "costs")
    private BigDecimal costs;

    @ManyToMany(fetch = FetchType.EAGER) // зв'язок проекти <--> девелопери
    @JoinTable(name = "developer_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers;

    public Project() {
    }

    public Project(String name, Customer customer, Company company, BigDecimal costs) {
        this.name = name;
        this.customer = customer;
        this.company = company;
        this.costs = costs;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany(){
        return company;
    }

    public void setCompany(Company company){
        this.company = company;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Project withId(Long id){
        this.id = id;
        return this;
    }

    public Project withName(String name){
        this.name = name;
        return this;
    }

    public Project withCustomer(Customer customer){
        this.customer = customer;
        return this;
    }

    public Project withCompany(Company company){
        this.company = company;
        return this;
    }

    public Project withCosts(BigDecimal costs){
        this.costs = costs;
        return this;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer='" + customer.getName() + '\'' +
                ", company='" + company.getName() + '\'' +
                ", costs=" + costs +
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

    public String showProjectDevelopers(){
        return "Project{" +
                "id=" +id + " " +
                "name=" + name + "; \n    " +
                "{Developers: \n    " +
                showDevelopers() +
                "}}";
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Project obj2 = (Project) obj;
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
