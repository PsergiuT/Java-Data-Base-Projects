package Domain;

import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    String email;

    @Column(name = "department_id")
    Integer department_id;

    @Column(name = "salary")
    Long salary;

    public Employee() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartmentId(Integer department) {
        this.department_id = department;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getDepartmentId() {
        return department_id;
    }

    public Long getSalary() {
        return salary;
    }

    public Employee(Long id, String email, Integer department, Long salary) {
        this.id = id;
        this.email = email;
        this.department_id = department;
        this.salary = salary;
    }
}
