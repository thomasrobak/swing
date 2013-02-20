package at.robak.utils;

import java.net.URL;

import javax.swing.ImageIcon;
public class ImageLoader {

	public static ImagePane loadImagePane(String image_name){
		return new ImagePane(loadImageIcon(image_name));
	}

	public static ImageIcon loadImageIcon(String image_name){
		URL url = null;//ImageLoader.class.getResource(Defaults.getInstance().getImageUrl()+image_name);
		if(url==null){
			return null;
		}
		return new ImageIcon(url);
	}

}
