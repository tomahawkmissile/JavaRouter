package board.data;

public class ViaExtent {

	String extent;
	Layer high;
	Layer low;
	
	public ViaExtent(String extent) {
		this.extent=extent;
		String[] split = extent.split("-");
		try {
			int a = Integer.parseInt(split[0]);
			int b = Integer.parseInt(split[1]);
			high=DataConverterFactory.getLayerFromInt(a);
			low=DataConverterFactory.getLayerFromInt(b);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	public String getExtend() {
		return extent;
	}
	public Layer getLowestLayer() {
		return low;
	}
	public Layer getHighestLayer() {
		return high;
	}
}
