/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **/
package bropals.lib.simplegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A GUI element that displays some text. When word wrap is enabled, the 
 * GuiText will attempt to draw all text inside of its box. If there is not
 * enough space, then it will continue drawing at the width of the GuiText.
 * 
 * @author Jonathon
 */
public class GuiText extends GuiElement {

    private String text;
    private Font font;
    private Color textColor;
    private int padding = 0;
    private BufferedImage backgroundImage = null;
    private boolean wordWrap;
    private String[] lines;
    
    /**
     * Creates a GuiText object to display text. Word wrap mode is on
     * by default; default color is Color.BLACK.
     * @param text the text to display
     * @param x the top-left corner's X position of the GuiText's box
     * @param y the top-left corner's Y position of the GuiText's box
     * @param w the width of the GuiText box
     * @param h the height of the GuiText
     */
    public GuiText(String text, int x, int y, int w, int h) {
        super(x, y, w, h);
        setText(text);
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        wordWrap = true;
        textColor = Color.BLACK;
    }
    
    /**
     * Creates a GuiText object to display text. Word wrap mode is on
     * by default; default color is Color.BLACK.
     * @param text the text to display
     * @param x the top-left corner's X position of the GuiText's box
     * @param y the top-left corner's Y position of the GuiText's box
     * @param w the width of the GuiText box
     * @param h the height of the GuiText
     * @param font the font that this GuiText will use
     */
    public GuiText(String text, int x, int y, int w, int h, Font font) {
        super(x, y, w, h);
        setText(text);
        this.font=font;
        wordWrap = true;
        textColor = Color.BLACK;
    }
    
    /**
     * Creates a GuiText object to display text. Word wrap mode is on
     * by default; default color is Color.BLACK.
     * @param text the text to display
     * @param x the top-left corner's X position of the GuiText's box
     * @param y the top-left corner's Y position of the GuiText's box
     * @param w the width of the GuiText box
     * @param h the height of the GuiText
     * @param wordWrap the initial word wrap state of this GuiText
     */
    public GuiText(String text, int x, int y, int w, int h, boolean wordWrap) {
        super(x, y, w, h);
        setText(text);
        this.font=font;
        this.wordWrap = wordWrap;
        textColor = Color.BLACK;
    }
    
    /**
     * Creates a GuiText object to display text. Word wrap mode is on
     * by default; default color is Color.BLACK.
     * @param text the text to display
     * @param x the top-left corner's X position of the GuiText's box
     * @param y the top-left corner's Y position of the GuiText's box
     * @param w the width of the GuiText box
     * @param h the height of the GuiText
     * @param font the font that this GuiText will use
     * @param textColor the text color that this GuiText will use
     */
    public GuiText(String text, int x, int y, int w, int h, Font font, Color textColor) {
        super(x, y, w, h);
        setText(text);
        this.font=font;
        wordWrap = true;
        this.textColor = textColor;
    }
    
    /**
     * Sets the color of this GuiText's text.
     * @param color the color of this GuiText's text.
     */
    public void setTextColor(Color color) {
        this.textColor=color;
    }
    
    /**
     * Gets the color of this GuiText's text.
     * @return the color of this GuiText's text.
     */
    public Color getTextColor() {
        return textColor;
    }
    
    /**
     * Gets the word wrap preference of this GuiText. Returns true if it
     * is wrapping its text, false if it is not.
     * @return the word wrap preference.
     */
    public boolean isWordWrapping() {
        return wordWrap;
    }
    
    /**
     * Sets the word wrap state of this GuiText.
     * @param wordWrap the word wrap state.
     */
    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;
        lines = null;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        lines = null;
    }    

    /**
     * Gets the text padding. This is similar to the CSS text padding..
     * @return the text padding
     */
    public int getPadding() {
        return padding;
    }

    /**
     * Sets the text padding. This is similar to the CSS text padding..
     * @param padding the text padding
     */
    public void setPadding(int padding) {
        this.padding = padding;
        lines = null;
    }

    /**
     * Gets the background image.
     * @return the background image.
     */
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Sets the background image.
     * @param backgroundImage the background image
     */
    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
            
    private void splitIntoLines(FontMetrics metrics) {
        if (this.wordWrap) {
            ArrayList<String> l = new ArrayList();
            String[] splitText = this.text.split(Pattern.quote(" "));
            /* Split into multiple lines */
            int lineWidth = 0;
            int wordLength;
            int spaceWidth = metrics.charWidth(' ');
            String currentLine = "";
            for (int word = 0; word < splitText.length; word++) {
                wordLength = metrics.stringWidth(splitText[word]);
                if ( wordLength + lineWidth > getWidth()-(getPadding()*2) ) {
                    l.add(currentLine);
                    lineWidth = (spaceWidth + wordLength);
                    currentLine = (splitText[word] + ' ');
                } else {
                    lineWidth += (spaceWidth + wordLength);
                    currentLine += (splitText[word] + ' ');
                }
            }
            l.add(currentLine);
            lines = (String[])l.toArray(new String[0]);
        }
    }
    
    /**
     * Sets the text for this GuiText to display.
     * @param text the text to display.
     */
    public void setText(String text) {
        this.text=text;
        lines = null;
    }
    
    /**
     * Gets the text that this GuiText is displaying.
     * @return the text that this GuiText is displaying.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Gets the Font that this GuiText is using
     * @return the font that this GuiText is using
     */
    public Font getFont() {
        return font;
    }
    
    /**
     * Sets the Font that this GuiText uses.
     * @param font the font that this GuiText uses.
     */
    public void setFont(Font font) {
        this.font=font;
    }

    @Override
    public void render(Object graphicsObject) {
        Graphics g = (Graphics)graphicsObject;
        if (isEnabled()) {
            g.setFont(font);
            g.setColor(textColor);
            FontMetrics fm = g.getFontMetrics();
            int xLoc = getX() + getPadding();
            int yLoc = getY() + fm.getHeight() + getPadding();
            g.drawImage(backgroundImage, getX(), getY(), getWidth(), getHeight(), null);
            if (wordWrap) {
                if (lines == null) {
                    splitIntoLines(fm);
                }
                for (String line : lines) {
                    g.drawString(line, xLoc, yLoc);
                    yLoc += fm.getHeight();
                }
            } else {
                g.drawString(getText(), xLoc, yLoc);
            }
        }
    }
}
