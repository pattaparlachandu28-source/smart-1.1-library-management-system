package edu.smartlibrary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100) private String name;
    @Column(nullable=false,length=160) private String email;
    @Column(nullable=false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Role role=Role.STUDENT;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    protected AppUser() {}
    public AppUser(String name,String email,String passwordHash,Role role){this.name=name;this.email=email;this.passwordHash=passwordHash;this.role=role;}
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
    public String getPasswordHash(){return passwordHash;} public Role getRole(){return role;} public LocalDateTime getCreatedAt(){return createdAt;}
}
