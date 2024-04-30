package com.example;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;




@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=45)
    private String username;

    @Column(nullable=false, length=40)
    private String password;

    public String getusername(){
        return username;
    }
    public void setusername(String username){
        this.username=username;
    }
    public String getpassword(){
        return password;
    }
    public void setpassword(String password){
        this.password=password;
    }
    public Long getid(){
        return id;
    }
    public void setid(Long id){
        this.id=id;
    }




}
