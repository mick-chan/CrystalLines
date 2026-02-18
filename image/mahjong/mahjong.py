
from PIL import Image
from PIL import ImageDraw

tiles = Image.open("mahjong2-1.png")
base = Image.open("mahjong-base2-2.png")
DIM_X = 9
DIM_Y = 4
SIZE_X = 36
SIZE_Y = 43
TILE_SIZE_X = 44
TILE_SIZE_Y = 55
OFFSET_X = 1
OFFSET_Y = 1
GAP_X = 2
GAP_Y = 2

yy = OFFSET_Y
for y in range(DIM_Y):
	xx = OFFSET_X
	for x in range(DIM_X):

		if (x == 7 or x == 8) and y == 3:
			continue;

		tile = tiles.crop((xx, yy, xx + SIZE_X, yy + SIZE_Y))
		b = base.copy()
		HIGHLIGHT_OFFSET_X = 8
		HIGHLIGHT_OFFSET_Y = 3
		HIGHLIGHT_COLOUR = "#ff0000"
		draw = ImageDraw.Draw(b)		
		b.paste(tile, (4 + 8, 3 + 3))
		filename = "mahjong_%d_%d.png" % (y, x)
		filename_selected = "mahjong_selected_%d_%d.png" % (y, x)
		b.save(filename)
		draw.rectangle([(HIGHLIGHT_OFFSET_X - 1, HIGHLIGHT_OFFSET_Y - 1), (HIGHLIGHT_OFFSET_X + TILE_SIZE_X - 1 + 1, HIGHLIGHT_OFFSET_Y + TILE_SIZE_Y - 1 - 6 + 1)], outline=HIGHLIGHT_COLOUR)
		draw.rectangle([(HIGHLIGHT_OFFSET_X, HIGHLIGHT_OFFSET_Y), (HIGHLIGHT_OFFSET_X + TILE_SIZE_X - 1, HIGHLIGHT_OFFSET_Y + TILE_SIZE_Y - 1 - 6)], outline="#ff0000")
		draw.rectangle([(HIGHLIGHT_OFFSET_X + 1, HIGHLIGHT_OFFSET_Y + 1), (HIGHLIGHT_OFFSET_X + TILE_SIZE_X - 2, HIGHLIGHT_OFFSET_Y + TILE_SIZE_Y - 2 - 6)], outline="#ff0000")
		b.save(filename_selected)
		xx += SIZE_X + GAP_X 
		
	yy += SIZE_Y + GAP_Y
