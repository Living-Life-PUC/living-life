package com.livinglive.llft.comments.dto;

import jakarta.annotation.Nullable;

public record CreateCommentDto (String content, @Nullable String url){
}
