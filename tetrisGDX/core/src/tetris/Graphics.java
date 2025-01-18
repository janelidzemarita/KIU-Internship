package tetris;


import java.awt.*;

public interface Graphics {
	void drawBoxAt(int x, int y, int size, int colorIndex);

	void drawBoxAt(int x, int y, int size);

	void fillRect(int i, int i1, int i2, int i3);

	void drawString(String s, int i, int i1);

	void setColor(Color color);

	void setFont(Font font);

}