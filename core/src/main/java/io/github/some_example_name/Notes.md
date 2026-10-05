# Program Notes

## The board as integers

- 1 to 8: the number of neighboring bombs
    - draw a blank tile
- -1: represents a hidden bomb
  - draw a blank tile
- 10 to 18: uncovered states of 0 to 8
  - draw an actual tile value (draw_value - 10)
- 9: bomb
  - game over (9-10=-1)
- 19-28: a flagged tile
  - draw a flagged tile
  - value is still known (flag_value - 20)

## User inputs
- left click: +10 to a tile
  - uncover a tile
- right click: +20/-20 to a tile
  - flag/unflag tile
