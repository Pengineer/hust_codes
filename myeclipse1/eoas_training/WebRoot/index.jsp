<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->

<head>
    <!-- Basic Page Needs
	================================================== -->
    <meta charset="utf-8">
    <title>EOAS</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Mobile Specific Metas
	================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- CSS
	================================================== -->
    <link rel="stylesheet" href="css/portal/style.css">
    <link rel="stylesheet" type="text/css" href="css/portal/settings.css" media="screen" />
    <!-- JSs
	================================================== -->
    <script src="javascript/portal/jquery-1.8.2.min.js" type="text/javascript"></script>
    <!-- jQuery -->
    <script src="javascript/portal/jquery.easing.1.3.js" type="text/javascript"></script>
    <!-- jQuery easing -->
    <script src="javascript/portal/modernizr.custom.js" type="text/javascript"></script>
    <!-- Modernizr -->
    <script src="javascript/portal/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
    <!-- tabs, toggles, accordion -->
    <script src="javascript/portal/custom.js" type="text/javascript"></script>
    <!-- jQuery initialization -->
    <!-- Responsive Menu -->
    <script src="javascript/portal/jquery.meanmenu.js"></script>
    <script>
    jQuery(document).ready(function() {
        jQuery('header nav').meanmenu();
    });
    </script>
    <!-- Revolution Slider -->
    <script src="javascript/portal/jquery.themepunch.plugins.min.js"></script>
    <script src="javascript/portal/jquery.themepunch.revolution.min.js"></script>
    <script src="javascript/portal/revolution-slider-options.js"></script>
    
    <!-- Tooltip -->
    <!-- Favicons
	================================================== -->
    <link rel="shortcut icon" href="image/favicon.ico">
</head>

<body>
    <!-- Primary Page Layout
	================================================== -->
    <header class="header">
        <div class="container">
            <div class="logos columns">
                <h1 class="logo"><a href="/">
                	<span>EOAS</span>
                </a></h1>
            </div>
            <!-- logo -->
            <div class="twelve columns">
                <nav class="main_menu">
                    <ul>
                        <li class="current_page_item">
                            <a href="/">
									首页
									<div class="sub">
										Get Started
									</div>
								</a>
                            <ul>
                                <li><a href="index.html">解决方案</a></li>
                                <li><a href="#">解决方案</a></li>
					            <li><a href="#">成功案例</a></li>
					            <li><a href="#">服务支持</a></li>
					            <li><a href="#">合作发展</a></li>
					            <li><a href="#">关于我们</a></li>

                            </ul>
                        </li>
                        <li>
                            <a href="#">
									解决方案
									<div class="sub">
										Solutions
									</div>
								</a>
                            <ul>
                               <li><a href="#">教育事业</a></li>
                               <li><a href="#">金融行业</a></li>
                               <li><a href="#">行政办公</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
									成功案例
									<div class="sub">
										 Examples
									</div>
								</a>
                            <ul>
                                <li><a href="#">案例1</a></li>
                                <li><a href="#">案例2</a></li>
                                <li><a href="#">案例3</a></li>
                                <li><a href="#">案例4</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
									服务支持
									<div class="sub">
										Supports
									</div>
								</a>
                            <ul>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
									合作发展
									<div class="sub">
										Collaboration
									</div>
								</a>
                            <ul>
                                
                            </ul>
                        </li>
                        <li>
                            <a href="#">
									关于我们
									<div class="sub">
										About Us
									</div>
								</a>
                                <ul >
                                <li><a href="#" title="公司简介">公司简介</a></li>
                                <li><a href="#" title="公司证照">公司证照</a></li>
                                <li><a href="#" title="代理合作">代理合作</a></li>
                                <li><a href="#" title="招贤纳士">招贤纳士</a></li>
                                <li><a href="#" title="联系我们">联系我们</a></li>
                                <li><a href="#" title="意见反馈">意见反馈</a></li>

                              </ul>
                        </li>
                    </ul>
                </nav>
                <!-- navigation -->
            </div>
            <div class="clearfix"></div>
        </div>
    </header>
    <!-- header -->
    <!-- REVOLUTION SLIDER
				============================================= -->
    <div class="fullwidthbanner-container top-shadow">
        <div class="fullwidthbanner">
            <ul>
                <!-- Slide 1 -->
                <li data-transition="boxfade" data-slotamount="2" data-masterspeed="300">
                    <!-- Main Image -->
                    <img src="image/portal/slide1.jpg" alt="">
                    <!-- End Main Image -->
                </li>
                <!-- End Slide 1 -->
                <!-- Slide 2 -->
                <li data-transition="boxfade" data-slotamount="3" data-masterspeed="300">
                    <!-- Main Image -->
                    <img src="image/portal/slide2.jpg" alt="">
                    <!-- End Main Image -->
                </li>
                <!-- End Slide 2 -->
                <!-- Slide 3 -->
                <li data-transition="boxfade" data-slotamount="4" data-masterspeed="300">
                    <!-- Main Image -->
                    <img src="image/portal/slide3.jpg" alt="">
                    <!-- End Main Image -->
                </li>
                <!-- End Slide 3 -->
                <!-- Slide 4 -->
                <li data-transition="boxfade" data-slotamount="5" data-masterspeed="300">
                    <!-- Main Image -->
                    <img src="image/portal/slide4.jpg" alt="">
                    <!-- End Main Image -->
                </li>
                <!-- End Slide 4-->
            </ul>
        </div>
    </div>
    <!-- END REVOLUTION SLIDER
				============================================= -->
    <div class="container">
        <div class="one-third column">
            <!-- Icon Box -->
            <div class="icon-box medium">
                <i class="fa fa-css3 medium colored"></i>
                <h4><a href="#" class="dark-link">安全</a></h4>
                <p>
                    采用最成熟和应用最广泛的技术平台，支持身份认证技术、安全加密技术；数据在传输过程和数据库中采用高加密技术，保证数据的安全性。分不同的角色控制信息数据，采用横向和纵向结合的矩阵权限控制模式，保证企业的各种信息安全。
                </p>
            </div>
            <!-- End Icon Box -->
        </div>
        <div class="one-third column">
            <!-- Icon Box -->
            <div class="icon-box medium">
                <i class="fa fa-magic medium colored"></i>
                <h4><a href="#" class="dark-link">灵活</a></h4>
                <p>
                    在日常工作中，不可避免地需要进行机构及人员的调整，OA系统能提供充分的变更与扩展能力，适用机构及人员的调整。OA系统还具有图形化工作流定义工具，系统管理员可在浏览器环境下任意调整或定义工作流程。系统具有灵活的信息发布系统，用户可根据需要定制发布需要的新闻、通知。
                </p>
            </div>
            <!-- End Icon Box -->
        </div>
        <div class="one-third column">
            <!-- Icon Box -->
            <div class="icon-box medium">
                <i class="fa fa-bullhorn medium colored"></i>
                <h4><a href="#" class="dark-link">高效</a></h4>
                <p>
                    系统提供对各类事务处理的高效性。使对大容量数据的查询和更新等操作也在较短的时间内迅速完成。对于大数据量的处理，也能高效地完成。
                </p>
            </div>
            <!-- End Icon Box -->
        </div>
        <div class="clearfix"></div>
        <div class="separator"></div>
        <div class="sixteen columns">
            <h4 class="headline">成功案例</h4>
        </div>
        <div class="one-third column identity photography tehnology">
            <div class="work">
                <a href="#" class="work-image">
                    <img src="image/portal/smiling.jpg" alt="">
                    <div class="link-overlay fa fa-chevron-right"></div>
                </a>
                <a href="#" class="work-name">案例1</a>
                <div class="tags">
                    Tecent Inc.
                </div>
            </div>
        </div>
        <div class="one-third column photography webdesign">
            <div class="work">
                <a href="#" data-gal="prettyPhoto" class="work-image">
                    <img src="image/portal/couple.jpg" alt="">
                    <div class="link-overlay fa fa-chevron-right"></div>
                </a>
                <a href="#" class="work-name">案例2</a>
                <div class="tags">
                    Baidu Inc.
                </div>
            </div>
        </div>
        <div class="one-third column identity">
            <div class="work">
                <a href="#" data-gal="prettyPhoto" class="work-image">
                    <img src="image/portal/beach.jpg" alt="">
                    <div class="link-overlay fa fa-chevron-right"></div>
                </a>
                <a href="#" class="work-name">案例3</a>
                <div class="tags">
                    Alibaba Inc.
                </div>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="separator"></div>
        <div class="eight columns">
            <h4 class="headline">为什么选择我们？</h4>
            <ul class="list iconok circle">
                <li>专业的团队</li>
                <li>在市场久经考验</li>
                <li>OA系统市场占有率遥遥领先</li>
                <li>就是好</li>
                <li>我编不出来了。。</li>
            </ul>
        </div>
        <div class="eight columns">
            <h4 class="headline">客户反馈</h4>
            <!-- Testimonial -->
            <blockquote class="speech-bubble">
                <div class="quote-content">
                    <p> 这是我们公司用过的最好的OA系统。</p>
                    <span class="quote-arrow"></span>
                </div>
                <div class="quote-meta"> Tim Cook, CEO
                    <br>
                    <span class="grey-text">Apple Inc.</span>
                </div>
            </blockquote>
            <!-- End Testimonial -->
        </div>
        <div class="clearfix"></div>
    </div>
    <!-- container -->
    <footer class="footer">
        <div class="container">
            <div class="footer-top clearfix">
                <div class="four columns">
                    <h3>关于我们</h3>
                    <p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.</p>
                    <p>Vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui.</p>
                </div>
                <div class="four columns">
                    <h3>合作发展</h3>
                    <p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.</p>
                    <p>Vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui.</p>
                </div>
                <div class="four columns">
                    <h3>服务支持</h3>
                    <p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.</p>
                    <p>Vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui.</p>
                </div>
                <div class="four columns">
                    <h3>联系我们</h3>
                    <ul class="list contact" style="margin-bottom: 15px;">
                        <li class="contact-address"><i class="fa fa-map-marker"></i><span>湖北省武汉市华中科技大学</span></li>
                        <li class="contact-mail"><i class="fa fa-envelope"></i><a class="link" href="#">mail@hust.edu.cn </a></li>
                        <li class="contact-phone"><i class="fa fa-phone"></i><span>027-000000</span></li>
                        <li class="contact-address"><i class="fa fa-clock-o"></i><span>周一 至 周五: 8:<sup>00</sup> - 18:<sup>00</sup></span>
                        </li>
                    </ul>
                    <div class="tooltips">
                        <ul class="social-icons-footer">
                            <li><a href="#" data-rel="tooltip" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="Facebook"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="Google+"><i class="fa fa-google-plus"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="Pinterest"><i class="fa fa-pinterest"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="LinkedIn"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="Dribbble"><i class="fa fa-dribbble"></i></a></li>
                            <li><a href="#" data-rel="tooltip" title="RSS"><i class="fa fa-rss"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- footer-top -->
        </div>
    </footer>
    <!-- footer -->
    <!-- End Document
							================================================== -->
</body>

</html>