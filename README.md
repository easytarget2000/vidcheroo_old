vidcheroo
=========

VJ tool: Rapid, beat-synched switcher for VLC-player

Made by Michel Sievers

=========

Uses vlcj: https://github.com/caprica/vlcj
Several instances of VLC players with media files playing are stacked on top of each other.
The interval at which their visibility is toggled depends on a musical tempo that can be controlled on the go.
It takes all files that are in a given folder and hands them to the players in a randomised order.

=========

This is still highly experimental.
Fullscreen mode, screen-size changes and dual monitor support are still wonky.
The optional amount of players that are active at one point in time needs tweaking.
There is no check if a file can be played by VLC or if it makes sense to use the file for this purpose.
