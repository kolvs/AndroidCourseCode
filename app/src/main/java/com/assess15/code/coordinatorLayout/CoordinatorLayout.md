## CoordinatorLayout


##### CoordinatorLayout


##### BeHavior
1、使用方式

```xml
'app:layout_behavior="@string/appbar_scrolling_view_behavior"'
```

2、自定义BeHavior


*注意：*
使用BeHavior必须是直接从属CoordinatorLayout，否则无效

##### AppBarLayout

CoordinatorLayout + AppBarLayout + ToolBar

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/behavior_demo_coordinatorLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#fff">

<android.support.design.widget.AppBarLayout
    android:id="@+id/appbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_scrollFlags="scroll|enterAlways|snap"
        >
    </android.support.v7.widget.Toolbar>

</android.support.design.widget.AppBarLayout>

<android.support.v7.widget.RecyclerView
    android:id="@+id/behavior_demo_recycler"
    android:layout_width="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    android:layout_height="match_parent"/>

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:layout_gravity="bottom"
    android:background="@color/colorPrimary"
    android:gravity="center"
    app:layout_behavior="com.lipt0n.coordinatorlayout.MyBottomBarBehavior">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="底部菜单"
        android:textColor="#ffffff"/>
</LinearLayout>
</android.support.design.widget.CoordinatorLayout>
```

