package com.esempla.proxy.model.dto;

import com.esempla.proxy.model.BorrowedBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name",length = 45)
    private String firstName;
    @Column(name="last_name",length = 45)
    private String lastName;
    @Column(name="address",length = 50)
    private String address;
    @Column(name="phone",length = 31)
    private String phone;

    public Client(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Set<BorrowedBook> borrowedBooks= new HashSet<>();

    public Set<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString()
    {
        return "id: "+id+" firstName: "+firstName+" lastName: "+lastName+" address: "+address+" phone: "+phone;
    }

    @Override
    public int hashCode() {
        return  id* firstName.hashCode() * lastName.hashCode()* address.hashCode()* phone.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if(this==object) return true;
        if(!(object instanceof Client)) return false;
        Client client=(Client) object;
        return this.id==client.id && this.firstName.equals(client.firstName) && this.lastName.equals(client.lastName) &&
                this.address.equals(client.address)&& this.phone.equals(client.phone);
    }
}
