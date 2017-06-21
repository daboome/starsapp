package org.daboo.starsapp.model.ui;

import android.view.View;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Star {
    private String starName;
    private String xcoord;
    private String ycoord;
    private StarType starType;
    private String discoveredPerson;
    @JsonIgnore
    private View.OnClickListener editStarBtnClickListener;

    public Star() {

    }

    public Star(String starName, String xcoord, String ycoord, StarType starType, String discoveredPerson) {
        this();
        this.starName = starName;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.starType = starType;
        this.discoveredPerson = discoveredPerson;
    }

}
