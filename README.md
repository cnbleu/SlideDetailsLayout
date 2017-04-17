>高仿淘宝、京东商品详情页面的上拉加载图文详情功能。使用扩展ViewGroup实现，对事件冲突已经做了处理，可嵌套ListView、WebView等自由使用。

## 技术特点

1. 完全继承ViewGroup实现的最小功能；
2. 针对事件冲突、事件消耗进行处理；
3. 可嵌套ListView、ViewPager、WebView等；
4. 快速集成。

## 代码

先贴代码，见[Github](https://github.com/cnbleu/SlideDetailsLayout)。

## 效果图

![](http://7xifbq.com1.z0.glb.clouddn.com/Fp1xaC2l40QBC8OKgHJfPt5qtlLs)


## 快速使用

与一般的组件使用方式类似，直接在xml中导入即可，需要注意的是，`SlideDetailsLayout`仅获取子节点中的前两个View，其中第一个作为Front，即概略视图；第二个作为Behind，即图文详情页面。

此处仅列出关键代码，详细代码请参见demo。

1. 布局导入

	文件名称：activity_main.xml

		<?xml version="1.0" encoding="utf-8"?>
		<cn.bleu.widget.slidedetails.SlideDetailsLayout
		    android:id="@+id/slidedetails"
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    xmlns:app="http://schemas.android.com/apk/res-auto"
		    app:duration="500"
		    app:percent="0.4"
		    app:default_panel="front"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent">
		
		    <FrameLayout
		        android:id="@+id/slidedetails_front"
		        android:layout_width="match_parent"
		        android:layout_height="match_parent"/>
		
		    <WebView
		        android:id="@+id/slidedetails_behind"
		        android:layout_width="match_parent"
		        android:layout_height="match_parent"
		        android:background="#FF0"/>
		
		</cn.bleu.widget.slidedetails.SlideDetailsLayout>

	配置信息如下：

	1. duration：动画时长，默认为300ms；
	2. percent：切换的阈值百分比，如0.2表示滑动具体为屏幕高度的20%时切换；
	3. default_panel：默认展示的面板，仅接受两个enum值：front、behind。

	FrameLayout以及WebView可以切换为任何你想要的View类型，当然可能会存在事件冲突，关于事件冲突的解决参考下文。

2. 代码调用：

	`SlideDetailsLayout`支持代码动态调用smoothOpen()来开启第二个面板，smoothClose来关闭第二个面板，默认情况，面板是关闭状态。

3. 扩展：

	如果你嵌套的View与`SlideDetailsLayout`有事件冲突，你可以覆写`canChildScrollVertically(direction)`方法来进行拦截处理。direction为负数时表示向下滑动，反之表示向上滑动。

## 实现思路

一个最小的功能集应该包含以下三个方面：

1. 包含两个面板，且可以在上下滑动的时候切换；
2. 嵌套ListView及WebView时可以正常滑动（图文详情部分假设是通过WebView加载H5页面）；
3. 允许通过代码调用切换面板及切换事件通知。

### 上下滑动

1. 通过`onInterceptTouchEvent`进行事件拦截后，在`onTouchEvent`方法中对触摸信息做进一步处理可以实现竖直方向的滑动；

2. 如下图：
	![](http://7xifbq.com1.z0.glb.clouddn.com/Fjq_vDJgvi-7nFtRT8mDZRCCREOB)
	
	Front、Behind面板是收尾相连上下平铺的两个面板，Front面板的高度为Height，`Top`为Front面板的最顶端，也是坐标系中x轴所在的位置，实红线与虚红线之间的部分为屏幕区域。我们要在屏幕区域滑动两个面板只需要改变两个面板在y轴方向的位移（有正负方向）即可。
	
3. 使滑动生效
	
	我们知道，自定义布局中有非常重要的两个环节`onMeasure`(测量)和`onLayout`(布局)。测量决定了View的所占的大小，布局决定了View所处的位置。实现滑动的关键思路就在这里，我们在`onLayout`方法中根据通过onInterceptTouchEvent、onTouchEvent得到的滑动信息进行计算而得到布局的位置信息，并把这个位置信息设置到子View上面即可实现滑动。

4. 标尺

	标尺，即：offset，相对于top的位移。Front面板展示时，offset为0，Behind面板展示时，offset为`Height`。此后所有的计算都是相对于该标尺。

### 事件冲突处理

假设父View所在的方向为外，子View所在的方向为内，则：事件的拦截方向为从外向内，事件的消耗方向为从内向外。如果当前View不拦截事件的话，有一定机会可以消耗事件；如果当前View拦截事件的话，则子View原则上不能接受后续事件。我们根据具体的需要来拦截事件或者捡漏掉的事件消耗掉，就能处理事件的冲突了（实际上没有这么简单，以后再进行更详细的描述）。

### 面板切换及切换事件通知

刚才说到，滑动的标尺是Front相对于Top的移动，且所有的位移计算都是基于该标尺。那我们在切换面板时只需要知道对应的offset值即可。当然，更改完offset值之后不要忘记调用`requestLayout()`方法。




