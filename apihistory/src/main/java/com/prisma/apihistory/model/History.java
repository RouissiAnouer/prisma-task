package com.prisma.apihistory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class History {

    @Id
    private String id;

    private String message;

    private Long timestamp;

}
