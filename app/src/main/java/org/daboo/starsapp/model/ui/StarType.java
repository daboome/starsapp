package org.daboo.starsapp.model.ui;

import lombok.Data;

@Data
public class StarType {
    private String value;
    private String color;

    public StarType() {

    }

    public StarType(String value, String color) {
        this();
        this.value = value;
        this.color = color;
    }
}
