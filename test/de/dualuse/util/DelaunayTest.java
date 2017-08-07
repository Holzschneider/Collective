package de.dualuse.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

import de.dualuse.util.geometry.Delaunay;
import de.dualuse.util.geometry.Delaunay.Edge;
import de.dualuse.util.geometry.Delaunay.TriangleVisitor;
import de.dualuse.util.geometry.Delaunay.Vertex;

public class DelaunayTest {
	public static void main(String[] args) {
		Random rng = new Random(1337);
		
		Edge<Integer> e = new Delaunay.Edge<Integer>(-10000,-10000,20000,20000);
		
		int n = 1000;
		for (int i=0;i<n;i++) {
			int x = rng.nextInt(800);
			int y = rng.nextInt(800);
			
			e = e.put(x, y, i);
		}

		
		final Edge<Integer> d = e;
		
		
		JFrame f = new JFrame();
		
		
		f.setContentPane(new JComponent() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				
				HashSet<Edge<Integer>> done = new HashSet<Edge<Integer>>();
				Deque<Edge<Integer>> todo = new ArrayDeque<Edge<Integer>>();
				
				todo.add(d);
				
				final Path2D.Double p = new Path2D.Double();
				Delaunay.triangles(new TriangleVisitor<Integer>() {
					@Override
					public void meet(Vertex<Integer> a, Vertex<Integer> b, Vertex<Integer> c) {
						p.moveTo(a.getX(), a.getY());
						p.lineTo(b.getX(), b.getY());
						p.lineTo(c.getX(), c.getY());
						p.lineTo(a.getX(), a.getY());
					}
				},todo,done);
				
				
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
				((Graphics2D)g).setStroke(new BasicStroke(0.33f));
				((Graphics2D)g).draw(p);
				
			}
			
		});
		
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(1500, 100, 800, 800);
		f.setVisible(true);
		
		
	}
}
