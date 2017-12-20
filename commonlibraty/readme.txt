1 目录规范

GloabApp 全局application
RootAct 启动Activity
Base 基础类 BaseActivity BaseFragment Base...
lib 可以放置一些非jar module类型的开源代码
widget 通用自定义控件类
util 通用工具类
xxx(业务模块)
model 数据层。网络api接口和缓存接口 命名xxxApi xxxCache
view 显示层。（activity fragment adapter）命名 xxxActivity xxxFragment xxxAdapter（也可以再分一层 activity fragment adapter）
present 数据处理层。命名xxxPresent
2 书写规范

不要将多行代码写在一行里如：if(condition) do_something
if / while / for 后面，一定要跟大括号
定义方法时大括号换行，如果是匿名方法，大括号跟方法名一行
不要连续出现2个以上的空行
3 命名规范

注：modulename为common或者业务模块xxx
3.1 变量、方法命名

方法命名小驼峰规则如：initView、setData
全局变量命名：mDataList
局部变量命名：dataList
常量命名：PAGE_SIZE
资源变量命名：mBtnLogin
3.2 控件前缀缩写

控件	前缀缩写
RelativeLayout	rl
LinearLayout	ll
FrameLayout	fl
TextView	txt
Button	btn
ImageButton	imgBtn
ImageView	img
CheckBox	chb
RadioButton	rdb
DatePicker	dtPk
EditText	edit
TimePicker	tmPk
ProgressBar	proBar
WebView	webVi
ScollView	scrollVi
ListView	lv
GridView	gv
ViewPager	vp
3.3 layout中资源id命名

控件缩写+描述

如 img_back、txt_name

3.4 layout命名

activity
modulename_activity_描述.xml
fragment
modulename_fragment_描述.xml
dialog
modulename_dialog_描述.xml
抽取出来复用的xml布局(include)
modulename_include_描述.xml
listview item
modulename_item_list_描述.xml
listview header
modulename_list_header_描述.xml
listview footer
modulename_list_footer_描述.xml
gridview item
模块item_grid描述.xml
widget
modulename_widget_描述.xml
3.5 资源文件命名

前缀+描述+后缀

前缀即为类型

图标 ic
背景 bg
分割线 div
按钮 btn
后缀是状态，如果没有就是普通就没有

默认图片 比如加载时的默认头像背景 default
按下状态 pressed/unpressed
选中状态 choosed/unchoosed
3.6 drawable命名

状态+控件缩写+描述

状态，即drawable类型：

selector
shape等
3.7 string命名

界面+描述
或者common+描述

通常建议把同一个界面的所有string都放到一起

全局的使用common

作者：Tsy远
链接：https://www.jianshu.com/p/c43b558c72b4
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。