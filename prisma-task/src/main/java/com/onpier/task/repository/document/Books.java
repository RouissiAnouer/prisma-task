package com.onpier.task.repository.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public class Books implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2134394251961071699L;

    @Id
    private String id;

    private String title;

    private String author;

    private String genre;

    private String publisher;

}
