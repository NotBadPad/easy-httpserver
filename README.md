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
	<li>支持动态请求(后缀为do，请求路径restful风格)</li>
	<li>支持静态资源请求(后缀为对应资源类型后缀，可在配置文件中拓展)</li>
	<li>可通实现Controller接口处理动态请求，并支持注解方式进行映射</li>
	<li>动态请求可返回页面、json数据，并支持跳转</li>
	<li>整合Velocity，返回页面可通过Velocity语法渲染页面</li>
	<li>支持HTTP表单方式的文件上传(input:file)</li>
</ul>

如何使用
-----------------
easy-httpserver项目内也有测试代码，可自行研究。这里介绍test-httpserver示例项目的使用，其中已经实现了一些例子，可参照这些例子实现。
<ul>
<li>1、clone easy-httpserver到本地，导入eclipse（已安装maven，编码utf-8）</li>
<li>2、clone test-httpserver（示例项目）到本地，导入eclipse</li>
<li>3、mvn install&nbsp;easy-httpserver（将jar导入test-httpserver，若配置maven则自动导），以下修改均在test-httpserver中</li>
<li>4、按照注释修改test-httpserver配置文件<br />
<div class="cnblogs_code">
<pre><span style="color: #800000;">#包前缀
PACKAGE_PREFIX=com.gj.web

#view页面根路径（相对classes/view文件夹，项目中在src/main/view下）
VIEW_BASE_PATH=page/

#静态资源路劲（相对classes/view文件夹，项目中在src/main/view下）
STATIC_RESOURCE_PATH=static/

#端口
httpserver.port=8888

#controller包路径，配置后可通过annocation直接配置
controller.package=com.gj.web.controller

#url与controller类对应关系，与上边controller.package效果一致，二选一，格式："url"+url路径+&ldquo;=&rdquo;+对应controller路径
#url/list=com.gj.web.controller.MyController</span></pre>
</div>
</li>
<li>5、编写Controller类，需实现Controller接口，并返回ResultInfo对象（详情见示例）。使用注解配置映射（如：http://127.0.0.1/test/path/show.do,配置controller为/test/path/，方法名为show）</li>
<li>5、在配置文件指定文件夹(page:velocity文件，static:静态页面和资源)下编写对应页面</li>
<li>6、mvn&nbsp;exec:java -Dexec.mainClass="org.eh.core.http.EHServer" &nbsp;启动服务</li>
<li>7、浏览器访问http://127.0.0.1:8888/test/path/show.do，即可看到结果</li>
</ul>

项目结构
-----------------
项目采用eclipse开发，并使用maven构建。
<p><strong>~src/main/java</strong><br />&nbsp; <strong>--org.eh.core</strong><br />&nbsp; &nbsp;　　 <strong>--annotation</strong><br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　AnnocationHandler.java &nbsp; 注解处理类<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　Controller.java &nbsp; &nbsp;Controller注解<br />　　　　　　&nbsp;RequestMapping &nbsp; &nbsp;请求方法注解，用来标注Controller中的处理方法<br />&nbsp; &nbsp;<strong> 　　--common</strong><br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　Constants.java &nbsp; &nbsp;常量类，包括系统常量和配置文件对应信息<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　ReturnType.java &nbsp; &nbsp;枚举，返回类型<br />&nbsp; &nbsp; 　　<strong>--http</strong><br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　EHHttpHandler.java &nbsp; &nbsp;httpserver请求核心处理类，完成请求的接收、处理、返回<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　EHServer.java &nbsp; &nbsp;项目启动类，完成服务器启动<br />　　　　　　&nbsp;ApplicationContext.java &nbsp;&nbsp;全局数据和会话相关数据,单例<br />　　　　　　&nbsp;HttpSession.java &nbsp;&nbsp;session会话<br />&nbsp; &nbsp; 　<strong>　--model</strong><br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　FileInfo.java &nbsp; &nbsp;上传文件信息封装<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　ResultInfo.java &nbsp; &nbsp;Controller返回结果<br />&nbsp; &nbsp;　　<strong> --util</strong><br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　FileUploadContentAnalysis.java &nbsp; &nbsp;上传请求解析类，从中提取表单中的域值和文件信息<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　FileUtil.java &nbsp; 文件工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　IOUtil.java &nbsp; &nbsp;IO工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　PropertyUtil.java &nbsp; &nbsp;配置文件工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　StringUtil.java &nbsp; &nbsp;字符串工具类<br />&nbsp; &nbsp; &nbsp; &nbsp;　　　　VelocityUtil.java &nbsp; &nbsp;Velocity工具类<br />&nbsp; &nbsp;　　 <strong>--web</strong><br />&nbsp; &nbsp; &nbsp; 　　<strong>&nbsp;--controller</strong><br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 　　　　Controller.java &nbsp; &nbsp;Controller接口，实现类必须实现process<br />&nbsp; &nbsp; &nbsp; &nbsp;　　<strong>--view</strong><br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 　　　　ViewHandler.java &nbsp; &nbsp;View处理类，完成页面文件渲染<br /><strong>~src/main/resources</strong><br />　　 &nbsp;&nbsp;velocity.properties<br /><em id="__mceDel">　　 &nbsp;&nbsp;</em>web.properties</p>

      

