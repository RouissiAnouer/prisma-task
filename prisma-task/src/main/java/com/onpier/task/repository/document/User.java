package com.onpier.task.repository.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2134394251961071699L;

    @Id
    private String id;

    private String firstName;

    private String name;

    private Date memberSince;

    private Date memberTill;

    private String gender;

}
