package com.themovie.helper.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.themovie.R
import com.themovie.helper.cacheImage
import kotlinx.android.synthetic.main.adapter_portrait.view.*

class PortraitView(context: Context,
                   attrs: AttributeSet? = null,
                   defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), CompoundView {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    private var listener: OnClickListener? = null

    init {
        inflateView()
        initAttr(context.obtainStyledAttributes(attrs, R.styleable.PortraitView))
    }

    override fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.adapter_portrait, this)
    }

    override fun initAttr(attrs: TypedArray?) {
        //val params = poster_item.layoutParams
        when(attrs?.getInt(R.styleable.PortraitView_type, 0)){
            0 -> {
                val params = poster_item.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                poster_subtitle.visibility = View.GONE
            }
            1 -> {
                val params = poster_item.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                poster_rate_l.visibility = View.GONE
            }
            2 -> {
                val params = poster_item.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240.toFloat(), resources.displayMetrics).toInt()
                poster_rate_l.visibility = View.GONE
                poster_subtitle.visibility = View.GONE
            }
            3 -> {
                val params = poster_item.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                poster_subtitle.visibility = View.GONE
                poster_rate_l.visibility = View.GONE
                poster_date.visibility = View.VISIBLE
            }
            else -> {
                val params = poster_item.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                poster_subtitle.visibility = View.GONE
            }
        }

        poster_item.setOnClickListener {
            listener?.onClick()
        }
    }

    fun setImage(url: String){
        cacheImage(context, url, poster_img)
    }

    fun setTitle(title: String){
        poster_title.text = title
    }

    fun setSubtitle(subtitle: String){
        poster_subtitle.text= subtitle
    }

    fun setRating(rating: String){
        poster_rate.text = rating
    }

    fun setDateRelease(date: String){
        poster_date.text = date
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.listener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }

}