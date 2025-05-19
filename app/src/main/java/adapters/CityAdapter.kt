package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import epi.gl4e.tp6.R

class CityAdapter (private val cityList:Array<String>,private val cityListener:OncityClickListener):RecyclerView.Adapter<CityAdapter.CityViewHolder>(){
    class CityViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val textView:TextView =view.findViewById(R.id.textView)
        fun bind(city:String,listener:OncityClickListener){
            textView.setOnClickListener(){
                listener.onCityClick(city)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val adapterLayout=LayoutInflater.from(parent.context).inflate(R.layout.city_item,parent,false)
        return CityViewHolder(adapterLayout)

    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.textView.text=cityList[position]
        holder.bind(cityList[position],cityListener)}
    interface OncityClickListener{
        fun onCityClick(city:String)
    }
}