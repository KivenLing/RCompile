package table;
/**
 * @author 王启航
 * @since 2018.01.06
 * 维护用户设置的URL，
 * 在图表调用时查询是否用户已经定义
 * 路径，在指定路径生成。
 */
public class UrlSelection {
		
	private String dict;
	
	private String filename;
	
	private static UrlSelection url = null;
	
	private UrlSelection() {
		super();
		this.dict = "";
		this.filename = "";
	}

	public static UrlSelection getInstance() {
		if (url == null) {
			url = new UrlSelection();
		}
		return url;
	}
	//判定是否可以使用本目录。
	public boolean isNull() {
		if (filename.isEmpty() || filename.equals("")) {
			return true;
		}
		if (dict.isEmpty() || dict.equals("")) {
			return true;
		}
		return false;
	}
	
	public String getUrl() {
		return dict + "/" + filename;
	}

	private String getDict() {
		return dict;
	}

	public void setDict(String dict) {
		this.dict = dict;
	}

	private String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
