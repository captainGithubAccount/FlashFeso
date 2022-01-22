package com.example.lwjtest_popupwindow.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.viewbinding.ViewBinding
import com.example.lwj_base.common.base.GetBinding
import com.example.lwj_common.R
import com.example.lwj_common.common.ui.controll.tools.utils.ScreenUtils

/**
 * @author lwj
 * 注意此基类包含y轴方向的自适应, 但是不包含水平方向自适应, 可以仿造我这个推导
 * */
abstract class BasePopupWindow<T : ViewBinding>(
    val mContext: Activity,
) : PopupWindow(), GetBinding<T> {
    //是否添加遮罩层
    private var isNeedMask: Boolean = false
    //是否使用默认的动画
    private var isDefaultAnimation: Boolean = false
    //需要弹出的window是否是满宽/match_parent
    private var isMatchParent = false

    /**
     * 设置默认动画(注意: 该方法需要在super.initPopupWindowSetting()前调用)
     * */
    fun setDefaultAnimation(){
        isDefaultAnimation = true
    }

    /**
     * 设置控件宽度满宽(注意: 该方法需要在super.initPopupWindowSetting()前调用)
     * */
    fun setMatchParent() {
        isMatchParent = true
    }

    /**
     * 设置添加遮罩层(注意: 该方法需要在super.initPopupWindowSetting()后调用)
     * */
    fun setNeedMask(){
        isNeedMask = true
    }

    private var _binding: T? = null
    val binding: T get() = _binding!!

    private var inflateView: View

    init {
        //inflateView = LayoutInflater.from(mContext).inflate(/*R.layout.layout_selector_image_type_window*/ getInflateLayout(), null)
        _binding = getViewBindingByReflex(LayoutInflater.from(mContext))
        inflateView = binding.root
        initPopupWindowSetting()
        binding.initView()

    }

    abstract fun T.initView()

    protected open fun initPopupWindowSetting() {
        contentView = inflateView

        // 设置SelectPicPopupWindow弹出窗体的宽
        if (isMatchParent) {
            width = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        // 设置SelectPicPopupWindow弹出窗体的高
        height = ViewGroup.LayoutParams.WRAP_CONTENT

        // 设置SelectPicPopupWindow弹出窗体可点击
        isFocusable = true
        isOutsideTouchable = true

        //刷新状态
        update()

        // 实例化一个ColorDrawable颜色为半透明
        val dw: ColorDrawable = ColorDrawable(0)

        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(dw)

        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        if (isDefaultAnimation) {
            animationStyle = R.style.PopupWindowAnimation
        }
        // 如果触摸位置在窗口外面则销毁


    }

    /**
     * 屏幕底部弹出
     * */
    fun showInScreenBottom(yoff: Int = 0) {
        showAtLocation(inflateView, Gravity.BOTTOM, 0, 0)
        if(isNeedMask){
            beginAnimation(true)
        }
    }

    /**
     * 屏幕顶部弹出
     * */
    fun showInScreenTop(yoff: Int = 0) {
        showAtLocation(inflateView, Gravity.TOP, 0, 0)
        if(isNeedMask){
            beginAnimation(true)
        }
    }

    /**
    * 控件下方下拉方式弹出
    * @param anchorView 锚点view(即点击该view弹出window)
    * */
    fun showBelowView(anchorView: View) {
        showAsDropDown(anchorView, anchorView.getLayoutParams().width / 2, 18)
    }

    /**
    * 控件居中显示
    * */
    fun showInScreenCenter(){
        showAtLocation(inflateView, Gravity.CENTER, 0, 0)
        if(isNeedMask){
            beginAnimation(true)
        }
    }


    /**
     * 根据点击呼出弹出window的控件位置弹出(贴附在右侧)
     * @param anchorView  呼出window的view, 如点击该view弹出window
     * @param xoff 水平偏移量
     * @param yoff 垂直偏移量
     */
    fun showByLocationAttachScreen(anchorView: View, xoff: Int = 0, yoff: Int = 0) {
        val contentWindowPos: IntArray = calculatePopWindowPos(anchorView, inflateView, 0)
        contentWindowPos[0] -= xoff
        contentWindowPos[1] -= yoff
        showAtLocation(inflateView,
            Gravity.TOP or Gravity.START,
            contentWindowPos[0],
            contentWindowPos[1])
    }

    /*
    * 根据点击呼出弹出window的控件位置弹出(不贴附parent右侧)
    * */
    fun showByLocationNoAttachScreen(anchorView: View, xoff: Int = 0, yoff: Int = 0) {
        val contentWindowPos: IntArray = calculatePopWindowPos(anchorView, inflateView, 1)
        contentWindowPos[0] -= xoff
        contentWindowPos[1] -= yoff
        showAtLocation(inflateView,
            Gravity.TOP or Gravity.START,
            contentWindowPos[0],
            contentWindowPos[1])
    }

    override fun dismiss() {
        super.dismiss()
        if(isNeedMask){
            beginAnimation(false)
        }
    }



    /*
    * 添加半透明遮罩层的渐变属性动画
    * */
    private  fun beginAnimation(show: Boolean) {
        val animator = ObjectAnimator.ofFloat(if (show) 1f else .8f, if (show) .8f else 1f)
        animator.addUpdateListener { animation -> setWindowAlpha(animation.animatedValue as Float) }
        animator.duration = 80
        animator.start()
    }

    /*
    * 设置window透明度
    * */
    private  fun setWindowAlpha(alpha: Float) {
        val window = mContext.window
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }

    companion object {
        /**
         * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
         * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
         * @param anchorView  呼出window的view, 如点击该view弹出window
         * @param contentView   window的内容布局, 即需要被inflate的布局
         * @return window显示的左上角的xOff,yOff坐标
         */
        fun calculatePopWindowPos(anchorView: View, contentView: View, windowType: Int): IntArray {

            //定义存储锚点的宽高坐标的数组
            val anchorPos: IntArray = IntArray(2)

            //获取锚点在其父窗口中的坐标并存放到数组内
            anchorView.getLocationInWindow(anchorPos)

            //获取锚点的高度和宽度
            val anchorHeight = anchorView.height
            val anchorWidth = anchorView.width

            //获取屏幕的宽度和高度
            val screenWidth = ScreenUtils.getScreenWidth(anchorView.context)
            val screenHeight = ScreenUtils.getScreenHeight(anchorView.context)

            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            //计算contentView的宽高
            val contentWindowHeight = contentView.measuredHeight;
            val contentWindowWidth = contentView.measuredWidth;

            // 判断需要向上弹出还是向下弹出显示
            val isNeedShowUp: Boolean =
                screenHeight - anchorPos[1] - anchorHeight < contentWindowHeight

            //定义需要被返回的用来存储contentView的宽高坐标的数组
            val contentWindowPos: IntArray = IntArray(2)

            when (windowType) {
                WINDOW_TYPE.WINDOW_ATTACH_SCREEN_RIGHT.flag -> {
                    if (isNeedShowUp) {
                        contentWindowPos[0] = screenWidth - contentWindowWidth
                        contentWindowPos[1] = anchorPos[1] - contentWindowHeight
                    } else {
                        contentWindowPos[0] = screenWidth - contentWindowWidth
                        contentWindowPos[1] = anchorPos[1] + anchorHeight
                    }
                }

                WINDOW_TYPE.WINDOW_NO_ATTACH_SCREEN_RIGHT.flag -> {
                    if (isNeedShowUp) {
                        if (contentWindowWidth == anchorWidth) {
                            //当弹出window不贴着屏幕右边, 并且弹出window宽度和anchorView宽度一样宽时候
                            contentWindowPos[0] = anchorPos[0]
                        } else {
                            //当弹出window不贴着屏幕右边, 并且弹出window宽度 不等于 anchorView宽度
                            contentWindowPos[0] =
                                anchorPos[0] + anchorWidth / 2 - contentWindowWidth / 2
                        }
                        contentWindowPos[1] = anchorPos[1] - contentWindowHeight
                    } else {
                        if (contentWindowWidth == anchorWidth) {
                            //当弹出window不贴着屏幕右边, 并且弹出window宽度和anchorView宽度一样宽时候
                            contentWindowPos[0] = anchorPos[0]
                        } else {
                            //当弹出window不贴着屏幕右边, 并且弹出window宽度 不等于 anchorView宽度
                            contentWindowPos[0] =
                                anchorPos[0] + anchorWidth / 2 - contentWindowWidth / 2
                        }
                        contentWindowPos[1] = anchorPos[1] + anchorHeight
                    }

                }
            }


            return contentWindowPos
        }
    }

    enum class WINDOW_TYPE(var flag: Int) {
        //弹出的window是贴附屏幕右边
        WINDOW_ATTACH_SCREEN_RIGHT(0),
        //弹出的window没有贴附屏幕右边
        WINDOW_NO_ATTACH_SCREEN_RIGHT(1)
    }




}