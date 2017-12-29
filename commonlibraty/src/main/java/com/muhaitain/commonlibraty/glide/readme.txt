Glide优点
(1).图片缓存-->媒体缓存
Glide不仅可以图片缓存,它支持Gif、WebP、缩略图，甚至是Video,所以更应该是一个媒体缓存
(2).支持优先级处理
(3).与Activity/Fragment生命周期一致,支持trimMemory：Glide对每个context都保持一个RequestManager,
通过FragmentTransaction保持与Activity/Fragment生命周期一致,并且有对应的trimMemory接口实现可供调用
(4).支持okhttp、Volley
Glide默认通过HttpURLConnection获取数据,可配合okhttp或Volley使用，实际ImageLoader、Picasso也都支持okhttp、Volley
(5).内存友好
a.Glide的内存缓存有个active的设计
从内存缓存中获取数据时,不像一般的实现用get,而是用remove,再将这个缓存数据放到一个value为软引用的activeResources map中,
并计数引用数,在图片加载完成后进行判断,如果引用计数为空则回收掉
b.内存缓存更小图片
Glide以url、view_width、view_height、屏幕的分辨率等作为联合主键(key),将处理后的图片缓存在内存缓存中,
而不是原始图片以节省大小
c.与Activity/Fragment声明周期一致,支持trimMemory
d.图片默认使用RGB_565而不是ARGB_888虽然清晰度差些,但是图片更小,也可配置到ARGB_888
