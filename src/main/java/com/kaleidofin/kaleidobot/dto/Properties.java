package com.kaleidofin.kaleidobot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class Properties {

    @JsonProperty(defaultValue = "Name")
    Name Name;
}
