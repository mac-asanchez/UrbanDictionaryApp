package com.example.urbandictionaryapp.presentation.base

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.urbandictionaryapp.presentation.utils.readInstanceProperty

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class BaseSpinnerAdapter<IT : Parcelable>(
    ctx: Context,
    resourceId: Int,
    private val dataSource: List<IT>?,
    private val listener: (IT) -> Unit
) : ArrayAdapter<IT>(ctx, resourceId) {
    override fun getCount(): Int = dataSource?.size ?: 0

    override fun getItem(position: Int): IT? = dataSource?.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    protected abstract fun getTextProperty(): String

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tv: TextView = super.getView(position, convertView, parent) as TextView

        tv.apply {
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            text = if (dataSource.isNullOrEmpty()) {
                ""
            } else {
                dataSource[position].let { item ->
                    try {
                        readInstanceProperty<String>(item, getTextProperty())
                    } catch (e: Exception) {
                        readInstanceProperty<Int>(item, getTextProperty()).toString()
                    } catch (e: Exception) {
                        ""
                    }
                }
            }
        }

        return tv
    }

    @ExperimentalStdlibApi
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tv: TextView = super.getView(position, convertView, parent) as TextView

        tv.apply {
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            //text = dataSource?.get(position)?.name ?: ""
            text = if (dataSource.isNullOrEmpty()) {
                ""
            } else {
                dataSource[position].let { item ->
                    setOnClickListener { listener(item) }
                    try {
                        readInstanceProperty<String>(item, getTextProperty())
                    } catch (e: Exception) {
                        readInstanceProperty<Int>(item, getTextProperty()).toString()
                    } catch (e: Exception) {
                        ""
                    }
                }
            }
        }

        return tv
    }
}