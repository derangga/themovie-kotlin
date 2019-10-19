package com.themovie.helper.portraintview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.themovie.R
import com.themovie.helper.ImageCache
import kotlinx.android.synthetic.main.adapter_portrait.view.*

class PortraitView(context: Context,
                   attrs: AttributeSet? = null,
                   defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), CompoundView {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    private lateinit var listener: OnClickListener

    init {
        inflateView()
        initAttr(context.obtainStyledAttributes(attrs, R.styleable.PortraitView))
    }

    override fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.adapter_portrait, this)
    }

    override fun initAttr(attrs: TypedArray?) {
        val params = poster_item.layoutParams
        when(attrs?.getInt(R.styleable.PortraitView_type, 0)){
            0 -> {
                poster_subtitle.visibility = View.GONE
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 232.toFloat(), resources.displayMetrics).toInt() // convert dip to pixel
            }
            1 -> {
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
            }
            else -> {

            }
        }

        poster_item.setOnClickListener {
            listener.onClick()
        }
    }

    fun setImage(url: String){
        ImageCache.setImageViewUrl(context, url, poster_img)
    }

    fun setTitle(title: String){
        poster_title.text = title
    }

    fun setSubtitle(subtitle: String){
        poster_subtitle.text= subtitle
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.listener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }

}