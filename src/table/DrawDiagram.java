package table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.util.ArrayList;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import basicvar.CommonVar;
import basicvar.RChar;
import basicvar.RInteger;
import basicvar.RNumeric;
import collection.CollectionFactory;
import collection.RCollection;
import collection.RVector;
import exception.IlleagelDiagrameSyntaxException;
import function.FuncConstant;
import varmanage.VarManager;

/**
 * @author 王启航
 * @since 2018.01.06
 * 绘制图表仅需要从此类中调用功能。
 * create函数中实现jpg图像的生成和存储。
 */
public class DrawDiagram {
	
	public static final String BARCHART = "BarChart";
	
	public static final String PIECHART = "PieChart";
	
	public static final String SERCHART = "SerChart";
	
	public static final String BARURL = "Diagram/tempBar.jpg";
	
	public static final String PIEURL = "Diagram/tempPie.jpg";
	
	public static final String SERURL = "Diagram/tempSer.jpg";
	
	private VarManager varMan = null;
	
	private UrlSelection urlSec = null;
	
	public DrawDiagram() {
		this.varMan = VarManager.getInstance();
		this.urlSec = UrlSelection.getInstance();
	}
	
	//对外接口，筛选调用生成函数。
	public void creatChart(String functionName, String Content) throws IlleagelDiagrameSyntaxException {
		try{
			if (functionName.equals(FuncConstant.BARPLOT)){
				this.createBarChart(Content);
			} else if (functionName.equals(FuncConstant.PIE)) {
				this.createPieChart(Content);
			} else if (functionName.equals(FuncConstant.PLOT)) {
				this.createSerChart(Content);
			} else {
				//抛出异常
			}
		} catch (IOException e) {
			//本错误必须在此处理，因其是不涉及R代码语法及其逻辑的错误
		}
	}
	
	public void SetDict(String dict) {
		urlSec.setDict(dict);
	}
	
	public void SetFilename(String filename) {
		urlSec.setFilename(filename);
	}
	
	//检查是否参数合法，其下类似命名函数功能目的类似
	private boolean checkBarData(String numericVec, String charVec) {
		if (!this.varMan.getCollection(numericVec).getClassType().equals(CommonVar.NUMERIC)) {
			return false;
		} else if (!this.varMan.getCollection(charVec).getClassType().equals(CommonVar.CHARACTER)) {
			return false;
		}
		RVector<CommonVar> value = (RVector<CommonVar>) this.varMan.getCollection(numericVec);
		RVector<CommonVar> name = (RVector<CommonVar>) this.varMan.getCollection(charVec);
		if (value.sizeof() != name.sizeof()) {
			return false;
		}
		return true;
	}
	
	//拆分所得String，其下相似函数功能目的类似
	private BarInputString setBarInputString(String barStr) {
		BarInputString barInput = new BarInputString();
		String[] strs = barStr.split(",");
		int size = strs.length;
		if (size >= 2) {
			barInput.setValue(strs[0]);
			barInput.setName(strs[1]);
			barInput.setTitle(DrawDiagram.BARCHART);
		}
		if (size >= 3) {
			barInput.setTitle(strs[2]);
		}
		if (size >= 4) {
			barInput.setXline(strs[3]);
		}
		if (size == 5) {
			barInput.setYline(strs[4]);
		}
		return barInput;
	}
	
	private void createBarChart(String input) throws IOException, IlleagelDiagrameSyntaxException {
		//模拟传入的数据。再将函数分开为验证与创建部分。
		//根据R简单图来看，可能传入的首先是这两组必要数据，接下来又可选的X轴标题，Y轴标题，大标题依次排开。
		//这儿要拆分字符串并且检查数据合法。
		BarInputString barInput = setBarInputString(input);
		if(!checkBarData(barInput.getValue(), barInput.getName())) {
			throw new IlleagelDiagrameSyntaxException("barchart");
		}
		
		//这儿以下确确实实是创建表的部分，先获取数据
		RVector<CommonVar> barValue = (RVector<CommonVar>) this.varMan.getCollection(barInput.getValue());
		RVector<CommonVar> barName = (RVector<CommonVar>) this.varMan.getCollection(barInput.getName());
		int size = barValue.sizeof();
		ArrayList<BarData> barList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			RNumeric value = (RNumeric) barValue.getElement(i);
			RChar name = (RChar) barName.getElement(i);
			barList.add(new BarData(value.getContent(),name.getContent()));
		}

		//创建图表
		BarChart barchart = new BarChart(barList);
		barchart.setTitle(barInput.getTitle());
		barchart.setXline(barInput.getXline());
		barchart.setYline(barInput.getYline());
		if (!urlSec.isNull()) {
			barchart.setUrl(urlSec.getUrl());
		}
		barchart.ChartCreate();
		JFreeChart jfc = barchart.getJFreeChart();
		FileOutputStream chartJPG = new FileOutputStream(barchart.getUrl());
		ChartUtilities.writeChartAsJPEG(chartJPG, jfc, 1000, 750);
		DiagramImageView div = new DiagramImageView(barchart.getTitle(),barchart.getUrl());
		chartJPG.close();
	}
	
	private boolean checkPieData(String charVec, String numericVec) {
		if (!this.varMan.getCollection(numericVec).getClassType().equals(CommonVar.NUMERIC)) {
			return false;
		} else if (!this.varMan.getCollection(charVec).getClassType().equals(CommonVar.CHARACTER)) {
			return false;
		}
		RVector<CommonVar> value = (RVector<CommonVar>) this.varMan.getCollection(numericVec);
		RVector<CommonVar> name = (RVector<CommonVar>) this.varMan.getCollection(charVec);
		if (value.sizeof() != name.sizeof()) {
			return false;
		}
		return true;
	}
	
	private PieInputString SetPieInputString(String pieStr) {
		PieInputString pieInput = new PieInputString();
		String[] strs = pieStr.split(",");
		int size = strs.length;
		if (size >= 2) {
			pieInput.setValue(strs[0]);
			pieInput.setName(strs[1]);
			pieInput.setTitle(DrawDiagram.PIECHART);
		}
		if (size >= 3) {
			pieInput.setTitle(strs[2]);
		}
		return pieInput;
	}
	
	private void createPieChart(String input) throws IOException, IlleagelDiagrameSyntaxException {
		//创建数据，检查数据合法性。
		PieInputString pieInput = SetPieInputString(input);
		if (!checkPieData(pieInput.getName(), pieInput.getValue())) {
			throw new IlleagelDiagrameSyntaxException("piechart");
		}
		
		//获得图表数据
		RVector<CommonVar> pieName = (RVector<CommonVar>) varMan.getCollection(pieInput.getName());
		RVector<CommonVar> pieValue = (RVector<CommonVar>) varMan.getCollection(pieInput.getValue());
		int size = pieName.sizeof();
		ArrayList<PieData> pieList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			RChar name = (RChar) pieName.getElement(i);
			RNumeric value = (RNumeric) pieValue.getElement(i);
			pieList.add(new PieData(name.getContent(), value.getContent()));
		}
		
		//创建图表
		PieChart pieChart = new PieChart(pieList);
		pieChart.setTitle(pieInput.getTitle());
		if (!urlSec.isNull()) {
			pieChart.setUrl(urlSec.getUrl());
		}
		pieChart.ChartCreate();
		JFreeChart jfc = pieChart.getJFreeChart();
		FileOutputStream chartJPG = new FileOutputStream(pieChart.getUrl());
		ChartUtilities.writeChartAsJPEG(chartJPG, jfc, 1000, 750);
		DiagramImageView div = new DiagramImageView(pieChart.getTitle(), pieChart.getUrl());
		chartJPG.close();
	}
	
	private boolean checkSerData(ArrayList<String> numericVecList) {
		for (String numericVec : numericVecList) {
			if (!this.varMan.getCollection(numericVec).getClassType().equals(CommonVar.NUMERIC)) {
				return false;
			}
		}
		return true;
	}
	
	private SerInputString SetSerInputString(String serStr) {
		SerInputString serInput = new SerInputString();
		String[] strs = serStr.split(",");
		int size = strs.length;
		serInput.setTitle(strs[size - 1]);
		serInput.setYline(strs[size - 2]);
		serInput.setXline(strs[size - 3]);
		ArrayList<String> valueList = new ArrayList<>();
		for (int i = 0; i < size - 3; i++) {
			valueList.add(strs[i]);
		}
		serInput.setValue(valueList);
		return serInput;
	}
	
	private void createSerChart(String input) throws IOException, IlleagelDiagrameSyntaxException {
		
		//同样需要解析并且判断对错
		SerInputString serInput = SetSerInputString(input);
		ArrayList<String> valueList = serInput.getValue();
		if (!checkSerData(valueList)) {
			throw new IlleagelDiagrameSyntaxException("serchart");
		}
		
		//获取数据，折线图可以接受任意多的Numeric类型Vector进行生成，需要嵌套循环。
		ArrayList<ArrayList<SeriesData>> DataList = new ArrayList<>();
		for (String value : valueList) {
			RVector<CommonVar> valueVec = (RVector<CommonVar>) varMan.getCollection(value);
			int size = valueVec.sizeof();
			ArrayList<SeriesData> serList = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				RNumeric vau = (RNumeric) valueVec.getElement(i);
				serList.add(new SeriesData(vau.getContent(), i+1));
			}
			DataList.add(serList);
		}
		
		//创建
		SeriesChart serChart = new SeriesChart(DataList);
		serChart.setTitle(serInput.getTitle());
		serChart.setXline(serInput.getXline());
		serChart.setYline(serInput.getYline());
		if (!urlSec.isNull()) {
			serChart.setUrl(urlSec.getUrl());
		}
		serChart.ChartCreate();
		JFreeChart jfc = serChart.getJFreeChart();
		FileOutputStream chartJPG = new FileOutputStream(serChart.getUrl());
		ChartUtilities.writeChartAsJPEG(chartJPG, jfc, 1000, 750);
		DiagramImageView dv = new DiagramImageView(serChart.getTitle(), serChart.getUrl());
		chartJPG.close();
	}

}

/*
 * 用于抽象拆分所得字符串后条状图
 * 所应该得到的各个参数，
 * 以下两个类类似。
 */
class BarInputString {
	
	private String value;
	
	private String name;
	
	private String title;
	
	private String xline;
	
	private String yline;

	public BarInputString(String value, String name) {
		super();
		this.value = value;
		this.name = name;
		this.title = DrawDiagram.BARCHART;
		this.xline = " ";
		this.yline = " ";
	}

	public BarInputString() {
		super();
		this.title = DrawDiagram.BARCHART;
		this.xline = " ";
		this.yline = " ";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXline() {
		return xline;
	}

	public void setXline(String xline) {
		this.xline = xline;
	}

	public String getYline() {
		return yline;
	}

	public void setYline(String yline) {
		this.yline = yline;
	}

	@Override
	public String toString() {
		return "BarInputString [value=" + value + ", name=" + name + ", title=" + title + ", xline=" + xline
				+ ", yline=" + yline + "]";
	}
	
}

class PieInputString {
	
	private String value;
	
	private String name;
	
	private String title;

	public PieInputString() {
		super();
		this.title = DrawDiagram.PIECHART;
	}

	public PieInputString(String value, String name) {
		super();
		this.value = value;
		this.name = name;
		this.title = DrawDiagram.PIECHART;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "PieInputString [value=" + value + ", name=" + name + ", title=" + title + "]";
	}
	
}

class SerInputString {
	
	private ArrayList<String> value;
	
	private String title;
	
	private String xline;
	
	private String yline;

	public SerInputString() {
		super();
		this.value = new ArrayList<>();
		this.title = DrawDiagram.SERCHART;
		this.xline = " ";
		this.yline = " ";
	}

	public SerInputString(ArrayList<String> value) {
		super();
		this.value = value;
		this.title = DrawDiagram.SERCHART;
		this.xline = " ";
		this.yline = " ";
	}

	public ArrayList<String> getValue() {
		return value;
	}

	public void setValue(ArrayList<String> value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXline() {
		return xline;
	}

	public void setXline(String xline) {
		this.xline = xline;
	}

	public String getYline() {
		return yline;
	}

	public void setYline(String yline) {
		this.yline = yline;
	}

	@Override
	public String toString() {
		return "SerInputString [value=" + value + ", title=" + title + ", xline=" + xline + ", yline=" + yline + "]";
	}
	
}