Feature: SpellmongerGame

  Scenario: GameInitialisation
    Given "Alice" and "Bob" join the game
    Then Alice has 20 life points and 5 creatures and 1 energy point and 40 cards in his/her deck
    And Bob has 20 life points and 5 creatures and 1 energy point and 40 cards in his/her deck

  Scenario: GamePhase
    Given "currentPlayer" and "oppositePlayer" join the game
#    Given the "currentPlayer" is the one who clicks first the draw button
#    And "oppositePlayer"'s draw button is disabled

    Then the "currentPlayer" gets a new card from his/her deck and adds it to his/her existing creatures
    Then if the "currentPlayer" has enough energy points to summon a "creature" he/she choose the corresponding creature and summon it

    Then if there are no creatures on the opposite field the creatures summoned by the the "currentPlayer" attack directly the "oppositePlayer"

#    Then if there are creatures on the opposite field the battle phase between creatures begins
#    Then if there is a draw between creatures strength then corresponding creatures make no move
#    Then after the battle phase the remaining creatures deal their remaining strength as damage
#
#    Then if the "currentPlayer" wants to summon a ritual he/she can summon it directly and profits the effects of the ritual
#
#    Then the "oppositePlayer" becomes the "currentPlayer" and his/her draw button is enabled
#    Then both "currentPlayer" and "oppositePlayer" get 1 energy point


  Scenario: EndGame
    Given "player" and "otherPlayer" join the game
    When a "player"'s life points attain 0
    Then the "player" is dead
    And the "otherPlayer" is the winner

