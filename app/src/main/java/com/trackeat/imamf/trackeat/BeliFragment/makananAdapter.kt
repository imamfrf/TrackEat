package com.trackeat.imamf.trackeat.BeliFragment

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.trackeat.imamf.trackeat.R
import com.trackeat.imamf.trackeat.model.List_Makanan
import com.trackeat.imamf.trackeat.util.Constant.DEFAULT.DEFAULT_NOT_SET

class makananAdapter(
        val items: ArrayList<List_Makanan>, val listener: makananListener, val mContext: Context
) : RecyclerView.Adapter<makananAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_food, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textViewFoodName.text = item.namaMakanan
        holder.textViewCateringName.text = item.namaCatering
        holder.textViewCalorie.text = item.kaloriMakanan + " kal"
        holder.textViewHarga.text = "Rp" + item.harga
        val firstPhotoUrl = item.photo
        if (firstPhotoUrl == DEFAULT_NOT_SET) {
            Picasso.get().load(R.drawable.default_image_not_set).into(holder.imageViewThumbnail)
        } else {
            Picasso.get().load(firstPhotoUrl).into(holder.imageViewThumbnail)
        }
        holder.buttonBuy.setOnClickListener {
            listener.onBuyClick(item.idMakanan)
        }
        holder.detailButton.setOnClickListener {
            listener.onItemClick(item.idMakanan, item.namaMakanan, item.namaCatering, item.latitude, item.longitude)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        // MARK: - Public Properties
        val cardView: CardView
        val textViewFoodName: TextView
        val textViewCateringName: TextView
        val textViewCalorie: TextView
        val textViewHarga: TextView
        val imageViewThumbnail: ImageView
        val buttonBuy: Button
        val detailButton: Button

        // MARK: - Initialization
        init {
            cardView = itemView?.findViewById(R.id.cardView) as CardView
            textViewFoodName = itemView.findViewById(R.id.textViewFoodName) as TextView
            textViewCateringName = itemView.findViewById(R.id.textViewCateringName) as TextView
            textViewCalorie = itemView.findViewById(R.id.textViewCalorie) as TextView
            textViewHarga = itemView.findViewById(R.id.textViewHarga) as TextView
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail) as ImageView
            buttonBuy = itemView.findViewById(R.id.buttonBuy) as Button
            detailButton = itemView.findViewById(R.id.buttonDetail) as Button
        }
    }
}