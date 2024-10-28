package com.livinglive.llft.reaction.dto;

import com.livinglive.llft.reaction.Reaction;
import java.util.UUID;

public record CreateReactionDto(UUID userId, Reaction.ReactionType reactionType){    
}
