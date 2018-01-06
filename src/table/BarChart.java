package table;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author 王启航
 * @since 2018.01.06
 * 图像的实现，此与PieChart，
 * SerChart一同实现三种图表的生成。
 */
public class BarChart {
	//私有变量，保存图表
	private JFreeChart barframe;
	//创建放入图标的数据，条状图中为CategoryDataset
	private DefaultCategoryDataset dataset;
	//x轴，y轴，标题名称
	private String Xline = " ";
	
	private String Yline = " ";
	
	private String title = " ";
	//保存生成图片的url，否则默认为Diagram/tempBar.jpg
	private String url = DrawDiagram.BARURL;
	
	public BarChart(ArrayList<BarData> barList) {
		this.dataset = new DefaultCategoryDataset();
		for (BarData data : barList) {
			this.dataset.addValue(data.getValue(), data.getName(), data.getName2());
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public void ChartCreate() {
		//创建放入图标的数据，条状图中为CategoryDataset
		CategoryDataset data = this.dataset;
		//利用第三方提供的图标工厂，产生条状图，参数依次为：标题、目录显示标签、数值轴标签、数据、图标方向(暂时全
		//默认为水垂直)、是否产生图例、是否生成工具、是否生成url链接
		barframe = ChartFactory.createBarChart3D(
				this.title,this.Xline,this.Yline,data,PlotOrientation.VERTICAL,true,false,false);
		
		//以下专注字体设置防止字体乱码。
		CategoryPlot plot = barframe.getCategoryPlot();//获取区域对象
		CategoryAxis domainAxis = plot.getDomainAxis();//底部列表
		domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 42));//底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 36));//垂直标题
		ValueAxis rangeAxis=plot.getRangeAxis();//获得柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,38));
        barframe.getLegend().setItemFont(new Font("黑体", Font.BOLD, 38));
        barframe.getTitle().setFont(new Font("宋体",Font.BOLD, 50));//标题字体设置
	}
	
	//将生成的图表返回
	public JFreeChart getJFreeChart(){  
	    return this.barframe;
	}

}
