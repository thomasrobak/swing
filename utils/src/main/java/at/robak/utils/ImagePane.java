/*
 * Created on Aug 18, 2004 for salestool
 * by Thomas Robak
 */
package at.robak.utils;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ImagePane extends JPanel{
	ImageIcon image = null;
	private int img_width;
	private int img_height;
	
	public ImagePane(ImageIcon _image){
		super();
		this.image=_image;
		this.setOpaque(false);
	}	
	public ImagePane(URL url){
		this(new ImageIcon(url));
	}
	public ImagePane(String imgpath){
		this(new ImageIcon(imgpath));
	}
	public void paint(Graphics g) {
		if(g!=null){
			if(image!=null)				
				g.drawImage(image.getImage(),0,0,this.img_width,this.img_height,null);
			super.paint( g );
		}
	}
	public void setSize(int height){
		if(image!=null){
			this.img_height=height;
			this.img_width=(this.image.getIconWidth()*height)/this.image.getIconHeight();
		}
		super.setSize(this.img_width,this.img_height);
	}
        
        public void setSize(int width,int height){
            this.img_width=width;
            this.img_height=height;
            super.setSize(width,height);
            System.out.println("Logo fehlt !!!");
        }
                
}
