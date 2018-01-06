package table;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
/**
 * @author 王启航
 * @since 2018.01.06
 * 详见BarChart
 */
public class SeriesChart {
	//私有变量，保存折线图
	private JFreeChart serframe;
	//折线图数据集，类型为TimeSeriesCollection
	private TimeSeriesCollection dataset;
	//x轴，y轴，标题名称
	private String Xline = " ";
	
	private String Yline = " ";
	
	private String title = " ";
	//保存生成图片的url，否则默认为Diagram/tempSer.jpg
	private String url = DrawDiagram.SERURL;
	
	public SeriesChart(ArrayList<ArrayList<SeriesData>> DataList) {
		this.dataset = new TimeSeriesCollection();
		int i = 1;
		for (ArrayList<SeriesData> alist : DataList) {
			TimeSeries timeseries = new TimeSeries(i+"",
					org.jfree.data.time.Month.class);
			for (SeriesData sdata : alist) {
				timeseries.add(new Month((int)sdata.getRank(), (int)sdata.getDefaultrank()), sdata.getValue());
			}
			this.dataset.addSeries(timeseries);
			i++;
		}
	}
	
	public String getXline() {
		return Xline;
	}

	public void setXline(String xline) {
		Xline = xline;
	}

	public String getYline() {
		return Yline;
	}

	public void setYline(String yline) {
		Yline = yline;
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
		TimeSeriesCollection data = this.dataset;
		//创建折线图。参数分别为：标题、目录显示标签、数值轴标签、数据集、是否产生图例、是否生成工具、是否生成url链接
		serframe = ChartFactory.createTimeSeriesChart(this.title, this.Xline, this.Yline
				,data, true, true, true);
		
		XYPlot xyplot = (XYPlot) serframe.getPlot();
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		dateaxis.setDateFormatOverride(new SimpleDateFormat("MM"));
		
		dateaxis.setLabelFont(new Font("宋体", Font.BOLD, 42));//水平底部标题
		dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 36));//垂直标题
		ValueAxis rangeAxis=xyplot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 38));
		serframe.getLegend().setItemFont(new Font("宋体", Font.BOLD, 38));
		serframe.getTitle().setFont(new Font("宋体", Font.BOLD, 50));//设置标题字体
		
	}
	
	public JFreeChart getJFreeChart(){  
	    return this.serframe;
	}
	
}
