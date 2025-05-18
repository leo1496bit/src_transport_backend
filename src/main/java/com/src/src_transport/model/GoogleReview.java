package com.src.src_transport.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleReview {
    private String authorName;
    private String profilePhotoUrl;
    private int rating;
    private String relativeTimeDescription;
    private String text;
}
