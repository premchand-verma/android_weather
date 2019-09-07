package weather.android.com.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itme_forecost.view.*
import weather.android.com.R
import weather.android.com.model.Forecastday
import java.text.SimpleDateFormat


class ForecastAdapter(val ctx:Context, val list:List<Forecastday>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.itme_forecost, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list.get(position))
    }

     class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
         val txt_day = view.txt_day
         val txt_temp = view.txt_temp
        fun bindView(data: Forecastday) {
            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val dt1 = format1.parse(data.date)
            val format2 = SimpleDateFormat("EEEE")
            val finalDay = format2.format(dt1)
            txt_day.text = finalDay
            txt_temp.text = data.day.avgtemp_c.toInt().toString()+" C"
        }
    }
}