package com.kaleidofin.kaleidobot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pages {
    String object;
    List<Page> results;
}
