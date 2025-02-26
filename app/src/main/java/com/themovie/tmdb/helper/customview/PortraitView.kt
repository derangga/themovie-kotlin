package com.themovie.tmdb.helper.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.themovie.tmdb.R
import com.themovie.tmdb.helper.cacheImage

class PortraitView @JvmOverloads constructor (context: Context,
                   attrs: AttributeSet? = null,
                   defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), CompoundView {

    private val posterContainer by lazy { findViewById<CardView>(R.id.poster_container) }
    private val posterTitle by lazy { findViewById<TextView>(R.id.poster_title) }
    private val posterSubtitle by lazy { findViewById<TextView>(R.id.poster_subtitle) }
    private val posterRate by lazy { findViewById<TextView>(R.id.poster_rate) }
    private val posterDate by lazy { findViewById<TextView>(R.id.poster_date) }
    private val posterImage by lazy { findViewById<ImageView>(R.id.poster_img) }

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
                val params = posterContainer.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                posterSubtitle.visibility = View.GONE
            }
            1 -> {
                val params = posterContainer.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                posterRate.visibility = View.GONE
            }
            2 -> {
                val params = posterContainer.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240.toFloat(), resources.displayMetrics).toInt()
                posterRate.visibility = View.GONE
                posterSubtitle.visibility = View.GONE
            }
            3 -> {
                val params = posterContainer.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                posterSubtitle.visibility = View.GONE
                posterRate.visibility = View.GONE
                posterDate.visibility = View.VISIBLE
            }
            else -> {
                val params = posterContainer.layoutParams
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 272.toFloat(), resources.displayMetrics).toInt()
                posterSubtitle.visibility = View.GONE
            }
        }
    }

    fun image(url: String) : PortraitView {
        posterImage.cacheImage(url)
        return this
    }

    fun title(title: String) : PortraitView {
        posterTitle.text = title
        return this
    }

    fun subtitle(subtitle: String) : PortraitView {
        posterSubtitle.text = subtitle
        return this
    }

    fun rating(rating: String) : PortraitView {
        posterRate.text = rating
        return this
    }

    fun dateRelease(date: String) : PortraitView {
        posterDate.text = date
        return this
    }
}