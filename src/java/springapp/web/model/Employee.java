package springapp.web.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee - Phiên bản sửa lỗi: Dùng BigDecimal cho cột DECIMAL
 */
@Entity
@Table(name = "employee")
public class Employee implements java.io.Serializable {

    @Id
    @Column(name = "Employee_Number", unique = true, nullable = false)
    private int employeeNumber;

    @Column(name = "idEmployee", nullable = false)
    private int idEmployee;

    @Column(name = "Last_Name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "First_Name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "SSN", nullable = false)
    private long ssn;

    @Column(name = "Pay_Rate", length = 40)
    private String payRate;

    @Column(name = "PayRates_id")
    private Integer payRatesId;

    @Column(name = "Vacation_Days")
    private Integer vacationDays;

    // --- SỬA QUAN TRỌNG: Dùng BigDecimal để hứng DECIMAL(2,0) ---
    @Column(name = "Paid_To_Date")
    private BigDecimal paidToDate;

    @Column(name = "Paid_Last_Year")
    private BigDecimal paidLastYear;
    // -----------------------------------------------------------

    public Employee() {
    }

    public Employee(int employeeNumber, int idEmployee, String lastName, String firstName, long ssn) {
        this.employeeNumber = employeeNumber;
        this.idEmployee = idEmployee;
        this.lastName = lastName;
        this.firstName = firstName;
        this.ssn = ssn;
    }

    public Employee(int employeeNumber, int idEmployee, String lastName, String firstName, long ssn, String payRate, Integer payRatesId, Integer vacationDays, BigDecimal paidToDate, BigDecimal paidLastYear) {
        this.employeeNumber = employeeNumber;
        this.idEmployee = idEmployee;
        this.lastName = lastName;
        this.firstName = firstName;
        this.ssn = ssn;
        this.payRate = payRate;
        this.payRatesId = payRatesId;
        this.vacationDays = vacationDays;
        this.paidToDate = paidToDate;
        this.paidLastYear = paidLastYear;
    }

    public int getEmployeeNumber() {
        return this.employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getIdEmployee() {
        return this.idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getSsn() {
        return this.ssn;
    }

    public void setSsn(long ssn) {
        this.ssn = ssn;
    }

    public String getPayRate() {
        return this.payRate;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }

    public Integer getPayRatesId() {
        return this.payRatesId;
    }

    public void setPayRatesId(Integer payRatesId) {
        this.payRatesId = payRatesId;
    }

    public Integer getVacationDays() {
        return this.vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    // --- Getter/Setter dùng BigDecimal ---
    public BigDecimal getPaidToDate() {
        return this.paidToDate;
    }

    public void setPaidToDate(BigDecimal paidToDate) {
        this.paidToDate = paidToDate;
    }

    public BigDecimal getPaidLastYear() {
        return this.paidLastYear;
    }

    public void setPaidLastYear(BigDecimal paidLastYear) {
        this.paidLastYear = paidLastYear;
    }
}