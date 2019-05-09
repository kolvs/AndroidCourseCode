## RecyclerView

### RecyclerView基础

##### 0、简单使用

##### 1、build.gradle 

```xml
implementation 'com.android.support:recyclerview-v7:28.0.0'
```

##### 2、创建对象

```Java
RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
```

##### 3、设置布局管理器

这里使用的是桥接模式

```Java
rv.setLayoutManager(new GridLayoutManager(this, 3));
rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
rv.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
// StaggeredGridLayoutManager

// GridLayoutManager

// LinearLayoutManager常用方法
lm.findFirstVisibleItemPosition(); 返回当前第一个可见 Item 的 position
lm.findFirstCompletelyVisibleItemPosition(); 返回当前第一个完全可见 Item 的 position
lm.findLastVisibleItemPosition(); 返回当前最后一个可见 Item 的 position
lm.findLastCompletelyVisibleItemPosition(); 返回当前最后一个完全可见 Item 的 position
lm.findViewByPosition();
lm.scrollHorizontallyBy(); 水平滚动到某个位置
lm.scrollVerticallyBy(); 垂直滚动到某个位置
// 自定义LayoutManager布局管理器

```

##### 4、设置适配器

这里使用的是适配器模式

```Java
// 设置适配器
rv.setAdapter(adapter);

/**
* 自定义Adapter，重写3个方法
* onCreateViewHolder() 初始化布局文件
* onBindViewHolder() 布局与数据绑定
* getItemCount() 数据源大小
* 自定义ViewHolder类；获取布局控件中id
*/
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.VH> {
    private List<Data> dataList;
    private Context context;

    public DemoAdapter(Context context, ArrayList<Data> datas) {
        this.dataList = datas;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(View.inflate(context, android.R.layout.simple_list_item_2, null));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.mTextView.setText(dataList.get(position).getNum());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView mTextView;
        public VH(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
```

##### 5、设置Item动画

```Java
// 自定义默认动画，设置添加/移除执行时间
DefaultItemAnimator animator = new DefaultItemAnimator();
animator.setAddDuration(1000);
animator.setMoveDuration(1000);
animator.setChangeDuration(1000);
animator.setRemoveDuration(1000);
rv.setItemAnimator(animator);
// 或者直接使用默认动画
rv.setItemAnimator(new DefaultItemAnimator());
```

##### 6、设置分割线

```Java
// 自定义分割线：颜色，高度
DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider));
// 添加分割线
rv.addItemDecoration(divider);
// 自定义分割线
public class Divider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    private int leftMargin, rightMargin, topMargin, bottomMargin;

    private int width, height;

    private int mOrientation;

    public Divider(Drawable divider, int orientation) {
        setDivider(divider);
        setOrientation(orientation);
    }

    private void setDivider(Drawable divider) {
        this.mDivider = divider;
        if (mDivider == null) {
            mDivider = new ColorDrawable(0xffff0000);
        }
        width = mDivider.getIntrinsicWidth();
        height = mDivider.getIntrinsicHeight();
    }

    private void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setMargin(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + topMargin;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - bottomMargin;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin + leftMargin;
            final int right = left + width;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + leftMargin;
        final int right = parent.getWidth() - parent.getPaddingRight() - rightMargin;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + topMargin;
            final int bottom = top + height;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, leftMargin + width + rightMargin, 0);
        } else {
            outRect.set(0, 0, 0, topMargin + height + bottomMargin);
        }
    }
}
```

##### 7、 点击/长按事件

```Java
// 基于RecyclerView.OnItemTouchListener实现监听
// 点击事件
rv.addOnItemTouchListener(object : RecyclerItemClickListener(this) {
	override fun onItemClick(view: View?, position: Int) {
  		toast("点击了....$position")
 		}
 });
// 自定义RecyclerView.OnItemTouchListener，通过GestureDetector()/GestureDetectorCompat()(v4)实现
public abstract class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    protected abstract void onItemClick(View view, int position);

    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}

/** 在RecyclerView.Adapter中通过
* ViewHolder.itemView.setOnClickListener()
* ViewHolder.itemView.setOnLongClickListener()
* 实现Item点击事件，接口回调方式获取Adapter中点击事件
*/
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedHolder feedHolder, int i) {
        //用户头像
        Picasso.with(feedHolder.itemView.getContext())
                .load(getAvatarResId(i))
                .centerInside()
                .fit()
                .into(feedHolder.ivAvatar);

        //内容图片
        Picasso.with(feedHolder.itemView.getContext())
                .load(getContentResId(i))
                .centerInside()
                .fit()
                .into(feedHolder.ivContent);

        feedHolder.tvContent.setText("测试数据" + i);

        if (mOnItemClickListener != null) {
            feedHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, feedHolder.getAdapterPosition());
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            feedHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onItemLongClick(v, feedHolder.getAdapterPosition());
                }
            });
        }
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

    public int getContentResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.taeyeon_one;
            case 1:
                return R.drawable.taeyeon_two;
            case 2:
                return R.drawable.taeyeon_three;
            case 3:
                return R.drawable.taeyeon_four;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class FeedHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        ImageView ivContent;
        TextView tvContent;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            ivContent = itemView.findViewById(R.id.iv_content);
            tvContent = itemView.findViewById(R.id.tv_nickname);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
  
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}


```

##### 8、核心方法

| 类名                        | 作用                           |
| --------------------------- | :----------------------------- |
| RecyclerView.LayoutManager  | 负责Item视图布局管理器         |
| RecyclerView.ItemDecoration | 给每一项Item视图添加子View     |
| RecyclerView.ItemAnimator   | 负责数据添加和删除时的动画效果 |
| RecyclerView.Adapter        | 为每一项Item创建视图           |
| RecyclerView.ViewHolder     | 承载Item视图的子局部           |

##### 9、工作原理

**LayoutManager：**

RecyclerView继承与ViewGroup，RecyclerView将onMeasure(),onLayout()交给了LayoutManager处理，因此如果给 RecyclerView 设置不同的 LayoutManager 就可以达到不同的显示效果，因为`onMeasure()`、`onLayout()`都不同。

**ItemDecoration：**

**ItemDecoration** 是为了显示每个 item 之间分隔样式的。它的本质实际上就是一个 Drawable。当 RecyclerView 执行到 `onDraw()` 方法的时候，就会调用到他的 `onDraw()`，这时，如果你重写了这个方法，就相当于是直接在 RecyclerView 上画了一个 Drawable 表现的东西。 而最后，在他的内部还有一个叫`getItemOffsets()`的方法，从字面就可以理解，他是用来偏移每个 item 视图的。当我们在每个 item 视图之间强行插入绘画了一段 Drawable，那么如果再照着原本的逻辑去绘 item 视图，就会覆盖掉 Decoration 了，所以需要`getItemOffsets()`这个方法，让每个 item 往后面偏移一点，不要覆盖到之前画上的分隔样式了。

**ItemAnimator：**

每一个 item 在特定情况下都会执行的动画。说是特定情况，其实就是在视图发生改变，我们手动调用`notifyxxxx()`的时候。通常这个时候我们会要传一个下标，那么从这个标记开始一直到结束，所有 item 视图都会被执行一次这个动画。

**Adapter：**

首先是适配器，适配器的作用都是类似的，用于提供每个 item 视图，并返回给 **RecyclerView** 作为其子布局添加到内部。但是，与 **ListView** 不同的是，ListView 的适配器是直接返回一个 View，将这个 View 加入到 ListView 内部。而 RecyclerView 是返回一个 ViewHolder 并且不是直接将这个 holder 加入到视图内部，而是加入到一个缓存区域，在视图需要的时候去缓存区域找到 holder 再间接的找到 holder 包裹的 View。

**ViewHolder：**

每个 **ViewHolder** 的内部是一个 View，并且 **ViewHolder** 必须继承自`RecyclerView.ViewHolder`类。 这主要是因为 RecyclerView 内部的缓存结构并不是像 ListView 那样去缓存一个 View，而是直接缓存一个 ViewHolder ，在 ViewHolder 的内部又持有了一个 View。既然是缓存一个 ViewHolder，那么当然就必须所有的 ViewHolder 都继承同一个类才能做到了。

**缓存与复用**

RecyclerView 的内部维护了一个二级缓存，滑出界面的 ViewHolder 会暂时放到 cache 结构中，而从 cache 结构中移除的 ViewHolder，则会放到一个叫做 **RecycledViewPool** 的循环缓存池中。
顺带一说，RecycledView 的性能并不比 ListView 要好多少，它最大的优势在于其扩展性。但是有一点，在 RecycledView 内部的这个第二级缓存池 **RecycledViewPool** 是可以被多个 RecyclerView 共用的，这一点比起直接缓存 View 的 ListView 就要高明了很多，但也正是因为需要被多个 RecyclerView 公用，所以我们的 ViewHolder 必须继承自同一个基类(即RecyclerView.ViewHolder)。
默认的情况下，cache 缓存 2 个 holder，RecycledViewPool 缓存 5 个 holder。对于二级缓存池中的 holder 对象，会根据 viewType 进行分类，不同类型的 viewType 之间互不影响。

##### 10、自定义缓存

```Java
rv.setViewCacheExtension();//:RecyclerView.ViewCacheExtension
```



---

### RecyclerView进阶

##### 1、吸顶View



##### 2、锚点定位

RecyclerView + TabLayout 实现锚点定位

实现思路：
1、监听recyclerView滑动到的位置，tablayout切换到对应标签
2、tablayout各标签点击，recyclerView可滑动到对应区域

##### 3、拖拽排序

##### 4、滑动删除

##### 5、局部刷新

