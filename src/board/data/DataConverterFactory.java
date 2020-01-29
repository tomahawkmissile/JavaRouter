package board.data;

import main.Registry;

public class DataConverterFactory {

	public DataConverterFactory() {}
	
	public static Layer getLayerFromInt(int number) {
		Registry r = Registry.getByName(Registry.LAYERS);
		for(Object j:r.listAllKeys()) {
			if(j instanceof Layer) {
				Layer layer = (Layer) j;
				if(layer.getNumber()==number) {
					return layer;
				}
			}
		}
		return null;
	}
	public static Layer getLayerFromString(String name) {
		Registry r = Registry.getByName(Registry.LAYERS);
		for(Object j:r.listAllKeys()) {
			if(j instanceof Layer) {
				Layer layer = (Layer) j;
				if(layer.getName().equals(name)) {
					return layer;
				}
			}
		}
		return null;
	}
}
