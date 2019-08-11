package com.daniel.memberid.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.daniel.memberid.CommonsCons
import com.daniel.memberid.R
import com.daniel.memberid.databinding.ItemDataLoadingBinding
import com.daniel.memberid.databinding.ItemFeedPageBinding
import com.daniel.memberid.model.AwardsModel
import java.text.NumberFormat
import java.util.*




class FeedPageAdapter: RecyclerView.Adapter<FeedPageAdapter.FeedPageViewHolder>() {

    private var awards = emptyList<AwardsModel>().toMutableList()

    companion object{
        private const val ITEM_VIEW_TYPE_CONTENT = 1
        private const val ITEM_VIEW_TYPE_LOADING = 2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPageViewHolder {

        if(viewType==ITEM_VIEW_TYPE_CONTENT){
            val binding = DataBindingUtil
                .inflate<ItemFeedPageBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_feed_page,
                    parent, false
                )
            return FeedPageViewHolder(binding)
        }
        else {
            val binding = DataBindingUtil
                .inflate<ItemDataLoadingBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_data_loading,
                    parent, false
                )
            return FeedPageViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: FeedPageViewHolder, position: Int) {

        if(position>=awards.count()){

        } else {
            holder.setData(awards[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == awards.count())
            ITEM_VIEW_TYPE_LOADING
        else
            ITEM_VIEW_TYPE_CONTENT
    }

    override fun getItemCount() = awards.size+1

    inner class FeedPageViewHolder : RecyclerView.ViewHolder {

        private var loadingBinding: ItemDataLoadingBinding? = null
        private var feedBinding: ItemFeedPageBinding? = null

        constructor(feedBinding: ItemFeedPageBinding) : super(feedBinding.root) {
            this.feedBinding = feedBinding

        }

        constructor(loadingBinding: ItemDataLoadingBinding) : super(loadingBinding.root) {
            this.loadingBinding = loadingBinding
        }

        @SuppressLint("SetTextI18n")
        fun setData(award: AwardsModel?) {

            when(award?.awardsType){
                CommonsCons.AWARD_TYPE_PRODUCTS -> feedBinding?.cardViewItemType?.setCardBackgroundColor(Color.parseColor("#FF4500"))
                CommonsCons.AWARD_TYPE_GIFTCARDS -> feedBinding?.cardViewItemType?.setCardBackgroundColor(Color.parseColor("#FF7F50"))
                CommonsCons.AWARD_TYPE_VOUCHERS -> feedBinding?.cardViewItemType?.setCardBackgroundColor(Color.parseColor("#039BE5"))
            }

            feedBinding?.itemTypeName?.text = award?.awardsType
            feedBinding?.poinNeeded?.text = "${NumberFormat.getNumberInstance(Locale.GERMAN).format(award?.poinNeeded)} Poin"
            feedBinding?.awardName?.text = award?.awardsName
        }

    }

    internal fun setSurveys(awardsList: List<AwardsModel>) {
        this.awards = awardsList.toMutableList()
        notifyDataSetChanged()
    }

    internal fun addData(awardsList: List<AwardsModel>){
        this.awards.addAll(awardsList)
        notifyDataSetChanged()
    }

}