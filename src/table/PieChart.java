package table;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author 王启航
 * @since 2018.01.06
 * 详见BarChart
 */
public class PieChart {
	//私有变量，保存饼状图
	private JFreeChart pieframe;
	//饼状图数据集，类型为DefaultPieDataset
	private DefaultPieDataset dataset;
	//标题
	private String title = " ";
	//保存生成图片的url，否则默认为Diagram/tempPie.jpg
	private String url = DrawDiagram.PIEURL;
	
	public PieChart(ArrayList<PieData> pieList) {
		this.dataset = new DefaultPieDataset();
		for (PieData data : pieList) {
			this.dataset.setValue(data.getName(), data.getValue());
		}
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void ChartCreate() {
		DefaultPieDataset data = this.dataset;
		//创建饼状图。参数分别为：标题、数据集、是否产生图例、是否生成工具、是否生成url链接
		pieframe = ChartFactory.createPieChart(
				this.title,data,true,false,false);
		
		
		PiePlot plot = (PiePlot) pieframe.getPlot();
		DecimalFormat dformat = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
		NumberFormat nformat = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
		//获得StandardPieSectionLabelGenerator对象
		StandardPieSectionLabelGenerator spGenertor = 
				new StandardPieSectionLabelGenerator("{0}  {2}", nformat, dformat);
		plot.setLabelGenerator(spGenertor);//设置饼图显示百分比
		
		//无数据时显示内容
		plot.setNoDataMessage("无数据显示");
		plot.setCircular(false);
		plot.setLabelGap(0.02D);
		
		plot.setIgnoreNullValues(true);//设置不显示空值
		plot.setIgnoreZeroValues(true);//设置不显示负值
		pieframe.getTitle().setFont(new Font("宋体",Font.BOLD, 50));
		
		PiePlot pieplot = (PiePlot) pieframe.getPlot();
		pieplot.setLabelFont(new Font("黑体",Font.BOLD,38));
		pieframe.getLegend().setItemFont(new Font("黑体", Font.BOLD, 38));
		
	}
	
	public JFreeChart getJFreeChart(){  
	    return this.pieframe;
	}
	
}
