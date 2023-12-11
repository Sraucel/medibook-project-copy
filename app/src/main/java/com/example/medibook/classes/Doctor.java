package com.example.medibook.classes;

public class Doctor extends User
{

    private String employeeNumber;
    private String specialties;


    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, String status, String employeeNumber, String specialties,String id)
    {
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address,status,id);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
    }

    public String getEmployeeNumber(){
        return employeeNumber;
    }
    public String getSpecialties(){
        return specialties;
    }


}
