# BiHu
  一个不可名状的APP
###  寒假考核
## 简介
一个混杂了QQ风和知乎风的自我感觉一般般的简单问道APP。

## 功能
●登录/注册/修改密码  
●修改头像（图片保存在本地）  
●加载问题/答案  
●认同/不认同 问题和答案  
●总之API里除了采纳以外都实现了

## 技术栈\知识点
●自定义控件（圆形的ImageView、RecyclerItem)  
●封装工具类（HTTP和ImageLoader，后者一点都不好用）  
●基础控件（cardView抄..借鉴知乎ui真好用）  
●伪·MVP架构（目前完成的层数，Presenter层和View层有点混在一起了）  

## 缺陷
●主线路没优化，卡  
●RecyclerView数据没有提前缓存，卡  
●ImageLoader加载图片，卡  
●我放弃把图片上传给我的服务器，头像是本地保存的  
●UI界面太简单了  
●代码命名有些不规范没改  
●代码部分混乱  

## 心得体会
●如果有下次，我一定提前写。（划掉）这个BIHU写了十天不到，十天从Android入门到入坑（划掉)  
●MVP架构初次接触真得折磨人，网上找到的Demo也是千奇百怪，最后只理清楚了Modeul层处理数据，View层显示和权限申请等，Presenter层两者的连接  
于是机智的我，当晚就给代码下了毒，登录注册还没有一梭子来得方便。我的Modeul层只不断post和deal数据，View层不断弹出各类自定义或者系统的Dialog  
，刷新RC和权限申请，Presenter则活跃在Activity中，什么都要通过它。  
●画画就是个弟弟，却用AS不断画UI，圆形ImageView参数总是设置不对，onDrawable学到了一手  
●还有各类工具类，虽然这回只封装了HTTP和ImageLoader，但本来打算在封装Bitmap下载压缩保存到本地，从本地读取返回程序。还在网上看到了各种工具。  
●Intent真是神奇，每每查回调、跳转都能看到它  
●我想知道怎么能让APP不那么卡

## Gif展示
[view][https://github.com/965023780/BH/blob/master/Demo%20(1).gif]
[view][https://github.com/965023780/BH/blob/master/Demo%20(2).gif]
[view][https://github.com/965023780/BH/blob/master/Demo%20(3).gif]
[view][https://github.com/965023780/BH/blob/master/Demo%20(4).gif]

