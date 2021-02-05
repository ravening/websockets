package com.rakeshv.tiktaktoe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextMessage {
    private int x;
    private int y;
    private boolean firstPlayer;
}
