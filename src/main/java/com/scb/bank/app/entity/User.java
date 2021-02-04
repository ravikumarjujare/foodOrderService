package com.scb.bank.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Id
    @TableGenerator(name = "userSequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "userSequence")
    private long id;

    @NotBlank(message = "firstName can not be blank or null")
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = "lastName can not be blank or null")
    @Column(name = "last_name")
    private String lastName;
    @Min(
            value = 1,
            message = "Age must be greater than or equal to 1"
    )
    @Column(name = "age")
    private int age;
    @Length(min = 10, max = 10)
    @Column(name = "pan_number", unique = true)
    private String panNumber;

    @Valid
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private Account account;
}
