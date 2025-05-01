# Map Generation System
The general concept is to have a starting point **a** and an ending point **b** on a dot-grid.
We then compute the taxicab distance between them, and traverse the
map by generating connections between the dots, deciding to branch off into two directions in some cases (randomly determined).
We can then take the path generated and compute the barriers to make the map.
I found out that we can do that by generating random branches & drawing
the paths that each of them take, making sure to only draw overlap once.