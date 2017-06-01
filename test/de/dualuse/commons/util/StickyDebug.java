package de.dualuse.commons.util;

import java.awt.Rectangle;

import javax.swing.JFrame;

import de.dualuse.util.Sticky;
import de.dualuse.util.Sticky.Getter;

public class StickyDebug {
	public static void main(String[] args) {
		
		final JFrame f = new JFrame();
		
		f.setBounds(new Rectangle(100,100,400,400));
		f.setBounds(Sticky.property(new Getter<Rectangle>() {
			@Override
			public Rectangle get() {
				System.out.println("GET "+f.getBounds());
				return f.getBounds();
			}
		}));
		
		f.setVisible(true);
		
		
	}
}
