easy-httpserver
=================

简介
-----------------
一个java实现的简单的web服务器，主要目的是为了理解web服务器的工作原理。http请求的接收和返回使用JDK自带的HttpServer，请求处理和页面解析是自己实现的。目前已经能够支持web服务器一些基本的功能。

功能
-----------------
目前项目已经能够完成一个动态网站的处理，并且采用类似jetty的嵌入启动方式，网站项目只需实现Controller和View页面并引入该项目jar包即可。
已完成主要功能如下：
<ul>
	<li>1、支持处理动态请求(后缀为do，请求路径restful风格)</li>
	<li>2、支持静态资源请求(后缀为对应资源类型后缀，可在配置文件中拓展)</li>
	<li>3、可通实现Controller接口处理动态请求，并支持配置文件和注解两种方式进行映射</li>
	<li>4、动态请求可返回页面、json数据，并支持跳转</li>
	<li>5、整合Velocity，返回页面可通过Velocity语法渲染页面</li>
	<li>6、支持HTTP表单方式的文件上传(input:file)</li>
</ul>

项目结构
-----------------
项目采用eclipse开发，并使用maven构建。
<p>~src/main/java<br />&nbsp; --org.eh.core<br />&nbsp; &nbsp; --annotation<br />&nbsp; &nbsp; &nbsp; &nbsp;AnnocationHandler.java &nbsp; 注解处理类<br />&nbsp; &nbsp; &nbsp; &nbsp;Controller.java &nbsp; &nbsp;Controller注解<br />&nbsp; &nbsp; --common<br />&nbsp; &nbsp; &nbsp; &nbsp;Constants.java &nbsp; &nbsp;常量类，包括系统常量和配置文件对应信息<br />&nbsp; &nbsp; &nbsp; &nbsp;ReturnType.java &nbsp; &nbsp;枚举，返回类型<br />&nbsp; &nbsp; --http<br />&nbsp; &nbsp; &nbsp; &nbsp;EHHttpHandler.java &nbsp; &nbsp;httpserver请求核心处理类，完成请求的接收、处理、返回<br />&nbsp; &nbsp; &nbsp; &nbsp;EHServer.java &nbsp; &nbsp;项目启动类，完成服务器启动<br />&nbsp; &nbsp; --model<br />&nbsp; &nbsp; &nbsp; &nbsp;FileInfo.java &nbsp; &nbsp;上传文件信息封装<br />&nbsp; &nbsp; &nbsp; &nbsp;ResultInfo.java &nbsp; &nbsp;Controller返回结果<br />&nbsp; &nbsp; --util<br />&nbsp; &nbsp; &nbsp; &nbsp;FileUploadContentAnalysis.java &nbsp; &nbsp;上传请求解析类，从中提取表单中的域值和文件信息<br />&nbsp; &nbsp; &nbsp; &nbsp;FileUtil.java &nbsp; 文件工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;IOUtil.java &nbsp; &nbsp;IO工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;PropertyUtil.java &nbsp; &nbsp;配置文件工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;StringUtil.java &nbsp; &nbsp;字符串工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;VelocityUtil.java &nbsp; &nbsp;Velocity工具类<br />&nbsp; &nbsp; --web<br />&nbsp; &nbsp; &nbsp; &nbsp;--controller<br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Controller.java &nbsp; &nbsp;Controller接口，实现类必须实现process<br />&nbsp; &nbsp; &nbsp; &nbsp;--view<br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ViewHandler.java &nbsp; &nbsp;View处理类，完成页面文件渲染</p>
<p>&nbsp;</p>
      

