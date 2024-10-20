# Nonogram
A program used to create and solve nonogram puzzles.
- Author: Tate Maguire
- Developed: June 2023
- Updated to work in JDK 23 and for distribution
- [https://github.com/tatemaguire/Nonogram](url)

# Installation / Running (instructions for mac and maybe windows idk)
From GitHub, you can click on the green code button and choose download as zip. Then, unpack the zip, which you can usually do by double-clicking on it.

To run this program, you can right click on the 'Nonogram-main' folder and choose 'New Terminal at Folder'. Alternatively, you can open terminal and navigate to the folder. Then, run one of the following commands:

`java Driver.java -c` for creative mode

`java Driver.java -s` for survival mode

# Survival vs. Creative
You must choose one of these modes when starting up the program.

### Survival
This mode is for solving puzzles. Playing the game itself. A level select window will show up, in which you can choose a puzzle to solve based off of the grid dimensions. Once you complete a level, its name will be revealed, and you can choose a new level to play.

### Creative
This mode is for making puzzles. In the file menu, you can create a new grid in any size, import puzzles from the puzzle folder, or export them to the puzzle folder. To export a puzzle, all you need to do is draw your pattern on the grid, and then export it with any name (a '.txt' file will be created).

# How To Use
Once in the program, you can use left click to fill in a square, and right click to mark it as red. Red tiles are used to mark a tile as empty, but don't actually affect the puzzle input. Left/right click filled tiles to reset them to white. You can also use the tools in the toolbar at the top of the window, and the keyboard shortcuts that accompany them.

# History
I made this program for my AP Computer Science class. After the AP Test, my teacher gave us a freeform final project to do anything related to computer science. Some students made poems about computer science, some made paintings, but most of us made programs. So, for the last month of school, I spent time in class and a lot of time out of class making this program. It was a passion project that I worked on during weekends, before going to bed every night, and in between classes if I had the chance.

# 2024 Update
In October 2024 I updated this program to be accessible outside of the IDE I developed it in (BlueJ). I also replaced some deprecated KeyEvent code, and fixed some issues with file reading and writing.
