package com.kaleidofin.kaleidobot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class Page {
    String object;
    String id;
    Properties properties;
    Paragraph paragraph;
    String url;
}
