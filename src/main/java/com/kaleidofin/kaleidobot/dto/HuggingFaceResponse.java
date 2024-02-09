package com.kaleidofin.kaleidobot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuggingFaceResponse {
    Double score;
    Integer start;
    Integer end;
    String answer;
}
