package org.daboo.starsapp.model.dto;

import org.daboo.starsapp.model.ui.Star;

import java.util.List;

import lombok.Data;

@Data
public class StarsCollection {
    List<Star> allStars;
}
